package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VenteCriteriaTest {

    @Test
    void newVenteCriteriaHasAllFiltersNullTest() {
        var venteCriteria = new VenteCriteria();
        assertThat(venteCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void venteCriteriaFluentMethodsCreatesFiltersTest() {
        var venteCriteria = new VenteCriteria();

        setAllFilters(venteCriteria);

        assertThat(venteCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void venteCriteriaCopyCreatesNullFilterTest() {
        var venteCriteria = new VenteCriteria();
        var copy = venteCriteria.copy();

        assertThat(venteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(venteCriteria)
        );
    }

    @Test
    void venteCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var venteCriteria = new VenteCriteria();
        setAllFilters(venteCriteria);

        var copy = venteCriteria.copy();

        assertThat(venteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(venteCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var venteCriteria = new VenteCriteria();

        assertThat(venteCriteria).hasToString("VenteCriteria{}");
    }

    private static void setAllFilters(VenteCriteria venteCriteria) {
        venteCriteria.id();
        venteCriteria.quantite();
        venteCriteria.montant();
        venteCriteria.venteDate();
        venteCriteria.deleteAt();
        venteCriteria.createdBy();
        venteCriteria.createdDate();
        venteCriteria.lastModifiedBy();
        venteCriteria.lastModifiedDate();
        venteCriteria.produitId();
        venteCriteria.structureId();
        venteCriteria.distinct();
    }

    private static Condition<VenteCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getQuantite()) &&
                condition.apply(criteria.getMontant()) &&
                condition.apply(criteria.getVenteDate()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getProduitId()) &&
                condition.apply(criteria.getStructureId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VenteCriteria> copyFiltersAre(VenteCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getQuantite(), copy.getQuantite()) &&
                condition.apply(criteria.getMontant(), copy.getMontant()) &&
                condition.apply(criteria.getVenteDate(), copy.getVenteDate()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getProduitId(), copy.getProduitId()) &&
                condition.apply(criteria.getStructureId(), copy.getStructureId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
