package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PermissionCriteriaTest {

    @Test
    void newPermissionCriteriaHasAllFiltersNullTest() {
        var permissionCriteria = new PermissionCriteria();
        assertThat(permissionCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void permissionCriteriaFluentMethodsCreatesFiltersTest() {
        var permissionCriteria = new PermissionCriteria();

        setAllFilters(permissionCriteria);

        assertThat(permissionCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void permissionCriteriaCopyCreatesNullFilterTest() {
        var permissionCriteria = new PermissionCriteria();
        var copy = permissionCriteria.copy();

        assertThat(permissionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(permissionCriteria)
        );
    }

    @Test
    void permissionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var permissionCriteria = new PermissionCriteria();
        setAllFilters(permissionCriteria);

        var copy = permissionCriteria.copy();

        assertThat(permissionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(permissionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var permissionCriteria = new PermissionCriteria();

        assertThat(permissionCriteria).hasToString("PermissionCriteria{}");
    }

    private static void setAllFilters(PermissionCriteria permissionCriteria) {
        permissionCriteria.id();
        permissionCriteria.ressource();
        permissionCriteria.action();
        permissionCriteria.deleteAt();
        permissionCriteria.createdDate();
        permissionCriteria.createdBy();
        permissionCriteria.lastModifiedDate();
        permissionCriteria.lastModifiedBy();
        permissionCriteria.authorityId();
        permissionCriteria.distinct();
    }

    private static Condition<PermissionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRessource()) &&
                condition.apply(criteria.getAction()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getAuthorityId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PermissionCriteria> copyFiltersAre(PermissionCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRessource(), copy.getRessource()) &&
                condition.apply(criteria.getAction(), copy.getAction()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getAuthorityId(), copy.getAuthorityId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
