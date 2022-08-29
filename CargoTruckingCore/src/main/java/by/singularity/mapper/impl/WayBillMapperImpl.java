package by.singularity.mapper.impl;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.WayBill;
import by.singularity.mapper.WayBillMapper;

import javax.annotation.Generated;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-30T01:49:14+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class WayBillMapperImpl implements WayBillMapper {

    @Override
    public WayBill toModel(WayBillDto wayBillDto) {
        if ( wayBillDto == null ) {
            return null;
        }

        WayBill wayBill = new WayBill();

        wayBill.setDistance( wayBillDto.getDistance() );
        wayBill.setEndDate( wayBillDto.getEndDate() );
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
        Integer distance = null;
        Date endDate = null;

        Set<CarriageStatus> set = wayBill.getCarriageStatuses();
        if ( set != null ) {
            carriageStatuses = new LinkedHashSet<CarriageStatus>( set );
        }
        Set<Checkpoint> set1 = wayBill.getCheckpoints();
        if ( set1 != null ) {
            checkpoints = new LinkedHashSet<Checkpoint>( set1 );
        }
        distance = wayBill.getDistance();
        endDate = wayBill.getEndDate();

        Long invoiceId = null;
        Long carId = null;
        Long verifierId = null;

        WayBillDto wayBillDto = new WayBillDto( invoiceId, distance, carId, endDate, verifierId, carriageStatuses, checkpoints );

        return wayBillDto;
    }
}
