package pl.mikolaj.eshop.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mikolaj.eshop.dao.ProductDao;
import pl.mikolaj.eshop.dao.ProductReferenceDao;
import pl.mikolaj.eshop.model.ProductModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultProductDaoTest {

    ProductReferenceDao productReferenceDao = new DefaultProductReferenceDao();
    ProductDao productDao = new DefaultProductDao(productReferenceDao);

    @BeforeEach
    void setUp() {
        productReferenceDao.setProductDao(productDao);
    }

    @Test
    void testFindAll() {
        List<ProductModel> products = productDao.findAllProducts();
        products.forEach(System.out::println);

        assertEquals(20, products.size());
    }
}