package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.ProduitTestSamples.*;
import static ga.aninf.stock.domain.StructureTestSamples.*;
import static ga.aninf.stock.domain.VenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vente.class);
        Vente vente1 = getVenteSample1();
        Vente vente2 = new Vente();
        assertThat(vente1).isNotEqualTo(vente2);

        vente2.setId(vente1.getId());
        assertThat(vente1).isEqualTo(vente2);

        vente2 = getVenteSample2();
        assertThat(vente1).isNotEqualTo(vente2);
    }

    @Test
    void produitTest() throws Exception {
        Vente vente = getVenteRandomSampleGenerator();
        Produit produitBack = getProduitRandomSampleGenerator();

        vente.setProduit(produitBack);
        assertThat(vente.getProduit()).isEqualTo(produitBack);

        vente.produit(null);
        assertThat(vente.getProduit()).isNull();
    }

    @Test
    void structureTest() throws Exception {
        Vente vente = getVenteRandomSampleGenerator();
        Structure structureBack = getStructureRandomSampleGenerator();

        vente.setStructure(structureBack);
        assertThat(vente.getStructure()).isEqualTo(structureBack);

        vente.structure(null);
        assertThat(vente.getStructure()).isNull();
    }
}
