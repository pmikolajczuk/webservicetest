package pl.mikolaj.eshop.service;

import pl.mikolaj.eshop.model.ProductModel;

import java.util.List;

public interface ProductService {
    List<ProductModel> findAllProducts();
    List<ProductModel> findAllProducts(int pageSize, int pageIndex);

    List<ProductModel> findProductsByCodes(List<String> codes, int pageSize, int pageIndex);
}
