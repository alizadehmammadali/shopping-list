package io.jur.shoppinglist.service.impl;

import io.jur.shoppinglist.dao.entity.ShopListEntity;
import io.jur.shoppinglist.dao.repository.ShopListRepository;
import io.jur.shoppinglist.exception.ResourceAlreadyExistsException;
import io.jur.shoppinglist.exception.ResourceNotFoundException;
import io.jur.shoppinglist.mapper.ShopListMapper;
import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.general.SearchResponse;
import io.jur.shoppinglist.service.ShopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopListServiceImpl implements ShopListService {

    private final ShopListRepository shopListRepository;

    @Override
    public ShopListDTO createOrUpdateShopList(ShopListDTO shopListDTO) {
        if (shopListDTO.getId() == null && shopListRepository.findShopListEntityByName(shopListDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException(ShopListEntity.class, "name", shopListDTO.getName());
        }
        return ShopListMapper.INSTANCE.toDTO(shopListRepository.save(ShopListMapper.INSTANCE.toEntity(shopListDTO)));
    }

    @Override
    public ShopListEntity getShopListEntityById(Long shopListId) {
        return getEntity(shopListId);
    }

    @Override
    public SearchResponse<List<ShopListDTO>> getShopLists(String name, Integer pageIndex, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        Page<ShopListEntity> shopListEntitiesByName = shopListRepository.getShopListEntitiesByName(name, pageable);
        return ShopListMapper.INSTANCE.toSearchResponse(shopListEntitiesByName);
    }

    @Override
    public void deleteShopList(Long id) {
        ShopListEntity entity = getEntity(id);
        shopListRepository.delete(entity);
    }

    private ShopListEntity getEntity(Long id) {
        return shopListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShopListEntity.class, "id", id));
    }

}
