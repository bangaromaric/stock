package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmployeCriteriaTest {

    @Test
    void newEmployeCriteriaHasAllFiltersNullTest() {
        var employeCriteria = new EmployeCriteria();
        assertThat(employeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void employeCriteriaFluentMethodsCreatesFiltersTest() {
        var employeCriteria = new EmployeCriteria();

        setAllFilters(employeCriteria);

        assertThat(employeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void employeCriteriaCopyCreatesNullFilterTest() {
        var employeCriteria = new EmployeCriteria();
        var copy = employeCriteria.copy();

        assertThat(employeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(employeCriteria)
        );
    }

    @Test
    void employeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var employeCriteria = new EmployeCriteria();
        setAllFilters(employeCriteria);

        var copy = employeCriteria.copy();

        assertThat(employeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(employeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var employeCriteria = new EmployeCriteria();

        assertThat(employeCriteria).hasToString("EmployeCriteria{}");
    }

    private static void setAllFilters(EmployeCriteria employeCriteria) {
        employeCriteria.id();
        employeCriteria.firstName();
        employeCriteria.lastName();
        employeCriteria.login();
        employeCriteria.email();
        employeCriteria.deleteAt();
        employeCriteria.createdBy();
        employeCriteria.createdDate();
        employeCriteria.lastModifiedBy();
        employeCriteria.lastModifiedDate();
        employeCriteria.internalUserId();
        employeCriteria.structureId();
        employeCriteria.distinct();
    }

    private static Condition<EmployeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFirstName()) &&
                condition.apply(criteria.getLastName()) &&
                condition.apply(criteria.getLogin()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getInternalUserId()) &&
                condition.apply(criteria.getStructureId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmployeCriteria> copyFiltersAre(EmployeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFirstName(), copy.getFirstName()) &&
                condition.apply(criteria.getLastName(), copy.getLastName()) &&
                condition.apply(criteria.getLogin(), copy.getLogin()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getInternalUserId(), copy.getInternalUserId()) &&
                condition.apply(criteria.getStructureId(), copy.getStructureId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
