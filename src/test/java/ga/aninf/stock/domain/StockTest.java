package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.EntrepotTestSamples.*;
import static ga.aninf.stock.domain.ProduitTestSamples.*;
import static ga.aninf.stock.domain.StockTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stock.class);
        Stock stock1 = getStockSample1();
        Stock stock2 = new Stock();
        assertThat(stock1).isNotEqualTo(stock2);

        stock2.setId(stock1.getId());
        assertThat(stock1).isEqualTo(stock2);

        stock2 = getStockSample2();
        assertThat(stock1).isNotEqualTo(stock2);
    }

    @Test
    void entrepotTest() throws Exception {
        Stock stock = getStockRandomSampleGenerator();
        Entrepot entrepotBack = getEntrepotRandomSampleGenerator();

        stock.setEntrepot(entrepotBack);
        assertThat(stock.getEntrepot()).isEqualTo(entrepotBack);

        stock.entrepot(null);
        assertThat(stock.getEntrepot()).isNull();
    }

    @Test
    void produitTest() throws Exception {
        Stock stock = getStockRandomSampleGenerator();
        Produit produitBack = getProduitRandomSampleGenerator();

        stock.setProduit(produitBack);
        assertThat(stock.getProduit()).isEqualTo(produitBack);

        stock.produit(null);
        assertThat(stock.getProduit()).isNull();
    }
}
