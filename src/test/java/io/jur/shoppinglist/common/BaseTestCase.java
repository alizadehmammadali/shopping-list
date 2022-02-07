package io.jur.shoppinglist.common;

import io.jur.shoppinglist.model.enums.QuantityUnit;
import io.jur.shoppinglist.model.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class BaseTestCase {

    public final String SHOP_LIST_NAME = "TEMPLATE 1";
    public final Long SHOP_LIST_ID = 1L;
    public final Long PRODUCT_ID = 1L;
    public final String PRODUCT_NAME = "Still water";
    public final QuantityUnit PRODUCT_QUANTITY_UNIT = QuantityUnit.LITRE;
    public final double PRODUCT_QUANTITY = 5;
    public final Status PRODUCT_STATUS = Status.ACTIVE;
    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }
}
