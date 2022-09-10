package by.singularity.service;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.mapper.ProductMapper;
import by.singularity.repository.ProductRepository;
import by.singularity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        productRepository.save(product);
        return product;
    }

    public void updateProduct(ProductDto productDto, Long id) {
        //todo
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return;
        }
        Product product = productOpt.get();
        Optional.ofNullable(productDto.getAmount()).ifPresent(product::setAmount);
        Optional.ofNullable(productDto.getProductStatus()).ifPresent(product::setProductStatus);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        //todo
        return productRepository.findAll();
    }
    //todo пофиксить
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            //todo
            return null;
        }
        return productOpt.get();
    }

    public Product get(Long id) {
        Iterable<Product> products = productRepository.findAll();
        Product product = new Product(2L,"qwe",5,userRepository.findById(4L).get(), Collections.emptySet());
        productRepository.save(product);
        return null;
    }
}