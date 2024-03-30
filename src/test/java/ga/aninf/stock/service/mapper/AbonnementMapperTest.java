package ga.aninf.stock.service.mapper;

import static ga.aninf.stock.domain.AbonnementAsserts.*;
import static ga.aninf.stock.domain.AbonnementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbonnementMapperTest {

    private AbonnementMapper abonnementMapper;

    @BeforeEach
    void setUp() {
        abonnementMapper = new AbonnementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAbonnementSample1();
        var actual = abonnementMapper.toEntity(abonnementMapper.toDto(expected));
        assertAbonnementAllPropertiesEquals(expected, actual);
    }
}
