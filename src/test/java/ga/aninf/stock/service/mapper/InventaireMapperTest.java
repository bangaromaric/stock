package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.InventaireAsserts.*;
import static ga.aninf.stock.domain.InventaireTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventaireMapperTest {

    private InventaireMapper inventaireMapper;

    @BeforeEach
    void setUp() {
        inventaireMapper = new InventaireMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInventaireSample1();
        var actual = inventaireMapper.toEntity(inventaireMapper.toDto(expected));
        assertInventaireAllPropertiesEquals(expected, actual);
    }
}
