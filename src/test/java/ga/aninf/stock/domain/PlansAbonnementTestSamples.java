package ga.aninf.stock.domain;

import java.util.UUID;

public class PlansAbonnementTestSamples {

    public static PlansAbonnement getPlansAbonnementSample1() {
        return new PlansAbonnement()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .libelle("libelle1")
            .duree("duree1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static PlansAbonnement getPlansAbonnementSample2() {
        return new PlansAbonnement()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .libelle("libelle2")
            .duree("duree2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static PlansAbonnement getPlansAbonnementRandomSampleGenerator() {
        return new PlansAbonnement()
            .id(UUID.randomUUID())
            .libelle(UUID.randomUUID().toString())
            .duree(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
