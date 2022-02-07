package io.jur.shoppinglist.service;

import io.jur.shoppinglist.dao.entity.ShopListEntity;
import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.general.SearchResponse;

import java.util.List;

public interface ShopListService {

    ShopListDTO createOrUpdateShopList(ShopListDTO shopListDTO);

    ShopListEntity getShopListEntityById(Long shopListId);

    SearchResponse<List<ShopListDTO>> getShopLists(String name, Integer pageIndex, Integer pageSize);

    void deleteShopList(Long id);

}
