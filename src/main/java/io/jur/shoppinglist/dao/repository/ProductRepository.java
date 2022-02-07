package io.jur.shoppinglist.dao.repository;


import io.jur.shoppinglist.dao.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    List<ProductEntity> findProductEntitiesByShopListEntityId(@Param("id") Long id);
}
