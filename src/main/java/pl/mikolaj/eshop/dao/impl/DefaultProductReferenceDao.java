package pl.mikolaj.eshop.dao.impl;

import pl.mikolaj.eshop.dao.ProductDao;
import pl.mikolaj.eshop.dao.ProductReferenceDao;
import pl.mikolaj.eshop.dto.ProductReferenceDto;
import pl.mikolaj.eshop.model.ProductReferenceModel;
import pl.mikolaj.utils.Utils;

import java.util.List;

public class DefaultProductReferenceDao implements ProductReferenceDao {
    private static final String PRODUCT_REFERENCES_FILE_PATH = "src\\main\\resources\\product_references.json";

    private ProductDao productDao;

    public DefaultProductReferenceDao(){}

    public DefaultProductReferenceDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductReferenceModel> findAllProductReferences() {
        List<ProductReferenceDto> dtos = Utils.loadAllItems(ProductReferenceDto.class, PRODUCT_REFERENCES_FILE_PATH);
        return dtos.stream()
                .map(dto -> new ProductReferenceModel(dto, productDao))
                .toList();

    }

    @Override
    public List<ProductReferenceModel> findReferencesBySource(String code) {
        return findAllProductReferences().stream()
                .filter(prm -> prm.getSource().getCode().equals(code))
                .toList();
    }
}
