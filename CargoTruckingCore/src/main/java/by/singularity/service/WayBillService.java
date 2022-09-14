package by.singularity.service;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.QWayBill;
import by.singularity.entity.WayBill;
import by.singularity.exception.CarException;
import by.singularity.exception.InvoiceException;
import by.singularity.exception.UserException;
import by.singularity.exception.WayBillException;
import by.singularity.mapper.impl.WayBillMapper;
import by.singularity.repository.CarRepository;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.UserRepository;
import by.singularity.repository.WayBillRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WayBillService {
    private final WayBillRepository wayBillRepository;
    private final CheckpointService checkpointService;
    private final WayBillMapper wayBillMapper;
    private final InvoiceRepository invoiceRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public WayBill createWayBill(WayBillDto wayBillDto) throws InvoiceException, CarException, UserException {

        if (!invoiceRepository.existsById(wayBillDto.getInvoiceNumber())) {
            throw new InvoiceException("invoice with number " + wayBillDto.getInvoiceNumber() + " not exist");
        }

        if (!carRepository.existsById(wayBillDto.getCarId())) {
            throw new CarException("car with id " + wayBillDto.getCarId() + " not exist");
        }

        if (!userRepository.existsById(wayBillDto.getVerifierId())) {
            throw new UserException("user with id " + wayBillDto.getVerifierId() + " not exist");
        }

        WayBill wayBill = wayBillMapper.toModel(wayBillDto);
        wayBill.setCarriageStatuses(new HashSet<>());
        wayBillRepository.save(wayBill);
        wayBillDto.getCheckpoints().forEach(checkpointDto->checkpointService.createCheckpoint(checkpointDto,wayBill));
        log.info("WAYBILL WITH ID {} CREATED", wayBill.getId());
        return wayBill;
    }

    public void reachCheckpoint(Long id) throws WayBillException {
        WayBill wayBill = wayBillRepository.findById(id)
                .orElseThrow(()->new WayBillException("waybill with id " + id + " not exist"));
        HashSet<CarriageStatus> carriageStatuses = new HashSet<>();
        carriageStatuses.add(CarriageStatus.FINISHED_CARRIAGE);
        wayBill.setCarriageStatuses(carriageStatuses);
        wayBillRepository.save(wayBill);
        log.info("CHECKPOINT IN WAYBILL WITH ID {} REACHED", id);
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

}
