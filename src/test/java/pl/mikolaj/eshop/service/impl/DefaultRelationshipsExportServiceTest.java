package pl.mikolaj.eshop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.mikolaj.eshop.dao.DaoFactory;
import pl.mikolaj.eshop.service.ConfigurationService;
import pl.mikolaj.eshop.service.RelationshipsExportService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class DefaultRelationshipsExportServiceTest {

    private ConfigurationService configurationService = Mockito.mock(ConfigurationService.class);
    private final RelationshipsExportService relationshipsExportService = new DefaultRelationshipsExportService(
            new DefaultProductService(DaoFactory.getProductDao()),
            configurationService);

    @BeforeEach
    void setUp() {
        when(configurationService.getStringValue(anyString()))
                .thenThrow(new RuntimeException("Incorrect configuration key"));
        when(configurationService.getStringValue(eq("pl.mikolaj.eshop.relationships.export.path")))
                .thenReturn("export/");
    }

    @Test
    void exportRelationships() {
        relationshipsExportService.exportRelationships();
    }

    @Test
    void exportRelationshipsByCodes() {
        List<String> productCodes = List.of("001", "003");
        relationshipsExportService.exportRelationshipsByCodes(productCodes);
    }
}