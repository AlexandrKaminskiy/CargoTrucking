package by.singularity.controller;


import by.singularity.dto.InvoiceDto;
import by.singularity.entity.Invoice;
import by.singularity.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    //todo add pagination
    @GetMapping()
    public void getAll(HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<Invoice> invoices = invoiceService.getAllInvoices();
        responseMap.put("content", invoices);
        responseMap.put("totalElements", invoices.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{number}")
    public Invoice getById(@PathVariable String number) {
        return invoiceService.getInvoice(number);
    }


    @PostMapping()
    public String addInvoice(@RequestBody @Valid InvoiceDto invoiceDto) {
        Invoice invoice = invoiceService.createInvoice(invoiceDto);
        return "/api/invoices/" + invoice.getNumber();
    }

    @PutMapping("/{number}")
    public void updateInvoice(@PathVariable String number, @RequestBody InvoiceDto invoiceDto) {
        invoiceService.updateInvoice(invoiceDto, number);
    }

    @DeleteMapping("/{number}")
    public void deleteInvoice(@PathVariable String number) {
        invoiceService.deleteInvoice(number);
    }
}