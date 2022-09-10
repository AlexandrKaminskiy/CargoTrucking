package by.singularity.service;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.exception.ProductException;
import by.singularity.mapper.ProductMapper;
import by.singularity.repository.ProductRepository;
import by.singularity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        productRepository.save(product);
        return product;
    }

    public void updateProduct(ProductDto productDto, Long id) throws ProductException {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new ProductException("product with id"+ id +" not found");
        }
        Product product = productOpt.get();
        Optional.ofNullable(productDto.getAmount()).ifPresent(product::setAmount);
        Optional.ofNullable(productDto.getProductStatus()).ifPresent(product::setProductStatus);
        productRepository.save(product);
        log.info("PRODUCT WITH ID {} UPDATED", id);
    }

    public List<Product> getAllProducts() {
        //todo
        return productRepository.findAll();
    }
    //todo пофиксить
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        log.info("PRODUCT WITH ID {} DELETED", id);
    }

    public Product getProduct(Long id) throws ProductException {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new ProductException("product with id " + id + "not found");
        }
        return productOpt.get();
    }

}