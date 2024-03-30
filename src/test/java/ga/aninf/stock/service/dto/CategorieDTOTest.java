package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CategorieDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieDTO.class);
        CategorieDTO categorieDTO1 = new CategorieDTO();
        categorieDTO1.setId(UUID.randomUUID());
        CategorieDTO categorieDTO2 = new CategorieDTO();
        assertThat(categorieDTO1).isNotEqualTo(categorieDTO2);
        categorieDTO2.setId(categorieDTO1.getId());
        assertThat(categorieDTO1).isEqualTo(categorieDTO2);
        categorieDTO2.setId(UUID.randomUUID());
        assertThat(categorieDTO1).isNotEqualTo(categorieDTO2);
        categorieDTO1.setId(null);
        assertThat(categorieDTO1).isNotEqualTo(categorieDTO2);
    }
}
