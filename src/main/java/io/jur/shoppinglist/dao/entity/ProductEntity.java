package io.jur.shoppinglist.dao.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.jur.shoppinglist.dao.entity.base.BaseEntity;
import io.jur.shoppinglist.model.enums.QuantityUnit;
import io.jur.shoppinglist.model.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Statement;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@SequenceGenerator(name = "default_gen", sequenceName = "product_seq", allocationSize = 1)
public class ProductEntity extends BaseEntity {

    private String name;

    @Enumerated(value = EnumType.STRING)
    private QuantityUnit quantityUnit;

    private double quantity;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn
    private ShopListEntity shopListEntity;

}
