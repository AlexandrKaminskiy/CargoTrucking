package by.singularity.service;

import by.singularity.dto.InvoiceDto;
import by.singularity.dto.ProductDto;
import by.singularity.entity.Invoice;
import by.singularity.entity.Product;
import by.singularity.mapper.InvoiceMapper;
import by.singularity.repository.impl.InvoiceRepository;
import by.singularity.repository.jparepo.InvoiceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceMapper invoiceMapper;
    private final InvoiceJpaRepository invoiceJpaRepository;
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoiceJpaRepository.save(invoice);
        return invoice;
    }

    public void updateInvoice(InvoiceDto invoiceDto, String number) {
        //todo
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(number);
        if (invoiceOpt.isEmpty()) {
            return;
        }
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoiceJpaRepository.save(invoice);
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