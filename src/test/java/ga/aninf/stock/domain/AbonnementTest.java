package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.AbonnementTestSamples.*;
import static ga.aninf.stock.domain.PaiementTestSamples.*;
import static ga.aninf.stock.domain.PlansAbonnementTestSamples.*;
import static ga.aninf.stock.domain.StructureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AbonnementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Abonnement.class);
        Abonnement abonnement1 = getAbonnementSample1();
        Abonnement abonnement2 = new Abonnement();
        assertThat(abonnement1).isNotEqualTo(abonnement2);

        abonnement2.setId(abonnement1.getId());
        assertThat(abonnement1).isEqualTo(abonnement2);

        abonnement2 = getAbonnementSample2();
        assertThat(abonnement1).isNotEqualTo(abonnement2);
    }

    @Test
    void structureTest() throws Exception {
        Abonnement abonnement = getAbonnementRandomSampleGenerator();
        Structure structureBack = getStructureRandomSampleGenerator();

        abonnement.setStructure(structureBack);
        assertThat(abonnement.getStructure()).isEqualTo(structureBack);

        abonnement.structure(null);
        assertThat(abonnement.getStructure()).isNull();
    }

    @Test
    void plansAbonnementTest() throws Exception {
        Abonnement abonnement = getAbonnementRandomSampleGenerator();
        PlansAbonnement plansAbonnementBack = getPlansAbonnementRandomSampleGenerator();

        abonnement.setPlansAbonnement(plansAbonnementBack);
        assertThat(abonnement.getPlansAbonnement()).isEqualTo(plansAbonnementBack);

        abonnement.plansAbonnement(null);
        assertThat(abonnement.getPlansAbonnement()).isNull();
    }

    @Test
    void paiementTest() throws Exception {
        Abonnement abonnement = getAbonnementRandomSampleGenerator();
        Paiement paiementBack = getPaiementRandomSampleGenerator();

        abonnement.setPaiement(paiementBack);
        assertThat(abonnement.getPaiement()).isEqualTo(paiementBack);

        abonnement.paiement(null);
        assertThat(abonnement.getPaiement()).isNull();
    }
}
