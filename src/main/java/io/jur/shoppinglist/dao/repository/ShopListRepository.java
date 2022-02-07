package io.jur.shoppinglist.dao.repository;


import io.jur.shoppinglist.dao.entity.ShopListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopListRepository extends CrudRepository<ShopListEntity, Long> {

    @Query("select sl from ShopListEntity sl where " +
            "(:name is null or (lower(sl.name) like lower(concat('%',:name,'%')))) order by sl.createdAt desc")
    Page<ShopListEntity> getShopListEntitiesByName(String name, Pageable page);

    Optional<ShopListEntity> findShopListEntityByName(@Param("name")String name);
}
