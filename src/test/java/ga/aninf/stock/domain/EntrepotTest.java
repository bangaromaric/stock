package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.EntrepotTestSamples.*;
import static ga.aninf.stock.domain.InventaireTestSamples.*;
import static ga.aninf.stock.domain.MouvementStockTestSamples.*;
import static ga.aninf.stock.domain.StockTestSamples.*;
import static ga.aninf.stock.domain.StructureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EntrepotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrepot.class);
        Entrepot entrepot1 = getEntrepotSample1();
        Entrepot entrepot2 = new Entrepot();
        assertThat(entrepot1).isNotEqualTo(entrepot2);

        entrepot2.setId(entrepot1.getId());
        assertThat(entrepot1).isEqualTo(entrepot2);

        entrepot2 = getEntrepotSample2();
        assertThat(entrepot1).isNotEqualTo(entrepot2);
    }

    @Test
    void structureTest() throws Exception {
        Entrepot entrepot = getEntrepotRandomSampleGenerator();
        Structure structureBack = getStructureRandomSampleGenerator();

        entrepot.setStructure(structureBack);
        assertThat(entrepot.getStructure()).isEqualTo(structureBack);

        entrepot.structure(null);
        assertThat(entrepot.getStructure()).isNull();
    }

    @Test
    void mouvementStockTest() throws Exception {
        Entrepot entrepot = getEntrepotRandomSampleGenerator();
        MouvementStock mouvementStockBack = getMouvementStockRandomSampleGenerator();

        entrepot.addMouvementStock(mouvementStockBack);
        assertThat(entrepot.getMouvementStocks()).containsOnly(mouvementStockBack);
        assertThat(mouvementStockBack.getEntrepot()).isEqualTo(entrepot);

        entrepot.removeMouvementStock(mouvementStockBack);
        assertThat(entrepot.getMouvementStocks()).doesNotContain(mouvementStockBack);
        assertThat(mouvementStockBack.getEntrepot()).isNull();

        entrepot.mouvementStocks(new HashSet<>(Set.of(mouvementStockBack)));
        assertThat(entrepot.getMouvementStocks()).containsOnly(mouvementStockBack);
        assertThat(mouvementStockBack.getEntrepot()).isEqualTo(entrepot);

        entrepot.setMouvementStocks(new HashSet<>());
        assertThat(entrepot.getMouvementStocks()).doesNotContain(mouvementStockBack);
        assertThat(mouvementStockBack.getEntrepot()).isNull();
    }

    @Test
    void stockTest() throws Exception {
        Entrepot entrepot = getEntrepotRandomSampleGenerator();
        Stock stockBack = getStockRandomSampleGenerator();

        entrepot.addStock(stockBack);
        assertThat(entrepot.getStocks()).containsOnly(stockBack);
        assertThat(stockBack.getEntrepot()).isEqualTo(entrepot);

        entrepot.removeStock(stockBack);
        assertThat(entrepot.getStocks()).doesNotContain(stockBack);
        assertThat(stockBack.getEntrepot()).isNull();

        entrepot.stocks(new HashSet<>(Set.of(stockBack)));
        assertThat(entrepot.getStocks()).containsOnly(stockBack);
        assertThat(stockBack.getEntrepot()).isEqualTo(entrepot);

        entrepot.setStocks(new HashSet<>());
        assertThat(entrepot.getStocks()).doesNotContain(stockBack);
        assertThat(stockBack.getEntrepot()).isNull();
    }

    @Test
    void inventaireTest() throws Exception {
        Entrepot entrepot = getEntrepotRandomSampleGenerator();
        Inventaire inventaireBack = getInventaireRandomSampleGenerator();

        entrepot.addInventaire(inventaireBack);
        assertThat(entrepot.getInventaires()).containsOnly(inventaireBack);
        assertThat(inventaireBack.getEntrepot()).isEqualTo(entrepot);

        entrepot.removeInventaire(inventaireBack);
        assertThat(entrepot.getInventaires()).doesNotContain(inventaireBack);
        assertThat(inventaireBack.getEntrepot()).isNull();

        entrepot.inventaires(new HashSet<>(Set.of(inventaireBack)));
        assertThat(entrepot.getInventaires()).containsOnly(inventaireBack);
        assertThat(inventaireBack.getEntrepot()).isEqualTo(entrepot);

        entrepot.setInventaires(new HashSet<>());
        assertThat(entrepot.getInventaires()).doesNotContain(inventaireBack);
        assertThat(inventaireBack.getEntrepot()).isNull();
    }
}
