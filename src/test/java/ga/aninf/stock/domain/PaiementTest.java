package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.AbonnementTestSamples.*;
import static ga.aninf.stock.domain.PaiementTestSamples.*;
import static ga.aninf.stock.domain.PlansAbonnementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PaiementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiement.class);
        Paiement paiement1 = getPaiementSample1();
        Paiement paiement2 = new Paiement();
        assertThat(paiement1).isNotEqualTo(paiement2);

        paiement2.setId(paiement1.getId());
        assertThat(paiement1).isEqualTo(paiement2);

        paiement2 = getPaiementSample2();
        assertThat(paiement1).isNotEqualTo(paiement2);
    }

    @Test
    void plansAbonnementTest() throws Exception {
        Paiement paiement = getPaiementRandomSampleGenerator();
        PlansAbonnement plansAbonnementBack = getPlansAbonnementRandomSampleGenerator();

        paiement.setPlansAbonnement(plansAbonnementBack);
        assertThat(paiement.getPlansAbonnement()).isEqualTo(plansAbonnementBack);

        paiement.plansAbonnement(null);
        assertThat(paiement.getPlansAbonnement()).isNull();
    }

    @Test
    void abonnementTest() throws Exception {
        Paiement paiement = getPaiementRandomSampleGenerator();
        Abonnement abonnementBack = getAbonnementRandomSampleGenerator();

        paiement.addAbonnement(abonnementBack);
        assertThat(paiement.getAbonnements()).containsOnly(abonnementBack);
        assertThat(abonnementBack.getPaiement()).isEqualTo(paiement);

        paiement.removeAbonnement(abonnementBack);
        assertThat(paiement.getAbonnements()).doesNotContain(abonnementBack);
        assertThat(abonnementBack.getPaiement()).isNull();

        paiement.abonnements(new HashSet<>(Set.of(abonnementBack)));
        assertThat(paiement.getAbonnements()).containsOnly(abonnementBack);
        assertThat(abonnementBack.getPaiement()).isEqualTo(paiement);

        paiement.setAbonnements(new HashSet<>());
        assertThat(paiement.getAbonnements()).doesNotContain(abonnementBack);
        assertThat(abonnementBack.getPaiement()).isNull();
    }
}
