package by.singularity.service;

import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Invoice;
import by.singularity.mapper.InvoiceMapper;
import by.singularity.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoiceRepository.save(invoice);
        return invoice;
    }

    public void updateInvoice(InvoiceDto invoiceDto, String number) {
        //todo
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(number);
        if (invoiceOpt.isEmpty()) {
            return;
        }
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        //todo
        return invoiceRepository.findAll();
    }
    //todo пофиксить
    public void deleteInvoice(String number) {
        invoiceRepository.deleteById(number);
    }

    public Invoice getInvoice(String number) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(number);
        if (invoiceOpt.isEmpty()) {
            //todo
            return null;
        }
        return invoiceOpt.get();
    }
}