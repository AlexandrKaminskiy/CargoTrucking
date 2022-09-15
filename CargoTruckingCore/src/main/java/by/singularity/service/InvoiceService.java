package by.singularity.service;

import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Invoice;
import by.singularity.entity.InvoiceStatus;
import by.singularity.entity.QInvoice;
import by.singularity.exception.InvoiceException;
import by.singularity.exception.ProductOwnerException;
import by.singularity.exception.StorageException;
import by.singularity.exception.UserException;
import by.singularity.mapper.impl.InvoiceMapper;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(InvoiceDto invoiceDto) throws ProductOwnerException, UserException, StorageException {
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoice.setCreationDate(new Date());
        invoice.setStatus(Collections.singleton(InvoiceStatus.MADE_OUT));
        invoiceRepository.save(invoice);
        log.info("INVOICE WITH NUMBER {} CREATED", invoice.getNumber());
        return invoice;
    }

    public void updateInvoice(InvoiceDto invoiceDto, String number) throws InvoiceException, ProductOwnerException, UserException, StorageException {
        Invoice invoice = invoiceRepository.findById(number)
                .orElseThrow(()->new InvoiceException("invoice with number " + number + "not found"));
        Invoice updatedInvoice = invoiceMapper.toModel(invoiceDto);
        invoice.setDriver(updatedInvoice.getDriver());
        invoice.setStorage(updatedInvoice.getStorage());
        invoice.setCreator(updatedInvoice.getCreator());
        invoice.setProductOwner(updatedInvoice.getProductOwner());
        invoice.setProducts(updatedInvoice.getProducts());
        invoiceRepository.save(invoice);
        log.info("INVOICE WITH NUMBER {} UPDATED", number);
    }

    public Page<Invoice> getAllInvoices(Map<String,String> params, Pageable pageable) {
        return invoiceRepository.findAll(getFindingPredicate(params), pageable);
    }

    public void deleteInvoice(String number) {
        invoiceRepository.deleteById(number);
        log.info("INVOICE WITH NUMBER {} DELETED", number);
    }

    public Invoice getInvoice(String number) throws InvoiceException {
        return invoiceRepository.findById(number)
                .orElseThrow(()->new InvoiceException("invoice with number " + number + "not found"));

    }

    private Predicate getFindingPredicate(Map<String, String> params) {
        return QPredicate.builder()
                .add(params.get("number"), QInvoice.invoice.number::eq)
                .add(ParseUtils.parseDate(params.get("beforeCreationDate")), QInvoice.invoice.creationDate::goe)
                .add(ParseUtils.parseDate(params.get("afterCreationDate")), QInvoice.invoice.creationDate::loe)
                .add(ParseUtils.parseDate(params.get("beforeVerifiedDate")), QInvoice.invoice.verifiedDate::goe)
                .add(ParseUtils.parseDate(params.get("afterVerifiedDate")), QInvoice.invoice.verifiedDate::loe)
                .add(ParseUtils.parseEnum(params.get("status"),InvoiceStatus.class),QInvoice.invoice.status::contains)
                .buildAnd();
    }
}