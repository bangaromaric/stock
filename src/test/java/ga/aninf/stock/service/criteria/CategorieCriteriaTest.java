package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CategorieCriteriaTest {

    @Test
    void newCategorieCriteriaHasAllFiltersNullTest() {
        var categorieCriteria = new CategorieCriteria();
        assertThat(categorieCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void categorieCriteriaFluentMethodsCreatesFiltersTest() {
        var categorieCriteria = new CategorieCriteria();

        setAllFilters(categorieCriteria);

        assertThat(categorieCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void categorieCriteriaCopyCreatesNullFilterTest() {
        var categorieCriteria = new CategorieCriteria();
        var copy = categorieCriteria.copy();

        assertThat(categorieCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(categorieCriteria)
        );
    }

    @Test
    void categorieCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var categorieCriteria = new CategorieCriteria();
        setAllFilters(categorieCriteria);

        var copy = categorieCriteria.copy();

        assertThat(categorieCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(categorieCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var categorieCriteria = new CategorieCriteria();

        assertThat(categorieCriteria).hasToString("CategorieCriteria{}");
    }

    private static void setAllFilters(CategorieCriteria categorieCriteria) {
        categorieCriteria.id();
        categorieCriteria.libelle();
        categorieCriteria.deleteAt();
        categorieCriteria.createdBy();
        categorieCriteria.createdDate();
        categorieCriteria.lastModifiedBy();
        categorieCriteria.lastModifiedDate();
        categorieCriteria.produitId();
        categorieCriteria.distinct();
    }

    private static Condition<CategorieCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLibelle()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getProduitId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CategorieCriteria> copyFiltersAre(CategorieCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLibelle(), copy.getLibelle()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getProduitId(), copy.getProduitId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
