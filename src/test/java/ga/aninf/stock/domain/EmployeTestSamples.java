package ga.aninf.stock.domain;

import java.util.UUID;

public class EmployeTestSamples {

    public static Employe getEmployeSample1() {
        return new Employe()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .firstName("firstName1")
            .lastName("lastName1")
            .login("login1")
            .email("email1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Employe getEmployeSample2() {
        return new Employe()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .firstName("firstName2")
            .lastName("lastName2")
            .login("login2")
            .email("email2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Employe getEmployeRandomSampleGenerator() {
        return new Employe()
            .id(UUID.randomUUID())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .login(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
