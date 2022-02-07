package io.jur.shoppinglist.service.impl;

import io.jur.shoppinglist.dao.entity.ProductEntity;
import io.jur.shoppinglist.dao.repository.ProductRepository;
import io.jur.shoppinglist.exception.ResourceNotFoundException;
import io.jur.shoppinglist.mapper.ProductMapper;
import io.jur.shoppinglist.model.ProductDTO;
import io.jur.shoppinglist.model.enums.Status;
import io.jur.shoppinglist.service.ProductService;
import io.jur.shoppinglist.service.ShopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ShopListService shopListService;

    @Override
    public ProductDTO createOrUpdateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = ProductMapper.INSTANCE.toEntity(productDTO);
        if (productDTO.getShopListDTO() != null && productDTO.getShopListDTO().getId() != null) {
            productEntity.setShopListEntity(shopListService.getShopListEntityById(productDTO.getShopListDTO().getId()));
        }
        ProductEntity entity = productRepository.save(productEntity);
        return ProductMapper.INSTANCE.toDTO(entity);
    }

    @Override
    public List<ProductDTO> getProductsByShopList(Long shopListId) {
        return productRepository.findProductEntitiesByShopListEntityId(shopListId)
                .stream().map(ProductMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.delete(getEntity(id));
    }

    @Override
    public void toggleProductStatus(long id) {
        ProductEntity entity = getEntity(id);
        if (entity.getStatus() == Status.ACTIVE) {
            entity.setStatus(Status.INACTIVE);
        } else {
            entity.setStatus(Status.ACTIVE);
        }
        productRepository.save(entity);
    }

    private ProductEntity getEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ProductEntity.class, "id", id));
    }
}
