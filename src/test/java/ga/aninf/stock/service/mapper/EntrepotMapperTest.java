package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.EntrepotAsserts.*;
import static ga.aninf.stock.domain.EntrepotTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrepotMapperTest {

    private EntrepotMapper entrepotMapper;

    @BeforeEach
    void setUp() {
        entrepotMapper = new EntrepotMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEntrepotSample1();
        var actual = entrepotMapper.toEntity(entrepotMapper.toDto(expected));
        assertEntrepotAllPropertiesEquals(expected, actual);
    }
}
