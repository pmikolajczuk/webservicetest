package pl.mikolaj.eshop.model;

import pl.mikolaj.eshop.dao.ProductDao;
import pl.mikolaj.eshop.dto.ProductReferenceDto;

public class ProductReferenceModel {
    private final ProductReferenceDto dto;
    private final ProductDao productDao;

    public ProductReferenceModel(ProductReferenceDto dto, ProductDao productDao) {
        this.dto = dto;
        this.productDao = productDao;
    }

    public ProductReferenceType getType() {
        return ProductReferenceType.valueOf(dto.getType());
    }

    public ProductModel getSource() {
        return productDao.findProductByCode(dto.getSource());
    }

    public ProductModel getTarget() {
        return productDao.findProductByCode(dto.getTarget());
    }

    @Override
    public String toString() {
        return "ProductReferenceModel{" +
                "type=" + getType() +
                ", source=" + getSource().getCode() +
                ", target=" + getTarget().getCode() +
                '}';
    }

    public enum ProductReferenceType {
        ACCESSORIES,
        SERVICE,
        BASE_PRODUCT,
        CONSUMABLES,
        SPAREPART,
        CROSSELING;
    }
}
