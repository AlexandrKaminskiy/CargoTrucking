package by.singularity.controller;


import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Invoice;
import by.singularity.exception.*;
import by.singularity.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping()
    public void getAll(HttpServletResponse response,
                       @RequestParam Map<String,String> params,
                       Pageable pageable) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        Page<Invoice> invoices = invoiceService.getAllInvoices(params,pageable);
        responseMap.put("content", invoices);
        responseMap.put("totalElements", invoices.getContent().size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{number}")
    public Invoice getById(@PathVariable String number) throws InvoiceException {
        return invoiceService.getInvoice(number);
    }


    @PostMapping()
    public String addInvoice(@RequestBody @Valid InvoiceDto invoiceDto) throws ProductOwnerException, UserException, StorageException, ProductException, ClientException {
        Invoice invoice = invoiceService.createInvoice(invoiceDto);
        return "/api/invoices/" + invoice.getNumber();
    }

    @PutMapping("/{number}")
    public void updateInvoice(@PathVariable String number,
                              @RequestBody @Valid InvoiceDto invoiceDto) throws InvoiceException, ProductOwnerException, UserException, StorageException, ProductException {
        invoiceService.updateInvoice(invoiceDto, number);
    }

    @DeleteMapping("/{number}")
    public void deleteInvoice(@PathVariable String number) {
        invoiceService.deleteInvoice(number);
    }
}