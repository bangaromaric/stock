package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PlansAbonnementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlansAbonnementDTO.class);
        PlansAbonnementDTO plansAbonnementDTO1 = new PlansAbonnementDTO();
        plansAbonnementDTO1.setId(UUID.randomUUID());
        PlansAbonnementDTO plansAbonnementDTO2 = new PlansAbonnementDTO();
        assertThat(plansAbonnementDTO1).isNotEqualTo(plansAbonnementDTO2);
        plansAbonnementDTO2.setId(plansAbonnementDTO1.getId());
        assertThat(plansAbonnementDTO1).isEqualTo(plansAbonnementDTO2);
        plansAbonnementDTO2.setId(UUID.randomUUID());
        assertThat(plansAbonnementDTO1).isNotEqualTo(plansAbonnementDTO2);
        plansAbonnementDTO1.setId(null);
        assertThat(plansAbonnementDTO1).isNotEqualTo(plansAbonnementDTO2);
    }
}
