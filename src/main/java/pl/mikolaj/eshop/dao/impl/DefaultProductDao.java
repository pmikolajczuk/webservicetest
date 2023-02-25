package pl.mikolaj.eshop.dao.impl;


import pl.mikolaj.eshop.dao.ProductDao;
import pl.mikolaj.eshop.dao.ProductReferenceDao;
import pl.mikolaj.eshop.dto.ProductDto;
import pl.mikolaj.eshop.model.ProductModel;
import pl.mikolaj.utils.Utils;

import java.util.List;

public class DefaultProductDao implements ProductDao {
    private static final String PRODUCTS_FILE_PATH = "src\\main\\resources\\products.json";

    private final ProductReferenceDao productReferenceDao;

    public DefaultProductDao(ProductReferenceDao productReferenceDao) {
        this.productReferenceDao = productReferenceDao;
    }

    @Override
    public List<ProductModel> findAllProducts() {
        return loadDtos().stream()
                .map(this::dtoToProductModel)
                .toList();
    }

    @Override
    public List<ProductModel> findAllProducts(int pageSize, int pageIndex) {
        return Utils.getPage(this::loadDtos, pageSize, pageIndex)
                .stream()
                .map(this::dtoToProductModel)
                .toList();
    }

    @Override
    public List<ProductModel> findProductsByCodes(List<String> codes, int pageSize, int pageIndex) {
        List<ProductModel> products = loadDtos().stream()
                .filter(dto -> codes.contains(dto.getCode()))
                .map(this::dtoToProductModel)
                .toList();
        return Utils.getPage(() -> products, pageSize, pageIndex);
    }

        @Override
    public ProductModel findProductByCode(String code) {
        return loadDtos().stream()
                .filter(dto -> dto.getCode().equals(code))
                .findAny()
                .map(this::dtoToProductModel)
                .orElse(null);
    }

    private List<ProductDto> loadDtos() {
        return Utils.loadAllItems(ProductDto.class, PRODUCTS_FILE_PATH);
    }

    private ProductModel dtoToProductModel(ProductDto dto) {
        return new ProductModel(dto, productReferenceDao);
    }
}
