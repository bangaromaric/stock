package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Paiement;
import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.service.dto.PaiementDTO;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {
    @Mapping(target = "plansAbonnement", source = "plansAbonnement", qualifiedByName = "plansAbonnementLibelle")
    PaiementDTO toDto(Paiement s);

    @Named("plansAbonnementLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    PlansAbonnementDTO toDtoPlansAbonnementLibelle(PlansAbonnement plansAbonnement);
}
