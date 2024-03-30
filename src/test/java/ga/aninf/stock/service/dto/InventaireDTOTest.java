package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class InventaireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventaireDTO.class);
        InventaireDTO inventaireDTO1 = new InventaireDTO();
        inventaireDTO1.setId(UUID.randomUUID());
        InventaireDTO inventaireDTO2 = new InventaireDTO();
        assertThat(inventaireDTO1).isNotEqualTo(inventaireDTO2);
        inventaireDTO2.setId(inventaireDTO1.getId());
        assertThat(inventaireDTO1).isEqualTo(inventaireDTO2);
        inventaireDTO2.setId(UUID.randomUUID());
        assertThat(inventaireDTO1).isNotEqualTo(inventaireDTO2);
        inventaireDTO1.setId(null);
        assertThat(inventaireDTO1).isNotEqualTo(inventaireDTO2);
    }
}
