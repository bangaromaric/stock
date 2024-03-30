package ga.aninf.stock.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ga.aninf.stock.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StructureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StructureDTO.class);
        StructureDTO structureDTO1 = new StructureDTO();
        structureDTO1.setId(UUID.randomUUID());
        StructureDTO structureDTO2 = new StructureDTO();
        assertThat(structureDTO1).isNotEqualTo(structureDTO2);
        structureDTO2.setId(structureDTO1.getId());
        assertThat(structureDTO1).isEqualTo(structureDTO2);
        structureDTO2.setId(UUID.randomUUID());
        assertThat(structureDTO1).isNotEqualTo(structureDTO2);
        structureDTO1.setId(null);
        assertThat(structureDTO1).isNotEqualTo(structureDTO2);
    }
}
