package io.jur.shoppinglist.mapper;

import io.jur.shoppinglist.dao.entity.ShopListEntity;
import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.general.SearchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShopListMapper {

    public static final ShopListMapper INSTANCE = Mappers.getMapper(ShopListMapper.class);

    public abstract ShopListEntity toEntity(ShopListDTO shopListDTO);

    public abstract ShopListDTO toDTO(ShopListEntity shopListEntity);

    public SearchResponse<List<ShopListDTO>> toSearchResponse(Page<ShopListEntity> shopListEntityPage) {
        if (shopListEntityPage == null) {
            return null;
        }
        SearchResponse<List<ShopListDTO>> response = new SearchResponse<>();
        response.setData(shopListEntityPage.getContent().stream().map(this::toDTO)
                .collect(Collectors.toList()));
        response.setSize(shopListEntityPage.getSize());
        response.setTotalPages(shopListEntityPage.getTotalPages());
        response.setTotalElements(shopListEntityPage.getTotalElements());
        return response;
    }

}
