package ga.aninf.stock.domain;

import static ga.aninf.stock.domain.EmployeTestSamples.*;
import static ga.aninf.stock.domain.StructureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employe.class);
        Employe employe1 = getEmployeSample1();
        Employe employe2 = new Employe();
        assertThat(employe1).isNotEqualTo(employe2);

        employe2.setId(employe1.getId());
        assertThat(employe1).isEqualTo(employe2);

        employe2 = getEmployeSample2();
        assertThat(employe1).isNotEqualTo(employe2);
    }

    @Test
    void structureTest() throws Exception {
        Employe employe = getEmployeRandomSampleGenerator();
        Structure structureBack = getStructureRandomSampleGenerator();

        employe.setStructure(structureBack);
        assertThat(employe.getStructure()).isEqualTo(structureBack);

        employe.structure(null);
        assertThat(employe.getStructure()).isNull();
    }
}
