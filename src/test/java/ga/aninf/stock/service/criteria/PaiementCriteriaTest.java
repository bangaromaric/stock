package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PaiementCriteriaTest {

    @Test
    void newPaiementCriteriaHasAllFiltersNullTest() {
        var paiementCriteria = new PaiementCriteria();
        assertThat(paiementCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void paiementCriteriaFluentMethodsCreatesFiltersTest() {
        var paiementCriteria = new PaiementCriteria();

        setAllFilters(paiementCriteria);

        assertThat(paiementCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void paiementCriteriaCopyCreatesNullFilterTest() {
        var paiementCriteria = new PaiementCriteria();
        var copy = paiementCriteria.copy();

        assertThat(paiementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(paiementCriteria)
        );
    }

    @Test
    void paiementCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var paiementCriteria = new PaiementCriteria();
        setAllFilters(paiementCriteria);

        var copy = paiementCriteria.copy();

        assertThat(paiementCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(paiementCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var paiementCriteria = new PaiementCriteria();

        assertThat(paiementCriteria).hasToString("PaiementCriteria{}");
    }

    private static void setAllFilters(PaiementCriteria paiementCriteria) {
        paiementCriteria.id();
        paiementCriteria.montant();
        paiementCriteria.methodePaiement();
        paiementCriteria.statutPaiement();
        paiementCriteria.deleteAt();
        paiementCriteria.createdBy();
        paiementCriteria.createdDate();
        paiementCriteria.lastModifiedBy();
        paiementCriteria.lastModifiedDate();
        paiementCriteria.plansAbonnementId();
        paiementCriteria.abonnementId();
        paiementCriteria.distinct();
    }

    private static Condition<PaiementCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMontant()) &&
                condition.apply(criteria.getMethodePaiement()) &&
                condition.apply(criteria.getStatutPaiement()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getPlansAbonnementId()) &&
                condition.apply(criteria.getAbonnementId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PaiementCriteria> copyFiltersAre(PaiementCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMontant(), copy.getMontant()) &&
                condition.apply(criteria.getMethodePaiement(), copy.getMethodePaiement()) &&
                condition.apply(criteria.getStatutPaiement(), copy.getStatutPaiement()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getPlansAbonnementId(), copy.getPlansAbonnementId()) &&
                condition.apply(criteria.getAbonnementId(), copy.getAbonnementId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
