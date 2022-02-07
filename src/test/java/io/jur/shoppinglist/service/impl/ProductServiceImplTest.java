package io.jur.shoppinglist.service.impl;

import io.jur.shoppinglist.common.BaseTestCase;
import io.jur.shoppinglist.dao.entity.ProductEntity;
import io.jur.shoppinglist.dao.repository.ProductRepository;
import io.jur.shoppinglist.exception.ResourceNotFoundException;
import io.jur.shoppinglist.mapper.ProductMapper;
import io.jur.shoppinglist.mapper.ShopListMapper;
import io.jur.shoppinglist.model.ProductDTO;
import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.enums.Status;
import io.jur.shoppinglist.service.ProductService;
import io.jur.shoppinglist.service.ShopListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class ProductServiceImplTest extends BaseTestCase {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShopListService shopListService;

    @Captor
    private ArgumentCaptor<ProductEntity> captor;

    private ProductService productService;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setName(PRODUCT_NAME);
        productDTO.setQuantity(PRODUCT_QUANTITY);
        productDTO.setQuantityUnit(PRODUCT_QUANTITY_UNIT);
        productDTO.setStatus(PRODUCT_STATUS);
        productService = new ProductServiceImpl(productRepository, shopListService);
    }

    @Test
    void shouldCreateProductWithoutShopList() {
        productDTO.setId(PRODUCT_ID);
        ProductEntity entity = ProductMapper.INSTANCE.toEntity(productDTO);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);
        ProductDTO product = productService.createOrUpdateProduct(productDTO);
        assertEquals("CHECKED", PRODUCT_NAME, product.getName());
        assertEquals("CHECKED", PRODUCT_QUANTITY, product.getQuantity());
        assertEquals("CHECKED", PRODUCT_QUANTITY_UNIT, product.getQuantityUnit());
        assertEquals("CHECKED", PRODUCT_STATUS, product.getStatus());
    }

    @Test
    void shouldCreateProductWithShopList() {
        ShopListDTO shopListDTO = new ShopListDTO();
        shopListDTO.setId(SHOP_LIST_ID);
        shopListDTO.setName(SHOP_LIST_NAME);
        productDTO.setShopListDTO(shopListDTO);
        productDTO.setId(PRODUCT_ID);
        ProductEntity entity = ProductMapper.INSTANCE.toEntity(productDTO);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(entity);
        when(shopListService.getShopListEntityById(SHOP_LIST_ID)).thenReturn(ShopListMapper.INSTANCE.toEntity(shopListDTO));
        ProductDTO product = productService.createOrUpdateProduct(productDTO);
        assertEquals("CHECKED", PRODUCT_NAME, product.getName());
        assertEquals("CHECKED", PRODUCT_QUANTITY, product.getQuantity());
        assertEquals("CHECKED", PRODUCT_QUANTITY_UNIT, product.getQuantityUnit());
        assertEquals("CHECKED", PRODUCT_STATUS, product.getStatus());
        assertEquals("CHECKED", PRODUCT_ID, product.getId());
    }

    @Test
    void shouldGetProductsByShopList() {
        ShopListDTO shopListDTO = new ShopListDTO();
        shopListDTO.setId(SHOP_LIST_ID);
        shopListDTO.setName(SHOP_LIST_NAME);
        productDTO.setShopListDTO(shopListDTO);
        productDTO.setId(PRODUCT_ID);
        ProductEntity entity = ProductMapper.INSTANCE.toEntity(productDTO);
        List<ProductEntity> entities = Collections.singletonList(entity);
        when(productRepository.findProductEntitiesByShopListEntityId(SHOP_LIST_ID)).thenReturn(entities);
        List<ProductDTO> products = productService.getProductsByShopList(SHOP_LIST_ID);
        verify(productRepository, times(1)).findProductEntitiesByShopListEntityId(SHOP_LIST_ID);
        assertEquals("CHECKED", 1, products.size());
    }

    @Test
    void shouldToggleProductStatus() {
        ShopListDTO shopListDTO = new ShopListDTO();
        shopListDTO.setId(SHOP_LIST_ID);
        shopListDTO.setName(SHOP_LIST_NAME);
        productDTO.setShopListDTO(shopListDTO);
        productDTO.setId(PRODUCT_ID);
        ProductEntity entity = ProductMapper.INSTANCE.toEntity(productDTO);
        when(productRepository.findById(PRODUCT_ID)).thenReturn(ofNullable(entity));
        assertSame(productDTO.getStatus(), Status.ACTIVE);

        productService.toggleProductStatus(PRODUCT_ID);

        verify(productRepository, times(1)).findById(PRODUCT_ID);
        verify(productRepository).save(captor.capture());
        verifyNoMoreInteractions(productRepository);
        assertSame(captor.getValue().getStatus(), Status.INACTIVE);

        productService.toggleProductStatus(PRODUCT_ID);
        assertSame(captor.getValue().getStatus(), Status.ACTIVE);

    }

    @Test
    void shouldDeleteProductWhenRecordExists() {
        ShopListDTO shopListDTO = new ShopListDTO();
        shopListDTO.setId(SHOP_LIST_ID);
        shopListDTO.setName(SHOP_LIST_NAME);
        productDTO.setShopListDTO(shopListDTO);
        productDTO.setId(PRODUCT_ID);
        Optional<ProductEntity> entity = Optional.of(ProductMapper.INSTANCE.toEntity(productDTO));
        when(productRepository.findById(PRODUCT_ID)).thenReturn(entity);
        productService.deleteProductById(PRODUCT_ID);
        verify(productRepository, times(1)).delete(entity.get());
    }

    @Test
    void shouldNotDeleteProductWhenRecordDoesNotExists() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProductById(SHOP_LIST_ID));
    }

}