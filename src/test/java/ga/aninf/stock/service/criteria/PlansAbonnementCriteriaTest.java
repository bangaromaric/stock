package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PlansAbonnementCriteriaTest {

    @Test
    void newPlansAbonnementCriteriaHasAllFiltersNullTest() {
        var plansAbonnementCriteria = new PlansAbonnementCriteria();
        assertThat(plansAbonnementCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void plansAbonnementCriteriaFluentMethodsCreatesFiltersTest() {
        var plansAbonnementCriteria = new PlansAbonnementCriteria();

        setAllFilters(plansAbonnementCriteria);

        assertThat(plansAbonnementCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void plansAbonnementCriteriaCopyCreatesNullFilterTest() {
        var plansAbonnementCriteria = new PlansAbonnementCriteria();
        var copy = plansAbonnementCriteria.copy();

        assertThat(plansAbonnementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(plansAbonnementCriteria)
        );
    }

    @Test
    void plansAbonnementCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var plansAbonnementCriteria = new PlansAbonnementCriteria();
        setAllFilters(plansAbonnementCriteria);

        var copy = plansAbonnementCriteria.copy();

        assertThat(plansAbonnementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(plansAbonnementCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var plansAbonnementCriteria = new PlansAbonnementCriteria();

        assertThat(plansAbonnementCriteria).hasToString("PlansAbonnementCriteria{}");
    }

    private static void setAllFilters(PlansAbonnementCriteria plansAbonnementCriteria) {
        plansAbonnementCriteria.id();
        plansAbonnementCriteria.libelle();
        plansAbonnementCriteria.prix();
        plansAbonnementCriteria.duree();
        plansAbonnementCriteria.deleteAt();
        plansAbonnementCriteria.createdBy();
        plansAbonnementCriteria.createdDate();
        plansAbonnementCriteria.lastModifiedBy();
        plansAbonnementCriteria.lastModifiedDate();
        plansAbonnementCriteria.abonnementId();
        plansAbonnementCriteria.paiementId();
        plansAbonnementCriteria.distinct();
    }

    private static Condition<PlansAbonnementCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLibelle()) &&
                condition.apply(criteria.getPrix()) &&
                condition.apply(criteria.getDuree()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getAbonnementId()) &&
                condition.apply(criteria.getPaiementId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PlansAbonnementCriteria> copyFiltersAre(
        PlansAbonnementCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLibelle(), copy.getLibelle()) &&
                condition.apply(criteria.getPrix(), copy.getPrix()) &&
                condition.apply(criteria.getDuree(), copy.getDuree()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getAbonnementId(), copy.getAbonnementId()) &&
                condition.apply(criteria.getPaiementId(), copy.getPaiementId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
