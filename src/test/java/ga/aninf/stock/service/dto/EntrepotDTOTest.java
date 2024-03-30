package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EntrepotDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntrepotDTO.class);
        EntrepotDTO entrepotDTO1 = new EntrepotDTO();
        entrepotDTO1.setId(UUID.randomUUID());
        EntrepotDTO entrepotDTO2 = new EntrepotDTO();
        assertThat(entrepotDTO1).isNotEqualTo(entrepotDTO2);
        entrepotDTO2.setId(entrepotDTO1.getId());
        assertThat(entrepotDTO1).isEqualTo(entrepotDTO2);
        entrepotDTO2.setId(UUID.randomUUID());
        assertThat(entrepotDTO1).isNotEqualTo(entrepotDTO2);
        entrepotDTO1.setId(null);
        assertThat(entrepotDTO1).isNotEqualTo(entrepotDTO2);
    }
}
