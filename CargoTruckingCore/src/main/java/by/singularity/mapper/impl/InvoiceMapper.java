package by.singularity.mapper.impl;

import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Invoice;
import by.singularity.mapper.Mapper;
import by.singularity.repository.ProductOwnerRepository;
import by.singularity.repository.StorageRepository;
import by.singularity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InvoiceMapper implements Mapper<Invoice, InvoiceDto> {
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;
    private final ProductOwnerRepository productOwnerRepository;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Invoice.class, InvoiceDto.class)
                .addMappings(m->m.skip(InvoiceDto::setDriverId))
                .addMappings(m->m.skip(InvoiceDto::setStorageId))
                .addMappings(m->m.skip(InvoiceDto::setProductOwnerId))
                .addMappings(m->m.skip(InvoiceDto::setProducts))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(InvoiceDto.class, Invoice.class)
                .addMappings(m->m.skip(Invoice::setDriver))
                .addMappings(m->m.skip(Invoice::setStorage))
                .addMappings(m->m.skip(Invoice::setProductOwner))
                .addMappings(m->m.skip(Invoice::setProducts))
                .setPostConverter(toModelConverter());
    }

    @Override
    public Invoice toModel(InvoiceDto invoiceDto) {
        return Objects.isNull(invoiceDto) ? null : mapper.map(invoiceDto, Invoice.class);
    }

    @Override
    public InvoiceDto toDto(Invoice invoice) {
        return Objects.isNull(invoice) ? null : mapper.map(invoice, InvoiceDto.class);
    }

    private Converter<InvoiceDto, Invoice> toModelConverter() {
        return context -> {
            InvoiceDto source = context.getSource();
            Invoice destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Invoice, InvoiceDto> toDtoConverter() {
        return context -> {
            Invoice source = context.getSource();
            InvoiceDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Invoice source, InvoiceDto destination) {
        destination.setDriverId(source.getDriver().getId());
        destination.setStorageId(source.getStorage().getId());
        destination.setProductOwnerId(source.getProductOwner().getId());
    }

    private void mapSpecificFields(InvoiceDto source, Invoice destination) {
        destination.setDriver(userRepository.findById(source.getDriverId()).orElse(null));
        destination.setStorage(storageRepository.findById(source.getStorageId()).orElse(null));
        destination.setProductOwner(productOwnerRepository.findById(source.getProductOwnerId()).orElse(null));
    }
}
