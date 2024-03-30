package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlansAbonnement} and its DTO {@link PlansAbonnementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlansAbonnementMapper extends EntityMapper<PlansAbonnementDTO, PlansAbonnement> {}
