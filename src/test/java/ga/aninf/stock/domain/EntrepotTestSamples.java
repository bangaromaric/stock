package ga.aninf.stock.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EntrepotTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Entrepot getEntrepotSample1() {
        return new Entrepot()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .libelle("libelle1")
            .slug("slug1")
            .capacite(1)
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Entrepot getEntrepotSample2() {
        return new Entrepot()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .libelle("libelle2")
            .slug("slug2")
            .capacite(2)
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Entrepot getEntrepotRandomSampleGenerator() {
        return new Entrepot()
            .id(UUID.randomUUID())
            .libelle(UUID.randomUUID().toString())
            .slug(UUID.randomUUID().toString())
            .capacite(intCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
