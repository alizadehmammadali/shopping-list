package io.jur.shoppinglist.model;

import io.jur.shoppinglist.model.enums.QuantityUnit;
import io.jur.shoppinglist.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductDTO {
    @Schema(description = "Id of product. It uses for update product", example = "1", required = false)
    private Long id;

    @Schema(description = "Name of product. It uses for update product", example = "Sugar", required = true)
    private String name;

    @Schema(description = "QuantityUnit(LITRE,KILO_GRAM,COUNT,METRE) of product.", example = "KILO_GRAM", required = true)
    @NotNull
    private QuantityUnit quantityUnit;

    @Schema(description = "Quantity of product.", example = "1.5", required = true)
    @NotNull
    private Double quantity;

    @Schema(description = "Status of product. Set Inactive after bought", example = "ACTIVE", required = false)
    private Status status = Status.ACTIVE;

    @Schema(description = "ShopList information that is related this product.Set only Id field", required = false)
    @NotNull
    private ShopListDTO shopListDTO;
}
