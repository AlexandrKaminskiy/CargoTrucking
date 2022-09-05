package by.singularity.service;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.WayBill;
import by.singularity.mapper.WayBillMapper;
import by.singularity.repository.impl.CarRepository;
import by.singularity.repository.impl.InvoiceRepository;
import by.singularity.repository.impl.WayBillRepository;
import by.singularity.repository.jparepo.WayBillJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//TODO TEST
public class WayBillService {
    private final WayBillRepository wayBillRepository;
    private final WayBillJpaRepository wayBillJpaRepository;
    private final CheckpointService checkpointService;
    private final WayBillMapper wayBillMapper;
    private final InvoiceRepository invoiceRepository;
    private final CarRepository carRepository;

    public WayBill createWayBill(WayBillDto wayBillDto) {
        //todo
        if (invoiceRepository.findById(wayBillDto.getInvoiceId()).isEmpty()) {
            return null;
        }
        if (carRepository.findById(wayBillDto.getCarId()).isEmpty()) {
            return null;
        }
        Set<Checkpoint> checkpoints = wayBillDto.getCheckpointDtos().stream()
                .map(checkpointService::createCheckpoint)
                .collect(Collectors.toSet());
        WayBill wayBill = wayBillMapper.toModel(wayBillDto);
        wayBillJpaRepository.save(wayBill);
        return wayBill;
    }

    public void reachCheckpoint(Long id) {
        //todo
        Optional<WayBill> wayBillOpt = wayBillRepository.findById(id);
        if (wayBillOpt.isEmpty()) {
            return;
        }
        WayBill wayBill = wayBillOpt.get();
        wayBill.setCarriageStatuses(Collections.singleton(CarriageStatus.FINISHED_CARRIAGE));
        wayBillJpaRepository.save(wayBill);
    }
    public List<WayBill> getAllWayBills() {
        //todo
        return wayBillRepository.findAll();
    }


    public WayBill getWayBill(Long id) {
        Optional<WayBill> wayBillOpt = wayBillRepository.findById(id);
        if (wayBillOpt.isEmpty()) {
            //todo
            return null;
        }
        return wayBillOpt.get();
    }

}
