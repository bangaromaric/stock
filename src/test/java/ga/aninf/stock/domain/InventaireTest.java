package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.EntrepotTestSamples.*;
import static ga.aninf.stock.domain.InventaireTestSamples.*;
import static ga.aninf.stock.domain.ProduitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventaire.class);
        Inventaire inventaire1 = getInventaireSample1();
        Inventaire inventaire2 = new Inventaire();
        assertThat(inventaire1).isNotEqualTo(inventaire2);

        inventaire2.setId(inventaire1.getId());
        assertThat(inventaire1).isEqualTo(inventaire2);

        inventaire2 = getInventaireSample2();
        assertThat(inventaire1).isNotEqualTo(inventaire2);
    }

    @Test
    void entrepotTest() throws Exception {
        Inventaire inventaire = getInventaireRandomSampleGenerator();
        Entrepot entrepotBack = getEntrepotRandomSampleGenerator();

        inventaire.setEntrepot(entrepotBack);
        assertThat(inventaire.getEntrepot()).isEqualTo(entrepotBack);

        inventaire.entrepot(null);
        assertThat(inventaire.getEntrepot()).isNull();
    }

    @Test
    void produitTest() throws Exception {
        Inventaire inventaire = getInventaireRandomSampleGenerator();
        Produit produitBack = getProduitRandomSampleGenerator();

        inventaire.setProduit(produitBack);
        assertThat(inventaire.getProduit()).isEqualTo(produitBack);

        inventaire.produit(null);
        assertThat(inventaire.getProduit()).isNull();
    }
}
