package pl.mikolaj.eshop.dao;

import pl.mikolaj.eshop.model.ProductModel;

import java.util.List;

public interface ProductDao {
    List<ProductModel> findAllProducts();

    List<ProductModel> findAllProducts(int pageSize, int pageIndex);

    List<ProductModel> findProductsByCodes(List<String> codes, int pageSize, int pageIndex);

    ProductModel findProductByCode(String code);
}
