package by.singularity.mapper.impl;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.WayBill;
import by.singularity.mapper.WayBillMapper;
import by.singularity.repository.impl.CarRepository;
import by.singularity.repository.impl.InvoiceRepository;
import by.singularity.repository.impl.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WayBillMapperImpl implements WayBillMapper {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public WayBill toModel(WayBillDto wayBillDto) {
        if ( wayBillDto == null ) {
            return null;
        }

        WayBill wayBill = new WayBill();

        wayBill.setDistance( wayBillDto.getDistance() );
        wayBill.setEndDate( wayBillDto.getEndDate() );
        wayBill.setInvoice(invoiceRepository.findById(wayBillDto.getInvoiceId()).get());
        wayBill.setVerifier(userRepository.findById(wayBillDto.getVerifierId()).get());
        wayBill.setCar(carRepository.findById(wayBillDto.getCarId()).get());
        Set<Checkpoint> set = wayBillDto.getCheckpoints();
        if ( set != null ) {
            wayBill.setCheckpoints( new LinkedHashSet<Checkpoint>( set ) );
        }
        Set<CarriageStatus> set1 = wayBillDto.getCarriageStatuses();
        if ( set1 != null ) {
            wayBill.setCarriageStatuses( new LinkedHashSet<CarriageStatus>( set1 ) );
        }

        return wayBill;
    }

    @Override
    public WayBillDto toDto(WayBill wayBill) {
        if ( wayBill == null ) {
            return null;
        }

        Set<CarriageStatus> carriageStatuses = null;
        Set<Checkpoint> checkpoints = null;
        Integer distance;
        Date endDate;

        Set<CarriageStatus> set = wayBill.getCarriageStatuses();
        if ( set != null ) {
            carriageStatuses = new LinkedHashSet<>(set);
        }
        Set<Checkpoint> set1 = wayBill.getCheckpoints();
        if ( set1 != null ) {
            checkpoints = new LinkedHashSet<>(set1);
        }
        distance = wayBill.getDistance();
        endDate = wayBill.getEndDate();

        String invoiceId = wayBill.getInvoice().getNumber();
        Long carId = wayBill.getCar().getId();
        Long verifierId = wayBill.getVerifier().getId();

        return new WayBillDto( invoiceId, distance, carId, endDate, verifierId, carriageStatuses, checkpoints );
    }
}
