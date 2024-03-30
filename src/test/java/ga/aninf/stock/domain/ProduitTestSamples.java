package ga.aninf.stock.domain;

import java.util.UUID;

public class ProduitTestSamples {

    public static Produit getProduitSample1() {
        return new Produit()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .libelle("libelle1")
            .slug("slug1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Produit getProduitSample2() {
        return new Produit()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .libelle("libelle2")
            .slug("slug2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Produit getProduitRandomSampleGenerator() {
        return new Produit()
            .id(UUID.randomUUID())
            .libelle(UUID.randomUUID().toString())
            .slug(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
