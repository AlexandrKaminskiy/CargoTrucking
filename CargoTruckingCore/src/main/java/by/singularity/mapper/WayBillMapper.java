package by.singularity.mapper;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.WayBill;
import org.mapstruct.Mapper;

@Mapper
public interface WayBillMapper {
    WayBill toModel(WayBillDto wayBillDto);
    WayBillDto toDto(WayBill wayBill);
}
