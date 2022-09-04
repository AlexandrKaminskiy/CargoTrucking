package by.singularity.service;

import by.singularity.dto.ProductOwnerDto;
import by.singularity.dto.StorageDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductOwner;
import by.singularity.entity.Storage;
import by.singularity.mapper.ProductOwnerMapper;
import by.singularity.mapper.StorageMapper;
import by.singularity.repository.impl.ProductOwnerRepository;
import by.singularity.repository.impl.StorageRepository;
import by.singularity.repository.jparepo.ProductJpaRepository;
import by.singularity.repository.jparepo.ProductOwnerJpaRepository;
import by.singularity.repository.jparepo.StorageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOwnerService {
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductOwnerJpaRepository productOwnerJpaRepository;
    private final ProductOwnerMapper productOwnerMapper;
    private final ProductService productService;

    @Transactional
    public Long createProductOwner(ProductOwnerDto productOwnerDto) {
        //todo
        ProductOwner productOwner = productOwnerMapper.toModel(productOwnerDto);
        Set<Product> products = productOwnerDto.getProducts()
                .stream()
                .map(productService::createProduct)
                .collect(Collectors.toSet());
        productOwner.setProducts(products);
        productOwnerJpaRepository.save(productOwner);
        return productOwner.getId();
    }

    public void updateProductOwner(ProductOwnerDto productOwnerDto, Long id) {
        //todo
        Optional<ProductOwner> productOwnerOpt = productOwnerRepository.findById(id);
        if (productOwnerOpt.isEmpty()) {
            return;
        }
        ProductOwner productOwner = productOwnerOpt.get();
        Optional.ofNullable(productOwnerDto.getName()).ifPresent(productOwner::setName);
        Optional.ofNullable(productOwnerDto.getProducts())
                .ifPresent((productDtos)-> {
                    //todo delete by product owner id
                    Set<Product> products = productDtos
                            .stream()
                            .map(productService::createProduct)
                            .collect(Collectors.toSet());
                    productOwner.setProducts(products);
                });
        productOwnerJpaRepository.save(productOwner);
    }

    public List<ProductOwner> getAllProductOwners() {
        //todo
        return productOwnerRepository.findAll();
    }

    public void deleteProductOwner(Long id) {
        productOwnerRepository.deleteById(id);
    }

    public ProductOwner getProductOwner(Long id) {
        Optional<ProductOwner> productOwnerOpt = productOwnerRepository.findById(id);
        if (productOwnerOpt.isEmpty()) {
            //todo
            return null;
        }
        return productOwnerOpt.get();
    }

}