package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.StructureAsserts.*;
import static ga.aninf.stock.domain.StructureTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructureMapperTest {

    private StructureMapper structureMapper;

    @BeforeEach
    void setUp() {
        structureMapper = new StructureMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStructureSample1();
        var actual = structureMapper.toEntity(structureMapper.toDto(expected));
        assertStructureAllPropertiesEquals(expected, actual);
    }
}
