package by.singularity.controller;

import by.singularity.dto.ClientDto;
import by.singularity.dto.ProductDto;
import by.singularity.entity.Client;
import by.singularity.entity.Product;
import by.singularity.service.ProductService;
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
@RequestMapping("api/product-writeoffs")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //todo add pagination
    @GetMapping()
    public void getAll(HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        responseMap.put("content", products);
        responseMap.put("totalElements", products.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getProduct(id);
    }


    @PostMapping()
    public String addProduct(@RequestBody @Valid ProductDto productDto) {
        Product product = productService.createProduct(productDto);
        return "/api/clients/" + product.getId();
    }

    @PutMapping("/{id}")
    public void updateClient(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productService.updateProduct(productDto, id);
    }
    @GetMapping("/test")
    public void test() {
        productService.get(1L);

    }
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}