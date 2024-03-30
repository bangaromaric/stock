package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.PlansAbonnementAsserts.*;
import static ga.aninf.stock.domain.PlansAbonnementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlansAbonnementMapperTest {

    private PlansAbonnementMapper plansAbonnementMapper;

    @BeforeEach
    void setUp() {
        plansAbonnementMapper = new PlansAbonnementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPlansAbonnementSample1();
        var actual = plansAbonnementMapper.toEntity(plansAbonnementMapper.toDto(expected));
        assertPlansAbonnementAllPropertiesEquals(expected, actual);
    }
}
