package by.singularity.mapper;

import by.singularity.dto.ClientDto;
import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Client;
import by.singularity.entity.Invoice;
import org.mapstruct.Mapper;

@Mapper
public interface InvoiceMapper {
    Invoice toModel(InvoiceDto invoiceDto);
    InvoiceDto toDto(Invoice invoice);
}
