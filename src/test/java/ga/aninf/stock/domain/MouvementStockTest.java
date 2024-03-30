package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.EntrepotTestSamples.*;
import static ga.aninf.stock.domain.MouvementStockTestSamples.*;
import static ga.aninf.stock.domain.ProduitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MouvementStockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MouvementStock.class);
        MouvementStock mouvementStock1 = getMouvementStockSample1();
        MouvementStock mouvementStock2 = new MouvementStock();
        assertThat(mouvementStock1).isNotEqualTo(mouvementStock2);

        mouvementStock2.setId(mouvementStock1.getId());
        assertThat(mouvementStock1).isEqualTo(mouvementStock2);

        mouvementStock2 = getMouvementStockSample2();
        assertThat(mouvementStock1).isNotEqualTo(mouvementStock2);
    }

    @Test
    void produitTest() throws Exception {
        MouvementStock mouvementStock = getMouvementStockRandomSampleGenerator();
        Produit produitBack = getProduitRandomSampleGenerator();

        mouvementStock.setProduit(produitBack);
        assertThat(mouvementStock.getProduit()).isEqualTo(produitBack);

        mouvementStock.produit(null);
        assertThat(mouvementStock.getProduit()).isNull();
    }

    @Test
    void entrepotTest() throws Exception {
        MouvementStock mouvementStock = getMouvementStockRandomSampleGenerator();
        Entrepot entrepotBack = getEntrepotRandomSampleGenerator();

        mouvementStock.setEntrepot(entrepotBack);
        assertThat(mouvementStock.getEntrepot()).isEqualTo(entrepotBack);

        mouvementStock.entrepot(null);
        assertThat(mouvementStock.getEntrepot()).isNull();
    }
}
