package by.singularity.service;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.QWayBill;
import by.singularity.entity.WayBill;
import by.singularity.exception.CarException;
import by.singularity.exception.InvoiceException;
import by.singularity.exception.WayBillException;
import by.singularity.mapper.impl.WayBillMapper;
import by.singularity.repository.CarRepository;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.WayBillRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO TEST
public class WayBillService {
    private final WayBillRepository wayBillRepository;
    private final CheckpointService checkpointService;
    private final WayBillMapper wayBillMapper;
    private final InvoiceRepository invoiceRepository;
    private final CarRepository carRepository;

    public WayBill createWayBill(WayBillDto wayBillDto) throws InvoiceException, CarException {
        //todo
        if (invoiceRepository.existsById(wayBillDto.getInvoiceNumber())) {
            throw new InvoiceException("invoice with number " + wayBillDto.getInvoiceNumber() + " not exist");
        }
        if (carRepository.findById(wayBillDto.getCarId()).isEmpty()) {
            throw new CarException("car with id " + wayBillDto.getCarId() + " not exist");
        }
        wayBillDto.getCheckpointDtos().forEach(checkpointService::createCheckpoint);

        WayBill wayBill = wayBillMapper.toModel(wayBillDto);

        wayBillRepository.save(wayBill);
        log.info("WAYBILL WITH ID {} CREATED", wayBill.getId());
        return wayBill;
    }

    public void reachCheckpoint(Long id) throws WayBillException {

        Optional<WayBill> wayBillOpt = wayBillRepository.findById(id);
        if (wayBillOpt.isEmpty()) {
            throw new WayBillException("waybill with id " + id + " not exist");
        }
        WayBill wayBill = wayBillOpt.get();
        wayBill.setCarriageStatuses(Collections.singleton(CarriageStatus.FINISHED_CARRIAGE));
        wayBillRepository.save(wayBill);
        log.info("CHECKPOINT IN WAYBILL WITH ID {} CREATED", id);
    }

    public Page<WayBill> getAllWayBills(CarriageStatus carriageStatus, Pageable pageable) {
        return wayBillRepository.findAll(getFindingPredicate(carriageStatus),pageable);
    }

    public WayBill getWayBill(Long id) throws WayBillException {
        Optional<WayBill> wayBillOpt = wayBillRepository.findById(id);
        if (wayBillOpt.isEmpty()) {
            throw new WayBillException("waybill with id " + id + " not exist");
        }
        return wayBillOpt.get();
    }

    private Predicate getFindingPredicate(CarriageStatus carriageStatus) {
        return QPredicate.builder()
                .add(carriageStatus, QWayBill.wayBill.carriageStatuses::contains)
                .buildAnd();
    }

}
