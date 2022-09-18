package by.singularity.service;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.*;
import by.singularity.exception.*;
import by.singularity.mapper.impl.WayBillMapper;
import by.singularity.repository.WayBillRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WayBillService {
    private final WayBillRepository wayBillRepository;
    private final CheckpointService checkpointService;
    private final WayBillMapper wayBillMapper;
    private final InvoiceService invoiceService;
    private final CarService carService;
    private final UserService userService;
    private final ProductService productService;
    private final PaymentService paymentService;

    @Transactional
    public WayBill createWayBill(WayBillDto wayBillDto, HttpServletRequest request) throws InvoiceException, CarException, UserException {
        invoiceService.getInvoice(wayBillDto.getInvoiceNumber());
        carService.getCar(wayBillDto.getCarId());
        WayBill wayBill = wayBillMapper.toModel(wayBillDto);
        User verifier = userService.getUserByAuthorization(request);
        wayBill.setVerifier(verifier);
        wayBill.setCarriageStatuses(new HashSet<>(Collections.singleton(CarriageStatus.STARTED_CARRIAGE)));
        Set<Checkpoint> checkpoints = wayBillDto.getCheckpoints().stream()
                .map(checkpointService::createCheckpoint)
                .collect(Collectors.toSet());
        wayBill.setCheckpoints(checkpoints);
        wayBillRepository.save(wayBill);
        log.info("WAYBILL WITH ID {} CREATED", wayBill.getId());
        Invoice invoice = wayBill.getInvoice();
        invoice.setStatus(new HashSet<>(Collections.singleton(InvoiceStatus.VERIFICATION_COMPLETE)));
        invoiceService.createInvoice(invoice);
        log.info("INVOICE WITH ID {} VERIFIED", invoice.getNumber());
        return wayBill;
    }

    @Transactional
    public void finishCarriage(Long id) throws WayBillException {
        WayBill wayBill = wayBillRepository.findById(id)
                .orElseThrow(()->new WayBillException("waybill with id " + id + " not exist"));
        if (wayBill.getCarriageStatuses().contains(CarriageStatus.FINISHED_CARRIAGE)) {
            return;
        }
        HashSet<CarriageStatus> carriageStatuses = new HashSet<>();
        wayBill.getInvoice().getProducts()
                .forEach(product -> {
                    product.setProductStatus(new HashSet<>(Collections.singleton(ProductStatus.DELIVERED)));
                    productService.createProduct(product);
                });
        carriageStatuses.add(CarriageStatus.FINISHED_CARRIAGE);
        setIncome(wayBill);
        wayBill.setCarriageStatuses(carriageStatuses);
        wayBillRepository.save(wayBill);
        log.info("CARRIAGE {} FINISHED", id);
    }

    public void reachCheckpoint(Long id, HttpServletRequest request) throws CheckpointException, UserException {
        User driver = userService.getUserByAuthorization(request);
        Checkpoint checkpoint = checkpointService.getById(id);
        WayBill wayBill = checkpoint.getWayBill();
        if (!wayBill.getInvoice().getDriver().getLogin().equals(driver.getLogin())) {
            throw new UserException("you haven't got this checkpoint");
        }
        checkpointService.reachCheckpoint(id);
        log.info("CHECKPOINT {} REACHED", id);
    }

    public List<WayBillDto> getAllWayBills(CarriageStatus carriageStatus, Pageable pageable) {
        Page<WayBill> wayBillList = wayBillRepository.findAll(getFindingPredicate(carriageStatus),pageable);
        return wayBillList.stream()
                .map(wayBillMapper::toDto)
                .collect(Collectors.toList());
    }

    public WayBillDto getWayBill(Long id) throws WayBillException {
        WayBill wayBill = wayBillRepository.findById(id)
                .orElseThrow(()->new WayBillException("waybill with id " + id + " not exist"));
        return wayBillMapper.toDto(wayBill);
    }

    private Predicate getFindingPredicate(CarriageStatus carriageStatus) {
        return QPredicate.builder()
                .add(carriageStatus, QWayBill.wayBill.carriageStatuses::contains)
                .buildAnd();
    }

    private Predicate invoiceByUserPredicate(Long userId) {
        return QPredicate.builder()
                .add(userId, QInvoice.invoice.driver.id::eq)
                .buildAnd();
    }

    private void setIncome(WayBill wayBill) {
        Car car = wayBill.getCar();
        int distance = wayBill.getDistance();
        int income = car.getPriceForKm() * distance;
        int consumption = car.getConsumptionForKm() * distance;
        int profit = income - consumption;
        wayBill.setIncome(income);
        wayBill.setConsumption(consumption);
        wayBill.setProfit(profit);
        wayBillRepository.save(wayBill);
        paymentService.createPayment(wayBill,profit);
    }

}
