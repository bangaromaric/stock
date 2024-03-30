package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.MouvementStockAsserts.*;
import static ga.aninf.stock.domain.MouvementStockTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MouvementStockMapperTest {

    private MouvementStockMapper mouvementStockMapper;

    @BeforeEach
    void setUp() {
        mouvementStockMapper = new MouvementStockMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMouvementStockSample1();
        var actual = mouvementStockMapper.toEntity(mouvementStockMapper.toDto(expected));
        assertMouvementStockAllPropertiesEquals(expected, actual);
    }
}
