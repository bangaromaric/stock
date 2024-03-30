package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.PaiementAsserts.*;
import static ga.aninf.stock.domain.PaiementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaiementMapperTest {

    private PaiementMapper paiementMapper;

    @BeforeEach
    void setUp() {
        paiementMapper = new PaiementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaiementSample1();
        var actual = paiementMapper.toEntity(paiementMapper.toDto(expected));
        assertPaiementAllPropertiesEquals(expected, actual);
    }
}
