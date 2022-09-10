package by.singularity.service;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.WayBill;
import by.singularity.exception.CarException;
import by.singularity.exception.InvoiceException;
import by.singularity.exception.WayBillException;
import by.singularity.mapper.WayBillMapper;
import by.singularity.repository.CarRepository;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.WayBillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<WayBill> getAllWayBills() {
        //todo
        return wayBillRepository.findAll();
    }


    public WayBill getWayBill(Long id) throws WayBillException {
        Optional<WayBill> wayBillOpt = wayBillRepository.findById(id);
        if (wayBillOpt.isEmpty()) {
            throw new WayBillException("waybill with id " + id + " not exist");
        }
        return wayBillOpt.get();
    }

}
