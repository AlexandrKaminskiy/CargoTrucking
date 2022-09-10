package by.singularity.controller;

import by.singularity.dto.ClientDto;
import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.Client;
import by.singularity.entity.ProductOwner;
import by.singularity.exception.ProductOwnerException;
import by.singularity.service.ProductOwnerService;
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
@RequestMapping("api/product-owners")
@RequiredArgsConstructor
public class ProductOwnerController {

    private final ProductOwnerService productOwnerService;

    //todo add pagination
    @GetMapping()
    public void getAll(HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<ProductOwner> clients = productOwnerService.getAllProductOwners();
        responseMap.put("content", clients);
        responseMap.put("totalElements", clients.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public ProductOwner getById(@PathVariable Long id) throws ProductOwnerException {
        return productOwnerService.getProductOwner(id);
    }


    @PostMapping()
    public String addProductOwner(@RequestBody @Valid ProductOwnerDto productOwnerDto) {
        Long createdId = productOwnerService.createProductOwner(productOwnerDto);
        return "/api/clients/" + createdId;
    }

    @PutMapping("/{id}")
    public void updateProductOwner(@PathVariable Long id,
                                   @RequestBody ProductOwnerDto productOwnerDto) throws ProductOwnerException {
        productOwnerService.updateProductOwner(productOwnerDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductOwner(@PathVariable Long id) {
        productOwnerService.deleteProductOwner(id);
    }
}