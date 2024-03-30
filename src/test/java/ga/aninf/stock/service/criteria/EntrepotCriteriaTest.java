package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EntrepotCriteriaTest {

    @Test
    void newEntrepotCriteriaHasAllFiltersNullTest() {
        var entrepotCriteria = new EntrepotCriteria();
        assertThat(entrepotCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void entrepotCriteriaFluentMethodsCreatesFiltersTest() {
        var entrepotCriteria = new EntrepotCriteria();

        setAllFilters(entrepotCriteria);

        assertThat(entrepotCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void entrepotCriteriaCopyCreatesNullFilterTest() {
        var entrepotCriteria = new EntrepotCriteria();
        var copy = entrepotCriteria.copy();

        assertThat(entrepotCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(entrepotCriteria)
        );
    }

    @Test
    void entrepotCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var entrepotCriteria = new EntrepotCriteria();
        setAllFilters(entrepotCriteria);

        var copy = entrepotCriteria.copy();

        assertThat(entrepotCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(entrepotCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var entrepotCriteria = new EntrepotCriteria();

        assertThat(entrepotCriteria).hasToString("EntrepotCriteria{}");
    }

    private static void setAllFilters(EntrepotCriteria entrepotCriteria) {
        entrepotCriteria.id();
        entrepotCriteria.libelle();
        entrepotCriteria.slug();
        entrepotCriteria.capacite();
        entrepotCriteria.deleteAt();
        entrepotCriteria.createdBy();
        entrepotCriteria.createdDate();
        entrepotCriteria.lastModifiedBy();
        entrepotCriteria.lastModifiedDate();
        entrepotCriteria.structureId();
        entrepotCriteria.mouvementStockId();
        entrepotCriteria.stockId();
        entrepotCriteria.inventaireId();
        entrepotCriteria.distinct();
    }

    private static Condition<EntrepotCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLibelle()) &&
                condition.apply(criteria.getSlug()) &&
                condition.apply(criteria.getCapacite()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getStructureId()) &&
                condition.apply(criteria.getMouvementStockId()) &&
                condition.apply(criteria.getStockId()) &&
                condition.apply(criteria.getInventaireId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EntrepotCriteria> copyFiltersAre(EntrepotCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLibelle(), copy.getLibelle()) &&
                condition.apply(criteria.getSlug(), copy.getSlug()) &&
                condition.apply(criteria.getCapacite(), copy.getCapacite()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getStructureId(), copy.getStructureId()) &&
                condition.apply(criteria.getMouvementStockId(), copy.getMouvementStockId()) &&
                condition.apply(criteria.getStockId(), copy.getStockId()) &&
                condition.apply(criteria.getInventaireId(), copy.getInventaireId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
