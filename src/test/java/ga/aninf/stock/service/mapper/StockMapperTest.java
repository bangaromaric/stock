package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.StockAsserts.*;
import static ga.aninf.stock.domain.StockTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockMapperTest {

    private StockMapper stockMapper;

    @BeforeEach
    void setUp() {
        stockMapper = new StockMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getStockSample1();
        var actual = stockMapper.toEntity(stockMapper.toDto(expected));
        assertStockAllPropertiesEquals(expected, actual);
    }
}
