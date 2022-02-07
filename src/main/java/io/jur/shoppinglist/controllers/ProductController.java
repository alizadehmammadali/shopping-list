package io.jur.shoppinglist.controllers;

import io.jur.shoppinglist.model.ProductDTO;
import io.jur.shoppinglist.service.ProductService;
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

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "product", description = "the Product API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Add a new or update product", description = "Add a new product or update product if id field exists", tags = {"product"})
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "Product created or updated", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")})
    @PostMapping
    public ResponseEntity<ProductDTO> createOrUpdateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createOrUpdateProduct(productDTO));
    }

    @Operation(summary = "Find Products by shopListId", description = "Name search by shopListId", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))})
    @GetMapping("/{shopListId}")
    public ResponseEntity<List<ProductDTO>> getProductListByShopListId(@Parameter(description = "The shopListId of the products to find.")
                                                                       @PathVariable("shopListId") Long shopListId) {
        return ResponseEntity.ok(productService.getProductsByShopList(shopListId));
    }

    @Operation(summary = "Patch product status", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @PatchMapping("/{productId}")
    public ResponseEntity<Void> toggleProductStatus(@Parameter(description = "The Id of the product to find.", required = true)
                                                    @PathVariable("productId") Long productId) {
        productService.toggleProductStatus(productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletes a product by id", tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@Parameter(description = "The Id of the product to find.", required = true)
                                                  @PathVariable("productId") Long productId) {

        productService.deleteProductById(productId);
        return ResponseEntity.ok().build();
    }

}
