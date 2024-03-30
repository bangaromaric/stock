package ga.aninf.stock.domain;

import java.util.UUID;

public class StructureTestSamples {

    public static Structure getStructureSample1() {
        return new Structure()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .libelle("libelle1")
            .telephone("telephone1")
            .email("email1")
            .adresse("adresse1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Structure getStructureSample2() {
        return new Structure()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .libelle("libelle2")
            .telephone("telephone2")
            .email("email2")
            .adresse("adresse2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Structure getStructureRandomSampleGenerator() {
        return new Structure()
            .id(UUID.randomUUID())
            .libelle(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .adresse(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
