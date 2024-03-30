package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class VenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VenteDTO.class);
        VenteDTO venteDTO1 = new VenteDTO();
        venteDTO1.setId(UUID.randomUUID());
        VenteDTO venteDTO2 = new VenteDTO();
        assertThat(venteDTO1).isNotEqualTo(venteDTO2);
        venteDTO2.setId(venteDTO1.getId());
        assertThat(venteDTO1).isEqualTo(venteDTO2);
        venteDTO2.setId(UUID.randomUUID());
        assertThat(venteDTO1).isNotEqualTo(venteDTO2);
        venteDTO1.setId(null);
        assertThat(venteDTO1).isNotEqualTo(venteDTO2);
    }
}
