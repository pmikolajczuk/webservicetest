package pl.mikolaj.eshop.model;

import pl.mikolaj.eshop.dao.ProductReferenceDao;
import pl.mikolaj.eshop.dto.ProductDto;

import java.util.List;

public class ProductModel {
    private ProductDto dto;
    private ProductReferenceDao productReferenceDao;

    public ProductModel(ProductDto dto, ProductReferenceDao productReferenceDao) {
        this.dto = dto;
        this.productReferenceDao = productReferenceDao;
    }

    public String getCode() {
        return dto.getCode();
    }

    public List<ProductReferenceModel> getProductReferences() {
        return productReferenceDao.findReferencesBySource(this.getCode());
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "code=" + dto.getCode() +
                ", productReferencs=" + getProductReferences() +
                '}';
    }
}
