package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.CategorieTestSamples.*;
import static ga.aninf.stock.domain.InventaireTestSamples.*;
import static ga.aninf.stock.domain.MouvementStockTestSamples.*;
import static ga.aninf.stock.domain.ProduitTestSamples.*;
import static ga.aninf.stock.domain.StockTestSamples.*;
import static ga.aninf.stock.domain.VenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProduitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produit.class);
        Produit produit1 = getProduitSample1();
        Produit produit2 = new Produit();
        assertThat(produit1).isNotEqualTo(produit2);

        produit2.setId(produit1.getId());
        assertThat(produit1).isEqualTo(produit2);

        produit2 = getProduitSample2();
        assertThat(produit1).isNotEqualTo(produit2);
    }

    @Test
    void categorieTest() throws Exception {
        Produit produit = getProduitRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        produit.setCategorie(categorieBack);
        assertThat(produit.getCategorie()).isEqualTo(categorieBack);

        produit.categorie(null);
        assertThat(produit.getCategorie()).isNull();
    }

    @Test
    void mouvementStockTest() throws Exception {
        Produit produit = getProduitRandomSampleGenerator();
        MouvementStock mouvementStockBack = getMouvementStockRandomSampleGenerator();

        produit.addMouvementStock(mouvementStockBack);
        assertThat(produit.getMouvementStocks()).containsOnly(mouvementStockBack);
        assertThat(mouvementStockBack.getProduit()).isEqualTo(produit);

        produit.removeMouvementStock(mouvementStockBack);
        assertThat(produit.getMouvementStocks()).doesNotContain(mouvementStockBack);
        assertThat(mouvementStockBack.getProduit()).isNull();

        produit.mouvementStocks(new HashSet<>(Set.of(mouvementStockBack)));
        assertThat(produit.getMouvementStocks()).containsOnly(mouvementStockBack);
        assertThat(mouvementStockBack.getProduit()).isEqualTo(produit);

        produit.setMouvementStocks(new HashSet<>());
        assertThat(produit.getMouvementStocks()).doesNotContain(mouvementStockBack);
        assertThat(mouvementStockBack.getProduit()).isNull();
    }

    @Test
    void stockTest() throws Exception {
        Produit produit = getProduitRandomSampleGenerator();
        Stock stockBack = getStockRandomSampleGenerator();

        produit.addStock(stockBack);
        assertThat(produit.getStocks()).containsOnly(stockBack);
        assertThat(stockBack.getProduit()).isEqualTo(produit);

        produit.removeStock(stockBack);
        assertThat(produit.getStocks()).doesNotContain(stockBack);
        assertThat(stockBack.getProduit()).isNull();

        produit.stocks(new HashSet<>(Set.of(stockBack)));
        assertThat(produit.getStocks()).containsOnly(stockBack);
        assertThat(stockBack.getProduit()).isEqualTo(produit);

        produit.setStocks(new HashSet<>());
        assertThat(produit.getStocks()).doesNotContain(stockBack);
        assertThat(stockBack.getProduit()).isNull();
    }

    @Test
    void inventaireTest() throws Exception {
        Produit produit = getProduitRandomSampleGenerator();
        Inventaire inventaireBack = getInventaireRandomSampleGenerator();

        produit.addInventaire(inventaireBack);
        assertThat(produit.getInventaires()).containsOnly(inventaireBack);
        assertThat(inventaireBack.getProduit()).isEqualTo(produit);

        produit.removeInventaire(inventaireBack);
        assertThat(produit.getInventaires()).doesNotContain(inventaireBack);
        assertThat(inventaireBack.getProduit()).isNull();

        produit.inventaires(new HashSet<>(Set.of(inventaireBack)));
        assertThat(produit.getInventaires()).containsOnly(inventaireBack);
        assertThat(inventaireBack.getProduit()).isEqualTo(produit);

        produit.setInventaires(new HashSet<>());
        assertThat(produit.getInventaires()).doesNotContain(inventaireBack);
        assertThat(inventaireBack.getProduit()).isNull();
    }

    @Test
    void venteTest() throws Exception {
        Produit produit = getProduitRandomSampleGenerator();
        Vente venteBack = getVenteRandomSampleGenerator();

        produit.addVente(venteBack);
        assertThat(produit.getVentes()).containsOnly(venteBack);
        assertThat(venteBack.getProduit()).isEqualTo(produit);

        produit.removeVente(venteBack);
        assertThat(produit.getVentes()).doesNotContain(venteBack);
        assertThat(venteBack.getProduit()).isNull();

        produit.ventes(new HashSet<>(Set.of(venteBack)));
        assertThat(produit.getVentes()).containsOnly(venteBack);
        assertThat(venteBack.getProduit()).isEqualTo(produit);

        produit.setVentes(new HashSet<>());
        assertThat(produit.getVentes()).doesNotContain(venteBack);
        assertThat(venteBack.getProduit()).isNull();
    }
}
