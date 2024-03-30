package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.AbonnementTestSamples.*;
import static ga.aninf.stock.domain.PaiementTestSamples.*;
import static ga.aninf.stock.domain.PlansAbonnementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlansAbonnementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlansAbonnement.class);
        PlansAbonnement plansAbonnement1 = getPlansAbonnementSample1();
        PlansAbonnement plansAbonnement2 = new PlansAbonnement();
        assertThat(plansAbonnement1).isNotEqualTo(plansAbonnement2);

        plansAbonnement2.setId(plansAbonnement1.getId());
        assertThat(plansAbonnement1).isEqualTo(plansAbonnement2);

        plansAbonnement2 = getPlansAbonnementSample2();
        assertThat(plansAbonnement1).isNotEqualTo(plansAbonnement2);
    }

    @Test
    void abonnementTest() throws Exception {
        PlansAbonnement plansAbonnement = getPlansAbonnementRandomSampleGenerator();
        Abonnement abonnementBack = getAbonnementRandomSampleGenerator();

        plansAbonnement.addAbonnement(abonnementBack);
        assertThat(plansAbonnement.getAbonnements()).containsOnly(abonnementBack);
        assertThat(abonnementBack.getPlansAbonnement()).isEqualTo(plansAbonnement);

        plansAbonnement.removeAbonnement(abonnementBack);
        assertThat(plansAbonnement.getAbonnements()).doesNotContain(abonnementBack);
        assertThat(abonnementBack.getPlansAbonnement()).isNull();

        plansAbonnement.abonnements(new HashSet<>(Set.of(abonnementBack)));
        assertThat(plansAbonnement.getAbonnements()).containsOnly(abonnementBack);
        assertThat(abonnementBack.getPlansAbonnement()).isEqualTo(plansAbonnement);

        plansAbonnement.setAbonnements(new HashSet<>());
        assertThat(plansAbonnement.getAbonnements()).doesNotContain(abonnementBack);
        assertThat(abonnementBack.getPlansAbonnement()).isNull();
    }

    @Test
    void paiementTest() throws Exception {
        PlansAbonnement plansAbonnement = getPlansAbonnementRandomSampleGenerator();
        Paiement paiementBack = getPaiementRandomSampleGenerator();

        plansAbonnement.addPaiement(paiementBack);
        assertThat(plansAbonnement.getPaiements()).containsOnly(paiementBack);
        assertThat(paiementBack.getPlansAbonnement()).isEqualTo(plansAbonnement);

        plansAbonnement.removePaiement(paiementBack);
        assertThat(plansAbonnement.getPaiements()).doesNotContain(paiementBack);
        assertThat(paiementBack.getPlansAbonnement()).isNull();

        plansAbonnement.paiements(new HashSet<>(Set.of(paiementBack)));
        assertThat(plansAbonnement.getPaiements()).containsOnly(paiementBack);
        assertThat(paiementBack.getPlansAbonnement()).isEqualTo(plansAbonnement);

        plansAbonnement.setPaiements(new HashSet<>());
        assertThat(plansAbonnement.getPaiements()).doesNotContain(paiementBack);
        assertThat(paiementBack.getPlansAbonnement()).isNull();
    }
}
