package pl.mikolaj.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.mikolaj.eshop.model.ProductModel;
import pl.mikolaj.eshop.model.ProductReferenceModel;
import pl.mikolaj.eshop.model.ProductReferenceModel.ProductReferenceType;
import pl.mikolaj.eshop.service.ConfigurationService;
import pl.mikolaj.eshop.service.ProductService;
import pl.mikolaj.eshop.service.RelationshipsExportService;
import pl.mikolaj.utils.Unchecked;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultRelationshipsExportService implements RelationshipsExportService {
    private static final int PAGE_SIZE = 3;
    private static final String RELATIONSHIPS_EXPORT_PATH = "pl.mikolaj.eshop.relationships.export.path";
    private static final Logger log = LoggerFactory.getLogger(DefaultRelationshipsExportService.class);

    private final ProductService productService;
    private final ConfigurationService configurationService;

    public DefaultRelationshipsExportService(
            ProductService productService,
            ConfigurationService configurationService
    ) {
        this.productService = productService;
        this.configurationService = configurationService;
    }

    @Override
    public void exportRelationships() {
        doExportRelationships(pageIndex -> productService.findAllProducts(PAGE_SIZE, pageIndex));
    }

    @Override
    public void exportRelationshipsByCodes(List<String> codes) {
        doExportRelationships(pageIndex -> productService.findProductsByCodes(codes, PAGE_SIZE, pageIndex));
    }

    private void doExportRelationships(Function<Integer, List<ProductModel>> dataProvider) {
        log.info("Starting relationships export");
        Map<ExportableRelationship, ByteArrayOutputStream> relationshipToOS = createRelationshipsToOSMap();
        writeHeaders(relationshipToOS);

        for(int i = 0, count = 0; true; i++) {
            List<ProductModel> products = dataProvider.apply(i);
            if(products.isEmpty()){
                break;
            }
            exportProductsWithReferencesV2(products, relationshipToOS);
            count += products.size();
            log.info("Exported {} products", count);
        }

        relationshipToOS.forEach(this::saveRelationshipsToFile);
        log.info("Finished relationships export");
    }

    private void exportProductsWithReferencesV2(
            List<ProductModel> products,
            Map<ExportableRelationship, ByteArrayOutputStream> relationshipToOS
    ) {
        Map<ProductReferenceType, BufferedWriter> referenceToBufferedWriter = relationshipToOS.keySet()
                .stream()
                .collect(Collectors.toMap(
                        ExportableRelationship::getReferenceType,
                        expRel -> new BufferedWriter(new OutputStreamWriter(relationshipToOS.get(expRel)))
                ));
        try {
            for (ProductModel parent : products) {
                for (ProductReferenceModel child : parent.getProductReferences()) {
                    BufferedWriter writer = referenceToBufferedWriter.get(child.getType());
                    if (writer != null) {
                        writer.write(parent.getCode() + "," + child.getTarget().getCode());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            referenceToBufferedWriter.values().forEach(Unchecked.consumer(BufferedWriter::close));
        }

    }

    private void exportProductsWithReferencesV1(
            List<ProductModel> products,
            Map<ExportableRelationship, ByteArrayOutputStream> relationshipToOS
    ) {
        for(ProductModel product : products) {
            for(ProductReferenceModel reference : product.getProductReferences()) {
                Optional<ExportableRelationship> exportableRelationship = relationshipToOS
                        .keySet()
                        .stream()
                        .filter(expRel -> expRel.getReferenceType().equals(reference.getType()))
                        .findAny();

                exportableRelationship.ifPresent(expRel -> writeParentChildProductToOS(
                        product.getCode(),
                        reference.getTarget().getCode(),
                        relationshipToOS.get(expRel)
                ));
            }
        }
    }

    private void writeParentChildProductToOS(String parentProduct, String childProduct, OutputStream os) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
            writer.write(parentProduct + "," + childProduct);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void saveRelationshipsToFile(ExportableRelationship relationship, ByteArrayOutputStream os) {
        String fileName = configurationService.getStringValue(RELATIONSHIPS_EXPORT_PATH)
                + generateFileName(relationship.getBaseFileName());
        log.info("Saving " + fileName);

        try(OutputStream fileOutputStream = new FileOutputStream(fileName)) {
            os.writeTo(fileOutputStream);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateFileName(String baseFileName) {
        return baseFileName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    private Map<ExportableRelationship, ByteArrayOutputStream> createRelationshipsToOSMap() {
        return Arrays.stream(ExportableRelationship.values())
                .collect(Collectors.toMap(r -> r, r -> new ByteArrayOutputStream()));
    }

    private void writeHeaders(Map<ExportableRelationship, ByteArrayOutputStream> relationshipToOS) {
        relationshipToOS.forEach(this::writeHeader);
    }

    private void writeHeader(ExportableRelationship relationship, ByteArrayOutputStream os) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
            String header = "Material Number <" + relationship.getBaseFileName()
                    + ">, Material Number <Parent Product for "
                    + relationship.getBaseFileName()
                    + ">";
            writer.write(header);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    enum ExportableRelationship {
        ACCESSORIES(ProductReferenceType.ACCESSORIES, "Accessories"),
        SERVICES(ProductReferenceType.SERVICE, "Services"),
        SPARE_PARTS(ProductReferenceType.SPAREPART, "Spare_Parts");

        private final ProductReferenceType referenceType;
        private final String baseFileName;

        ExportableRelationship(ProductReferenceType referenceType, String baseFileName) {
            this.baseFileName = baseFileName;
            this.referenceType = referenceType;
        }

        public ProductReferenceType getReferenceType() {
            return referenceType;
        }

        public String getBaseFileName() {
            return baseFileName;
        }
    }
}
