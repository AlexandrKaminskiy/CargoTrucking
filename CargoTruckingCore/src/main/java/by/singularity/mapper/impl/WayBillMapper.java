package by.singularity.mapper.impl;

import by.singularity.dto.WayBillDto;
import by.singularity.entity.WayBill;
import by.singularity.mapper.Mapper;
import by.singularity.repository.CarRepository;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WayBillMapper implements Mapper<WayBill, WayBillDto> {
    private final ModelMapper mapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(WayBill.class, WayBillDto.class)
                .addMappings(m->m.skip(WayBillDto::setCarId))
                .addMappings(m->m.skip(WayBillDto::setVerifierId))
                .addMappings(m->m.skip(WayBillDto::setInvoiceNumber))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(WayBillDto.class, WayBill.class)
                .addMappings(m->m.skip(WayBill::setCar))
                .addMappings(m->m.skip(WayBill::setVerifier))
                .addMappings(m->m.skip(WayBill::setInvoice))
                .setPostConverter(toModelConverter());
    }

    @Override
    public WayBill toModel(WayBillDto wayBillDto) {
        return Objects.isNull(wayBillDto) ? null : mapper.map(wayBillDto, WayBill.class);
    }

    @Override
    public WayBillDto toDto(WayBill wayBill) {
        return Objects.isNull(wayBill) ? null : mapper.map(wayBill, WayBillDto.class);
    }

    private Converter<WayBillDto, WayBill> toModelConverter() {
        return context -> {
            WayBillDto source = context.getSource();
            WayBill destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<WayBill, WayBillDto> toDtoConverter() {
        return context -> {
            WayBill source = context.getSource();
            WayBillDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(WayBill source, WayBillDto destination) {
        destination.setCarId(source.getCar().getId());
        destination.setVerifierId(source.getVerifier().getId());
        destination.setInvoiceNumber(source.getInvoice().getNumber());
    }

    private void mapSpecificFields(WayBillDto source, WayBill destination) {
        destination.setCar(carRepository.findById(source.getCarId()).orElse(null));
        destination.setVerifier(userRepository.findById(source.getVerifierId()).orElse(null));
        destination.setInvoice(invoiceRepository.findById(source.getInvoiceNumber()).orElse(null));
    }
}
