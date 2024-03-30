package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AbonnementCriteriaTest {

    @Test
    void newAbonnementCriteriaHasAllFiltersNullTest() {
        var abonnementCriteria = new AbonnementCriteria();
        assertThat(abonnementCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void abonnementCriteriaFluentMethodsCreatesFiltersTest() {
        var abonnementCriteria = new AbonnementCriteria();

        setAllFilters(abonnementCriteria);

        assertThat(abonnementCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void abonnementCriteriaCopyCreatesNullFilterTest() {
        var abonnementCriteria = new AbonnementCriteria();
        var copy = abonnementCriteria.copy();

        assertThat(abonnementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(abonnementCriteria)
        );
    }

    @Test
    void abonnementCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var abonnementCriteria = new AbonnementCriteria();
        setAllFilters(abonnementCriteria);

        var copy = abonnementCriteria.copy();

        assertThat(abonnementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(abonnementCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var abonnementCriteria = new AbonnementCriteria();

        assertThat(abonnementCriteria).hasToString("AbonnementCriteria{}");
    }

    private static void setAllFilters(AbonnementCriteria abonnementCriteria) {
        abonnementCriteria.id();
        abonnementCriteria.dateDebut();
        abonnementCriteria.dateFin();
        abonnementCriteria.statutAbonnement();
        abonnementCriteria.prix();
        abonnementCriteria.deleteAt();
        abonnementCriteria.createdBy();
        abonnementCriteria.createdDate();
        abonnementCriteria.lastModifiedBy();
        abonnementCriteria.lastModifiedDate();
        abonnementCriteria.structureId();
        abonnementCriteria.plansAbonnementId();
        abonnementCriteria.paiementId();
        abonnementCriteria.distinct();
    }

    private static Condition<AbonnementCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDateDebut()) &&
                condition.apply(criteria.getDateFin()) &&
                condition.apply(criteria.getStatutAbonnement()) &&
                condition.apply(criteria.getPrix()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getStructureId()) &&
                condition.apply(criteria.getPlansAbonnementId()) &&
                condition.apply(criteria.getPaiementId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AbonnementCriteria> copyFiltersAre(AbonnementCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDateDebut(), copy.getDateDebut()) &&
                condition.apply(criteria.getDateFin(), copy.getDateFin()) &&
                condition.apply(criteria.getStatutAbonnement(), copy.getStatutAbonnement()) &&
                condition.apply(criteria.getPrix(), copy.getPrix()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getStructureId(), copy.getStructureId()) &&
                condition.apply(criteria.getPlansAbonnementId(), copy.getPlansAbonnementId()) &&
                condition.apply(criteria.getPaiementId(), copy.getPaiementId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
