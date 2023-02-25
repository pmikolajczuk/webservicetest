package pl.mikolaj.webservicetest.services.impl;

import pl.mikolaj.webservicetest.beans.Person;
import pl.mikolaj.webservicetest.services.PersonService;
import pl.mikolaj.utils.Unchecked;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExportServiceImpl implements pl.mikolaj.webservicetest.services.ExportService {
    private static final int PAGE_SIZE = 10;

    private final PersonService personService;

    public ExportServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void exportAllPeople() {
        doExportPeople(pageIndex -> personService.getAllPeople(PAGE_SIZE, pageIndex));
    }

    @Override
    public void exportPeopleByIds(List<Integer> ids) {
        doExportPeople(pageIndex -> personService.findByIds(ids, PAGE_SIZE, pageIndex));
    }

    private void doExportPeople(Function<Integer, List<Person>> dataProvider) {
        System.out.println("Starting people export");
        Map<String, Writer> genderToWriter = null;
        try {
            genderToWriter = Arrays.stream(Gender.values())
                    .collect(Collectors.toMap(Gender::getCamelCase, Unchecked.function(this::createWriter)));

            genderToWriter.values().forEach(Unchecked.consumer(this::writeHeader));

            int count = 0;
            for (int i = 0; true; i++) {
                List<Person> persons = dataProvider.apply(i);
                if (persons.isEmpty()) {
                    break;
                }
                Map<String, Writer> finalGenderToWriter = genderToWriter;
                persons.forEach(person -> exportPerson(person, finalGenderToWriter));
                count += persons.size();
                System.out.println("Exported " + count + " persons");
            }
        } finally {
            genderToWriter.values().forEach(Unchecked.consumer(Writer::close));
        }
        System.out.println("Finished people export");
    }

    private void exportPerson(Person person, Map<String, Writer> genderToWriter) {
        Optional.of(person)
                .map(Person::getGender)
                .map(genderToWriter::get)
                .ifPresent(Unchecked.consumer(writer -> writer.write(toExportRow(person))));
    }

    private String toExportRow(Person person) {
        return person.getId() + ";" + person.getFirstName() + ";" + person.getAge() + "\n";
    }

    private Writer createWriter(Gender gender) throws IOException {
        return new FileWriter("export/" + gender.getFileNamePrefix() + "_" + createFileNameSuffix());
    }

    private String createFileNameSuffix() {
        return String.valueOf(new Date().getTime());
    }

    private void writeHeader(Writer writer) throws IOException {
        writer.write("id;firstName;age\n");
    }

    enum Gender {
        MALE("Male", "males"),
        FEMALE("Female", "females");

        private final String camelCase;
        private final String fileNamePrefix;

        Gender(String camelCase, String fileNamePrefix) {
            this.camelCase = camelCase;
            this.fileNamePrefix = fileNamePrefix;
        }

        public String getCamelCase() {
            return camelCase;
        }

        public String getFileNamePrefix() {
            return fileNamePrefix;
        }
    }
}
