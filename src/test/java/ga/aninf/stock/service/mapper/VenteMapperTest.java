package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.VenteAsserts.*;
import static ga.aninf.stock.domain.VenteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenteMapperTest {

    private VenteMapper venteMapper;

    @BeforeEach
    void setUp() {
        venteMapper = new VenteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVenteSample1();
        var actual = venteMapper.toEntity(venteMapper.toDto(expected));
        assertVenteAllPropertiesEquals(expected, actual);
    }
}
