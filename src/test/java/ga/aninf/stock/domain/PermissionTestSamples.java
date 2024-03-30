package ga.aninf.stock.domain;

import java.util.UUID;

public class PermissionTestSamples {

    public static Permission getPermissionSample1() {
        return new Permission()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .ressource("ressource1")
            .action("action1")
            .createdBy("createdBy1")
            .lastModifiedBy("lastModifiedBy1");
    }

    public static Permission getPermissionSample2() {
        return new Permission()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .ressource("ressource2")
            .action("action2")
            .createdBy("createdBy2")
            .lastModifiedBy("lastModifiedBy2");
    }

    public static Permission getPermissionRandomSampleGenerator() {
        return new Permission()
            .id(UUID.randomUUID())
            .ressource(UUID.randomUUID().toString())
            .action(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .lastModifiedBy(UUID.randomUUID().toString());
    }
}
