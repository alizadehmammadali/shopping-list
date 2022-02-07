package io.jur.shoppinglist.controllers;

import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.general.SearchResponse;
import io.jur.shoppinglist.service.ShopListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shop-lists")
@RequiredArgsConstructor
@Tag(name = "shopList", description = "the ShopList API")
public class ShopListController {

    private final ShopListService shopListService;

    @Operation(summary = "Add a new or update shop list", description = "Add a new or update shop list if id field exists", tags = {"shopList"})
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "ShopList created or updated", content = @Content(schema = @Schema(implementation = ShopListDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "409", description = "Conflict has been occurred.ShopList with same name already exists")
            })
    @PostMapping
    public ResponseEntity<ShopListDTO> createOrUpdateShopList(@Valid @RequestBody ShopListDTO shopListDTO) {
        return ResponseEntity.ok(shopListService.createOrUpdateShopList(shopListDTO));
    }

    @Operation(summary = "Find ShopLists by name with paginated", description = "Search in shopLists in %name% format", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShopListDTO.class))))})
    @GetMapping
    public ResponseEntity<SearchResponse<List<ShopListDTO>>> getShopListByName(@Parameter(description = "The name of the shopList to find.", required = false)
                                                                               @RequestParam(value = "name", defaultValue = "") String name,
                                                                               @Parameter(description = "The pageIndex of the shopList.", required = false)
                                                                               @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                                               @Parameter(description = "Number of shopLists in page)", required = false)
                                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return ResponseEntity.ok(shopListService.getShopLists(name, pageIndex, pageSize));
    }

    @Operation(summary = "Deletes a shopList by id", description = "", tags = {"shopList"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @DeleteMapping("/{shopListId}")
    public ResponseEntity<Void> deleteById(@Parameter(description = "The Id of the shopList to find.", required = true)
                                           @PathVariable("shopListId") Long shopListId) {
        shopListService.deleteShopList(shopListId);
        return ResponseEntity.ok().build();
    }

}
