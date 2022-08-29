package by.singularity.mapper.impl;

import by.singularity.dto.InvoiceDto;
import by.singularity.dto.ProductDto;
import by.singularity.entity.Invoice;
import by.singularity.entity.Product;
import by.singularity.entity.ProductStatus;
import by.singularity.mapper.InvoiceMapper;
import by.singularity.repository.impl.InvoiceRepository;
import by.singularity.repository.impl.StorageRepository;
import by.singularity.repository.impl.UserRepository;
import by.singularity.repository.impl.WayBillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InvoiceMapperImpl implements InvoiceMapper {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;
    private final WayBillRepository wayBillRepository;
    //todo доделать
    @Override
    public Invoice toModel(InvoiceDto invoiceDto) {
        if ( invoiceDto == null ) {
            return null;
        }

        Invoice invoice = new Invoice();

        invoice.setNumber( invoiceDto.getNumber() );
        invoice.setProducts( productDtoSetToProductSet( invoiceDto.getProducts() ) );
        invoice.setCreator(userRepository.findById(invoiceDto.getCreatorId()).get());
        invoice.setDriver(userRepository.findById(invoiceDto.getDriverId()).get());
        invoice.setStorage(storageRepository.findById(invoiceDto.getStorageId()).get());
        return invoice;
    }

    @Override
    public InvoiceDto toDto(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        Set<ProductDto> products = null;
        String number = null;

        products = productSetToProductDtoSet( invoice.getProducts() );
        number = invoice.getNumber();

        Long storageId = null;
        Long creatorId = null;
        Long driverId = null;

        return new InvoiceDto( number, storageId, creatorId, driverId, products );
    }

    protected Product productDtoToProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDto.getId() );
        product.setName( productDto.getName() );
        product.setAmount( productDto.getAmount() );
        Set<ProductStatus> set = productDto.getProductStatus();
        if ( set != null ) {
            product.setProductStatus( new LinkedHashSet<ProductStatus>( set ) );
        }

        return product;
    }

    protected Set<Product> productDtoSetToProductSet(Set<ProductDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Product> set1 = new LinkedHashSet<Product>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ProductDto productDto : set ) {
            set1.add( productDtoToProduct( productDto ) );
        }

        return set1;
    }

    protected ProductDto productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Set<ProductStatus> productStatus = null;
        Long id = null;
        String name = null;
        Integer amount = null;

        Set<ProductStatus> set = product.getProductStatus();
        if ( set != null ) {
            productStatus = new LinkedHashSet<ProductStatus>( set );
        }
        id = product.getId();
        name = product.getName();
        amount = product.getAmount();

        Long creatorId = null;

        ProductDto productDto = new ProductDto( id, name, amount, creatorId, productStatus );

        return productDto;
    }

    protected Set<ProductDto> productSetToProductDtoSet(Set<Product> set) {
        if ( set == null ) {
            return null;
        }

        Set<ProductDto> set1 = new LinkedHashSet<ProductDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Product product : set ) {
            set1.add( productToProductDto( product ) );
        }

        return set1;
    }
}
