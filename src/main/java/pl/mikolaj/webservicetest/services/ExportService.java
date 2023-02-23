package pl.mikolaj.webservicetest.services;

import java.util.List;

public interface ExportService {
    void exportAllPeople();

    void exportPeopleByIds(List<Integer> ids);
}
