package pl.mikolaj.eshop.service.impl;

import pl.mikolaj.eshop.dao.ProductDao;
import pl.mikolaj.eshop.model.ProductModel;
import pl.mikolaj.eshop.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {
    private final ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<ProductModel> findAllProducts() {
        return productDao.findAllProducts();
    }

    @Override
    public List<ProductModel> findAllProducts(int pageSize, int pageIndex) {
        return productDao.findAllProducts(pageSize, pageIndex);
    }

    @Override
    public List<ProductModel> findProductsByCodes(List<String> codes, int pageSize, int pageIndex) {
        return productDao.findProductsByCodes(codes, pageSize, pageIndex);
    }
}
