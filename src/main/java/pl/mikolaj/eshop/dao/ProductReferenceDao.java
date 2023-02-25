package pl.mikolaj.eshop.dao;

import pl.mikolaj.eshop.model.ProductReferenceModel;

import java.util.List;

public interface ProductReferenceDao {
    List<ProductReferenceModel> findAllProductReferences();

    List<ProductReferenceModel> findReferencesBySource(String code);

    void setProductDao(ProductDao productDao);
}
