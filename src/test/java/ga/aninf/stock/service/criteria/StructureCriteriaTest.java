package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StructureCriteriaTest {

    @Test
    void newStructureCriteriaHasAllFiltersNullTest() {
        var structureCriteria = new StructureCriteria();
        assertThat(structureCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void structureCriteriaFluentMethodsCreatesFiltersTest() {
        var structureCriteria = new StructureCriteria();

        setAllFilters(structureCriteria);

        assertThat(structureCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void structureCriteriaCopyCreatesNullFilterTest() {
        var structureCriteria = new StructureCriteria();
        var copy = structureCriteria.copy();

        assertThat(structureCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(structureCriteria)
        );
    }

    @Test
    void structureCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var structureCriteria = new StructureCriteria();
        setAllFilters(structureCriteria);

        var copy = structureCriteria.copy();

        assertThat(structureCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(structureCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var structureCriteria = new StructureCriteria();

        assertThat(structureCriteria).hasToString("StructureCriteria{}");
    }

    private static void setAllFilters(StructureCriteria structureCriteria) {
        structureCriteria.id();
        structureCriteria.libelle();
        structureCriteria.telephone();
        structureCriteria.email();
        structureCriteria.adresse();
        structureCriteria.deleteAt();
        structureCriteria.createdBy();
        structureCriteria.createdDate();
        structureCriteria.lastModifiedBy();
        structureCriteria.lastModifiedDate();
        structureCriteria.entrepotId();
        structureCriteria.employeId();
        structureCriteria.venteId();
        structureCriteria.abonnementId();
        structureCriteria.distinct();
    }

    private static Condition<StructureCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLibelle()) &&
                condition.apply(criteria.getTelephone()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getAdresse()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getEntrepotId()) &&
                condition.apply(criteria.getEmployeId()) &&
                condition.apply(criteria.getVenteId()) &&
                condition.apply(criteria.getAbonnementId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StructureCriteria> copyFiltersAre(StructureCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLibelle(), copy.getLibelle()) &&
                condition.apply(criteria.getTelephone(), copy.getTelephone()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getAdresse(), copy.getAdresse()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getEntrepotId(), copy.getEntrepotId()) &&
                condition.apply(criteria.getEmployeId(), copy.getEmployeId()) &&
                condition.apply(criteria.getVenteId(), copy.getVenteId()) &&
                condition.apply(criteria.getAbonnementId(), copy.getAbonnementId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
