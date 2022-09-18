package by.singularity.service;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductStatus;
import by.singularity.entity.QProduct;
import by.singularity.entity.User;
import by.singularity.exception.ProductException;
import by.singularity.exception.UserException;
import by.singularity.mapper.impl.ProductMapper;
import by.singularity.repository.ProductRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final UserService userService;
    @Transactional
    public Product createProduct(User creator, ProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        product.setCreator(creator);
        productRepository.save(product);
        log.info("PRODUCT WITH ID {} CREATED", product.getId());
        return product;
    }

    public Product createProduct(HttpServletRequest request, ProductDto productDto) throws UserException {
        User creator = userService.getUserByAuthorization(request);
        return createProduct(creator, productDto);
    }

    @Transactional
    public Product createProduct(Product product) {
        productRepository.save(product);
        log.info("INVOICE PRODUCT WITH ID {} CREATED", product.getId());
        return product;
    }

    public Set<Product> createProducts(Set<Product> products) {
        productRepository.saveAll(products);
        log.info("RESERVED PRODUCTS CREATED");
        return products;
    }

    @Transactional
    public void updateProduct(ProductDto productDto, Long id) throws ProductException {
        Product product = productRepository.findById(id)
                        .orElseThrow(()->new ProductException("product with id" + id +" not found"));
        Optional.ofNullable(productDto.getAmount()).ifPresent(product::setAmount);
        Optional.ofNullable(productDto.getProductStatus()).ifPresent(product::setProductStatus);
        productRepository.save(product);
        log.info("PRODUCT WITH ID {} UPDATED", id);
    }

    public Page<Product> getAllProducts(Map<String, String> params, Pageable pageable) {
        return productRepository.findAll(getFindingPredicate(params),pageable);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        log.info("PRODUCT WITH ID {} DELETED", id);
    }

    public Product getProduct(Long id) throws ProductException {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductException("product with id " + id + "not found"));
    }

    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("name"), QProduct.product.name::eq)
                .add(ParseUtils.parseInt(params.get("amount")), QProduct.product.amount::goe)
                .add(ParseUtils.parseEnum(params.get("productStatus"), ProductStatus.class), QProduct.product.productStatus::contains)
                .buildAnd();
    }


}