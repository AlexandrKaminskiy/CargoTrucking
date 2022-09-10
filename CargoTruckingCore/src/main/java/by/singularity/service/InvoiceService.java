package by.singularity.service;

import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Invoice;
import by.singularity.exception.InvoiceException;
import by.singularity.mapper.InvoiceMapper;
import by.singularity.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoiceRepository.save(invoice);
        log.info("INVOICE WITH NUMBER {} CREATED", invoice.getNumber());
        return invoice;
    }

    public void updateInvoice(InvoiceDto invoiceDto, String number) throws InvoiceException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(number);
        if (invoiceOpt.isEmpty()) {
            throw new InvoiceException("invoice with number " + number + "not found");
        }
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoiceRepository.save(invoice);
        log.info("INVOICE WITH NUMBER {} UPDATED", number);
    }

    public List<Invoice> getAllInvoices() {
        //todo
        return invoiceRepository.findAll();
    }

    //todo пофиксить
    public void deleteInvoice(String number) {
        invoiceRepository.deleteById(number);
        log.info("INVOICE WITH NUMBER {} DELETED", number);
    }

    public Invoice getInvoice(String number) throws InvoiceException {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(number);
        if (invoiceOpt.isEmpty()) {
            throw new InvoiceException("invoice with number" + number + "not found");
        }
        return invoiceOpt.get();
    }
}