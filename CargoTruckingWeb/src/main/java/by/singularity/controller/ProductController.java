package by.singularity.controller;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.exception.ProductException;
import by.singularity.exception.UserException;
import by.singularity.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/product-writeoffs")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public void getAll(HttpServletResponse response,
                       @RequestParam Map<String,String> params,
                       Pageable pageable) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        Page<Product> products = productService.getAllProducts(params,pageable);
        responseMap.put("content", products);
        responseMap.put("totalElements", products.getContent().size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) throws ProductException {
        return productService.getProduct(id);
    }

    @PostMapping()
    public String addProduct(HttpServletRequest request,
                             @RequestBody @Valid ProductDto productDto) throws UserException {
        Product product = productService.createProduct(request,productDto);
        return "/api/product-writeoffs/" + product.getId();
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id,
                             @RequestBody @Valid ProductDto productDto) throws ProductException {
        productService.updateProduct(productDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}