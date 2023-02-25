package pl.mikolaj.eshop.service;

import java.util.List;

public interface RelationshipsExportService {
    void exportRelationships();
    void exportRelationshipsByCodes(List<String> codes);
}
