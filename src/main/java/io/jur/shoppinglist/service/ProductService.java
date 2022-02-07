package io.jur.shoppinglist.service;

import io.jur.shoppinglist.model.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createOrUpdateProduct(ProductDTO productDTO);

    List<ProductDTO> getProductsByShopList(Long shopListId);

    void deleteProductById(long id);

    void toggleProductStatus(long id);
}
