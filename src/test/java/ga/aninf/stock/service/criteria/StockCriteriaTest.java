package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StockCriteriaTest {

    @Test
    void newStockCriteriaHasAllFiltersNullTest() {
        var stockCriteria = new StockCriteria();
        assertThat(stockCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void stockCriteriaFluentMethodsCreatesFiltersTest() {
        var stockCriteria = new StockCriteria();

        setAllFilters(stockCriteria);

        assertThat(stockCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void stockCriteriaCopyCreatesNullFilterTest() {
        var stockCriteria = new StockCriteria();
        var copy = stockCriteria.copy();

        assertThat(stockCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(stockCriteria)
        );
    }

    @Test
    void stockCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var stockCriteria = new StockCriteria();
        setAllFilters(stockCriteria);

        var copy = stockCriteria.copy();

        assertThat(stockCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(stockCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var stockCriteria = new StockCriteria();

        assertThat(stockCriteria).hasToString("StockCriteria{}");
    }

    private static void setAllFilters(StockCriteria stockCriteria) {
        stockCriteria.id();
        stockCriteria.quantite();
        stockCriteria.deleteAt();
        stockCriteria.createdBy();
        stockCriteria.createdDate();
        stockCriteria.lastModifiedBy();
        stockCriteria.lastModifiedDate();
        stockCriteria.entrepotId();
        stockCriteria.produitId();
        stockCriteria.distinct();
    }

    private static Condition<StockCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getQuantite()) &&
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

    private static Condition<StockCriteria> copyFiltersAre(StockCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getQuantite(), copy.getQuantite()) &&
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
