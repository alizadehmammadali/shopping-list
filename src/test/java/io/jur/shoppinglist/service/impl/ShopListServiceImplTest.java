package io.jur.shoppinglist.service.impl;

import io.jur.shoppinglist.common.BaseTestCase;
import io.jur.shoppinglist.dao.entity.ShopListEntity;
import io.jur.shoppinglist.dao.repository.ShopListRepository;
import io.jur.shoppinglist.exception.ResourceAlreadyExistsException;
import io.jur.shoppinglist.exception.ResourceNotFoundException;
import io.jur.shoppinglist.mapper.ShopListMapper;
import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.general.SearchResponse;
import io.jur.shoppinglist.service.ShopListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopListServiceImplTest extends BaseTestCase {

    @Mock
    private ShopListRepository shopListRepository;

    private ShopListDTO shopListDTO;

    private ShopListService shopListService;

    @BeforeEach
    void setUp() {
        shopListDTO = new ShopListDTO();
        shopListDTO.setName(SHOP_LIST_NAME);
        shopListService = new ShopListServiceImpl(shopListRepository);
    }

    @Test
    @DisplayName("create shop list")
    void shouldCreateShopList() {
        when(shopListRepository.findShopListEntityByName(SHOP_LIST_NAME)).thenReturn(Optional.empty());
        shopListService.createOrUpdateShopList(shopListDTO);
        verify(shopListRepository, times(1)).findShopListEntityByName(SHOP_LIST_NAME);
        verify(shopListRepository, times(1)).save(any(ShopListEntity.class));
    }

    @Test
    @DisplayName("create shop list that is already exists")
    void shouldNotCreateShopListWhenNameExistsInDatabaseAndThrowsException() {
        when(shopListRepository.findShopListEntityByName(SHOP_LIST_NAME)).thenReturn(Optional.of(ShopListMapper.INSTANCE.toEntity(shopListDTO)));
        assertThrows(ResourceAlreadyExistsException.class, () -> shopListService.createOrUpdateShopList(shopListDTO));
    }

    @Test
    void shouldGetShopLists() {
        String name = "";
        int pageIndex = 0;
        int pageSize = 1;
        shopListDTO.setId(SHOP_LIST_ID);
        Page<ShopListEntity> response = new PageImpl<>(Collections.singletonList(ShopListMapper.INSTANCE.toEntity(shopListDTO)));
        when(shopListRepository.getShopListEntitiesByName(name, PageRequest.of(pageIndex, pageSize))).thenReturn(response);
        SearchResponse<List<ShopListDTO>> shopLists = shopListService.getShopLists(name, pageIndex, pageSize);
        verify(shopListRepository, times(1)).getShopListEntitiesByName(name, PageRequest.of(pageIndex, pageSize));
        assertEquals(1, shopLists.getData().size());
        assertEquals(SHOP_LIST_ID, shopLists.getData().get(0).getId());
        assertEquals(SHOP_LIST_NAME, shopLists.getData().get(0).getName());
    }

    @Test
    void shouldDeleteShopListWhenRecordExists() {
        shopListDTO.setId(SHOP_LIST_ID);
        Optional<ShopListEntity> entity = Optional.of(ShopListMapper.INSTANCE.toEntity(shopListDTO));
        when(shopListRepository.findById(SHOP_LIST_ID)).thenReturn(entity);
        shopListService.deleteShopList(SHOP_LIST_ID);
        verify(shopListRepository, times(1)).delete(entity.get());
    }

    @Test
    void shouldNotDeleteShopListWhenRecordDoesNotExists() {
        shopListDTO.setId(SHOP_LIST_ID);
        when(shopListRepository.findById(SHOP_LIST_ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> shopListService.deleteShopList(SHOP_LIST_ID));
    }
}