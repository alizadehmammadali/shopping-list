package io.jur.shoppinglist.dao.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.jur.shoppinglist.dao.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "shop_lists")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SequenceGenerator(name = "default_gen", sequenceName = "shop_list_seq", allocationSize = 1)
public class ShopListEntity extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "shopListEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> productEntities = new LinkedList<>();
}
