package io.jur.shoppinglist.runner;

import io.jur.shoppinglist.mapper.ShopListMapper;
import io.jur.shoppinglist.model.ProductDTO;
import io.jur.shoppinglist.model.ShopListDTO;
import io.jur.shoppinglist.model.enums.QuantityUnit;
import io.jur.shoppinglist.model.enums.Status;
import io.jur.shoppinglist.service.ProductService;
import io.jur.shoppinglist.service.ShopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FakeDataImporter implements CommandLineRunner {

    private final ShopListService shopListService;

    private final ProductService productService;

    private final String[] products = {"Egg", "Bred", "Water", "100 sheets paper", "Sugar"};
    private final QuantityUnit[] quantityUnits = {QuantityUnit.COUNT, QuantityUnit.COUNT, QuantityUnit.LITRE, QuantityUnit.COUNT, QuantityUnit.KILO_GRAM};

    @Override
    public void run(String... args) throws Exception {
        int min = 1;
        int max = 8;
        for (int i = 1; i < 10; i++) {
            ShopListDTO shopListDTO = new ShopListDTO();
            shopListDTO.setName("Template " + i);
            shopListService.createOrUpdateShopList(shopListDTO);
        }
        for (int i = 0; i < products.length; i++) {
            ProductDTO productDTO = new ProductDTO();
            long randomNumber = min + (long) (Math.random() * (max - min));
            productDTO.setName(products[i]);
            productDTO.setStatus(Status.ACTIVE);
            productDTO.setQuantityUnit(quantityUnits[i]);
            productDTO.setQuantity((double) Integer.parseInt(randomNumber + ""));
            productDTO.setShopListDTO(ShopListMapper.INSTANCE.toDTO(shopListService.getShopListEntityById(randomNumber)));
            productService.createOrUpdateProduct(productDTO);
        }
    }
}
