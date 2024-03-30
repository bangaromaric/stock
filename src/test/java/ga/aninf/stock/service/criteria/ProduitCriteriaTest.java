package ga.aninf.stock.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ProduitCriteriaTest {

    @Test
    void newProduitCriteriaHasAllFiltersNullTest() {
        var produitCriteria = new ProduitCriteria();
        assertThat(produitCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void produitCriteriaFluentMethodsCreatesFiltersTest() {
        var produitCriteria = new ProduitCriteria();

        setAllFilters(produitCriteria);

        assertThat(produitCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void produitCriteriaCopyCreatesNullFilterTest() {
        var produitCriteria = new ProduitCriteria();
        var copy = produitCriteria.copy();

        assertThat(produitCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(produitCriteria)
        );
    }

    @Test
    void produitCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var produitCriteria = new ProduitCriteria();
        setAllFilters(produitCriteria);

        var copy = produitCriteria.copy();

        assertThat(produitCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(produitCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var produitCriteria = new ProduitCriteria();

        assertThat(produitCriteria).hasToString("ProduitCriteria{}");
    }

    private static void setAllFilters(ProduitCriteria produitCriteria) {
        produitCriteria.id();
        produitCriteria.libelle();
        produitCriteria.slug();
        produitCriteria.prixUnitaire();
        produitCriteria.dateExpiration();
        produitCriteria.deleteAt();
        produitCriteria.createdBy();
        produitCriteria.createdDate();
        produitCriteria.lastModifiedBy();
        produitCriteria.lastModifiedDate();
        produitCriteria.categorieId();
        produitCriteria.mouvementStockId();
        produitCriteria.stockId();
        produitCriteria.inventaireId();
        produitCriteria.venteId();
        produitCriteria.distinct();
    }

    private static Condition<ProduitCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLibelle()) &&
                condition.apply(criteria.getSlug()) &&
                condition.apply(criteria.getPrixUnitaire()) &&
                condition.apply(criteria.getDateExpiration()) &&
                condition.apply(criteria.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate()) &&
                condition.apply(criteria.getCategorieId()) &&
                condition.apply(criteria.getMouvementStockId()) &&
                condition.apply(criteria.getStockId()) &&
                condition.apply(criteria.getInventaireId()) &&
                condition.apply(criteria.getVenteId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ProduitCriteria> copyFiltersAre(ProduitCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLibelle(), copy.getLibelle()) &&
                condition.apply(criteria.getSlug(), copy.getSlug()) &&
                condition.apply(criteria.getPrixUnitaire(), copy.getPrixUnitaire()) &&
                condition.apply(criteria.getDateExpiration(), copy.getDateExpiration()) &&
                condition.apply(criteria.getDeleteAt(), copy.getDeleteAt()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getLastModifiedBy(), copy.getLastModifiedBy()) &&
                condition.apply(criteria.getLastModifiedDate(), copy.getLastModifiedDate()) &&
                condition.apply(criteria.getCategorieId(), copy.getCategorieId()) &&
                condition.apply(criteria.getMouvementStockId(), copy.getMouvementStockId()) &&
                condition.apply(criteria.getStockId(), copy.getStockId()) &&
                condition.apply(criteria.getInventaireId(), copy.getInventaireId()) &&
                condition.apply(criteria.getVenteId(), copy.getVenteId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
