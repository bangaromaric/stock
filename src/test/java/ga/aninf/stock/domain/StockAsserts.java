package ga.aninf.stock.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class StockAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStockAllPropertiesEquals(Stock expected, Stock actual) {
        assertStockAutoGeneratedPropertiesEquals(expected, actual);
        assertStockAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStockAllUpdatablePropertiesEquals(Stock expected, Stock actual) {
        assertStockUpdatableFieldsEquals(expected, actual);
        assertStockUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStockAutoGeneratedPropertiesEquals(Stock expected, Stock actual) {
        assertThat(expected)
            .as("Verify Stock auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStockUpdatableFieldsEquals(Stock expected, Stock actual) {
        assertThat(expected)
            .as("Verify Stock relevant properties")
            .satisfies(e -> assertThat(e.getQuantite()).as("check quantite").isEqualTo(actual.getQuantite()))
            .satisfies(e -> assertThat(e.getDeleteAt()).as("check deleteAt").isEqualTo(actual.getDeleteAt()))
            .satisfies(e -> assertThat(e.getCreatedBy()).as("check createdBy").isEqualTo(actual.getCreatedBy()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()))
            .satisfies(e -> assertThat(e.getLastModifiedBy()).as("check lastModifiedBy").isEqualTo(actual.getLastModifiedBy()))
            .satisfies(e -> assertThat(e.getLastModifiedDate()).as("check lastModifiedDate").isEqualTo(actual.getLastModifiedDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertStockUpdatableRelationshipsEquals(Stock expected, Stock actual) {
        assertThat(expected)
            .as("Verify Stock relationships")
            .satisfies(e -> assertThat(e.getEntrepot()).as("check entrepot").isEqualTo(actual.getEntrepot()))
            .satisfies(e -> assertThat(e.getProduit()).as("check produit").isEqualTo(actual.getProduit()));
    }
}