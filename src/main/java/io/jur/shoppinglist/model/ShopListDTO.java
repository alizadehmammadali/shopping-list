package io.jur.shoppinglist.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ShopListDTO {

    @Schema(description = "Id of shopList. It uses for update shopList", example = "1", required = false)
    private Long id;

    @Schema(description = "Name of shopList.", example = "Foods", required = true)
    @NotNull
    @Length(min = 3)
    private String name;
}
