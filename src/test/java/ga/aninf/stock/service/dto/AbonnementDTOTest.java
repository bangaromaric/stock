package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AbonnementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbonnementDTO.class);
        AbonnementDTO abonnementDTO1 = new AbonnementDTO();
        abonnementDTO1.setId(UUID.randomUUID());
        AbonnementDTO abonnementDTO2 = new AbonnementDTO();
        assertThat(abonnementDTO1).isNotEqualTo(abonnementDTO2);
        abonnementDTO2.setId(abonnementDTO1.getId());
        assertThat(abonnementDTO1).isEqualTo(abonnementDTO2);
        abonnementDTO2.setId(UUID.randomUUID());
        assertThat(abonnementDTO1).isNotEqualTo(abonnementDTO2);
        abonnementDTO1.setId(null);
        assertThat(abonnementDTO1).isNotEqualTo(abonnementDTO2);
    }
}
