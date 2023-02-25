package pl.mikolaj.eshop.dto;

public class ProductReferenceDto {
    private String type;
    private String source;
    private String target;

    public ProductReferenceDto(String type, String source, String target) {
        this.type = type;
        this.source = source;
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    //    public static ProductReferenceDto fromProductReferenceModel(ProductReferenceModel prm) {
//        return new ProductReferenceDto(
//                prm.getType().toString(),
//                prm.getSource().getCode(),
//                prm .getTarget().getCode()
//        );
//    }
//
//    public ProductReferenceModel toProductReferenceModel(Map<String, ProductModel> productCodeToModel) {
//        return new ProductReferenceModel(
//                ProductReferenceModel.ProductReferenceType.valueOf(this.type),
//                productCodeToModel.get(this.source),
//                productCodeToModel.get(this.target)
//        );
//    }
}
