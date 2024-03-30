package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PaiementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementDTO.class);
        PaiementDTO paiementDTO1 = new PaiementDTO();
        paiementDTO1.setId(UUID.randomUUID());
        PaiementDTO paiementDTO2 = new PaiementDTO();
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO2.setId(paiementDTO1.getId());
        assertThat(paiementDTO1).isEqualTo(paiementDTO2);
        paiementDTO2.setId(UUID.randomUUID());
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
        paiementDTO1.setId(null);
        assertThat(paiementDTO1).isNotEqualTo(paiementDTO2);
    }
}
