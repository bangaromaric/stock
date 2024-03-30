package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InventaireCriteriaTest {

    @Test
    void newInventaireCriteriaHasAllFiltersNullTest() {
        var inventaireCriteria = new InventaireCriteria();
        assertThat(inventaireCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void inventaireCriteriaFluentMethodsCreatesFiltersTest() {
        var inventaireCriteria = new InventaireCriteria();

        setAllFilters(inventaireCriteria);

        assertThat(inventaireCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void inventaireCriteriaCopyCreatesNullFilterTest() {
        var inventaireCriteria = new InventaireCriteria();
        var copy = inventaireCriteria.copy();

        assertThat(inventaireCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(inventaireCriteria)
        );
    }

    @Test
    void inventaireCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var inventaireCriteria = new InventaireCriteria();
        setAllFilters(inventaireCriteria);

        var copy = inventaireCriteria.copy();

        assertThat(inventaireCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(inventaireCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var inventaireCriteria = new InventaireCriteria();

        assertThat(inventaireCriteria).hasToString("InventaireCriteria{}");
    }

    private static void setAllFilters(InventaireCriteria inventaireCriteria) {
        inventaireCriteria.id();
        inventaireCriteria.quantite();
        inventaireCriteria.inventaireDate();
        inventaireCriteria.deleteAt();
        inventaireCriteria.createdBy();
        inventaireCriteria.createdDate();
        inventaireCriteria.lastModifiedBy();
        inventaireCriteria.lastModifiedDate();
        inventaireCriteria.entrepotId();
        inventaireCriteria.produitId();
        inventaireCriteria.distinct();
    }

    private static Condition<InventaireCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getQuantite()) &&
                condition.apply(criteria.getInventaireDate()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getEntrepotId()) &&
                condition.apply(criteria.getProduitId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InventaireCriteria> copyFiltersAre(InventaireCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getQuantite(), copy.getQuantite()) &&
                condition.apply(criteria.getInventaireDate(), copy.getInventaireDate()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getEntrepotId(), copy.getEntrepotId()) &&
                condition.apply(criteria.getProduitId(), copy.getProduitId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
