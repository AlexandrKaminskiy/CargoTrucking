package by.singularity.controller;

import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.ProductOwner;
import by.singularity.exception.ProductOwnerException;
import by.singularity.service.ProductOwnerService;
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
@RequestMapping("api/product-owners")
@RequiredArgsConstructor
public class ProductOwnerController {

    private final ProductOwnerService productOwnerService;

    @GetMapping()
    public void getAll(HttpServletResponse response,
                       String name,
                       Pageable pageable) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        Page<ProductOwner> clients = productOwnerService.getAllProductOwners(pageable,name);
        responseMap.put("content", clients);
        responseMap.put("totalElements", clients.getContent().size());
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
                                   @RequestBody @Valid ProductOwnerDto productOwnerDto) throws ProductOwnerException {
        productOwnerService.updateProductOwner(productOwnerDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductOwner(@PathVariable Long id) {
        productOwnerService.deleteProductOwner(id);
    }
}