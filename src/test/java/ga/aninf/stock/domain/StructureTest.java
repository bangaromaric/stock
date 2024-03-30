package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.AbonnementTestSamples.*;
import static ga.aninf.stock.domain.EmployeTestSamples.*;
import static ga.aninf.stock.domain.EntrepotTestSamples.*;
import static ga.aninf.stock.domain.StructureTestSamples.*;
import static ga.aninf.stock.domain.VenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class StructureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Structure.class);
        Structure structure1 = getStructureSample1();
        Structure structure2 = new Structure();
        assertThat(structure1).isNotEqualTo(structure2);

        structure2.setId(structure1.getId());
        assertThat(structure1).isEqualTo(structure2);

        structure2 = getStructureSample2();
        assertThat(structure1).isNotEqualTo(structure2);
    }

    @Test
    void entrepotTest() throws Exception {
        Structure structure = getStructureRandomSampleGenerator();
        Entrepot entrepotBack = getEntrepotRandomSampleGenerator();

        structure.addEntrepot(entrepotBack);
        assertThat(structure.getEntrepots()).containsOnly(entrepotBack);
        assertThat(entrepotBack.getStructure()).isEqualTo(structure);

        structure.removeEntrepot(entrepotBack);
        assertThat(structure.getEntrepots()).doesNotContain(entrepotBack);
        assertThat(entrepotBack.getStructure()).isNull();

        structure.entrepots(new HashSet<>(Set.of(entrepotBack)));
        assertThat(structure.getEntrepots()).containsOnly(entrepotBack);
        assertThat(entrepotBack.getStructure()).isEqualTo(structure);

        structure.setEntrepots(new HashSet<>());
        assertThat(structure.getEntrepots()).doesNotContain(entrepotBack);
        assertThat(entrepotBack.getStructure()).isNull();
    }

    @Test
    void employeTest() throws Exception {
        Structure structure = getStructureRandomSampleGenerator();
        Employe employeBack = getEmployeRandomSampleGenerator();

        structure.addEmploye(employeBack);
        assertThat(structure.getEmployes()).containsOnly(employeBack);
        assertThat(employeBack.getStructure()).isEqualTo(structure);

        structure.removeEmploye(employeBack);
        assertThat(structure.getEmployes()).doesNotContain(employeBack);
        assertThat(employeBack.getStructure()).isNull();

        structure.employes(new HashSet<>(Set.of(employeBack)));
        assertThat(structure.getEmployes()).containsOnly(employeBack);
        assertThat(employeBack.getStructure()).isEqualTo(structure);

        structure.setEmployes(new HashSet<>());
        assertThat(structure.getEmployes()).doesNotContain(employeBack);
        assertThat(employeBack.getStructure()).isNull();
    }

    @Test
    void venteTest() throws Exception {
        Structure structure = getStructureRandomSampleGenerator();
        Vente venteBack = getVenteRandomSampleGenerator();

        structure.addVente(venteBack);
        assertThat(structure.getVentes()).containsOnly(venteBack);
        assertThat(venteBack.getStructure()).isEqualTo(structure);

        structure.removeVente(venteBack);
        assertThat(structure.getVentes()).doesNotContain(venteBack);
        assertThat(venteBack.getStructure()).isNull();

        structure.ventes(new HashSet<>(Set.of(venteBack)));
        assertThat(structure.getVentes()).containsOnly(venteBack);
        assertThat(venteBack.getStructure()).isEqualTo(structure);

        structure.setVentes(new HashSet<>());
        assertThat(structure.getVentes()).doesNotContain(venteBack);
        assertThat(venteBack.getStructure()).isNull();
    }

    @Test
    void abonnementTest() throws Exception {
        Structure structure = getStructureRandomSampleGenerator();
        Abonnement abonnementBack = getAbonnementRandomSampleGenerator();

        structure.addAbonnement(abonnementBack);
        assertThat(structure.getAbonnements()).containsOnly(abonnementBack);
        assertThat(abonnementBack.getStructure()).isEqualTo(structure);

        structure.removeAbonnement(abonnementBack);
        assertThat(structure.getAbonnements()).doesNotContain(abonnementBack);
        assertThat(abonnementBack.getStructure()).isNull();

        structure.abonnements(new HashSet<>(Set.of(abonnementBack)));
        assertThat(structure.getAbonnements()).containsOnly(abonnementBack);
        assertThat(abonnementBack.getStructure()).isEqualTo(structure);

        structure.setAbonnements(new HashSet<>());
        assertThat(structure.getAbonnements()).doesNotContain(abonnementBack);
        assertThat(abonnementBack.getStructure()).isNull();
    }
}
