package io.jur.shoppinglist.mapper;

import io.jur.shoppinglist.dao.entity.ProductEntity;
import io.jur.shoppinglist.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    public abstract ProductEntity toEntity(ProductDTO productDTO);

    @Mappings({
            @Mapping(target = "shopListDTO",expression = "java(ShopListMapper.INSTANCE.toDTO(productEntity.getShopListEntity()))")
    })
    public abstract ProductDTO toDTO(ProductEntity productEntity);

}
