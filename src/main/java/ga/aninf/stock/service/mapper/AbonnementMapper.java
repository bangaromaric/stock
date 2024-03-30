package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Abonnement;
import ga.aninf.stock.domain.Paiement;
import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.service.dto.AbonnementDTO;
import ga.aninf.stock.service.dto.PaiementDTO;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
import ga.aninf.stock.service.dto.StructureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Abonnement} and its DTO {@link AbonnementDTO}.
 */
@Mapper(componentModel = "spring")
public interface AbonnementMapper extends EntityMapper<AbonnementDTO, Abonnement> {
    @Mapping(target = "structure", source = "structure", qualifiedByName = "structureLibelle")
    @Mapping(target = "plansAbonnement", source = "plansAbonnement", qualifiedByName = "plansAbonnementLibelle")
    @Mapping(target = "paiement", source = "paiement", qualifiedByName = "paiementMontant")
    AbonnementDTO toDto(Abonnement s);

    @Named("structureLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    StructureDTO toDtoStructureLibelle(Structure structure);

    @Named("plansAbonnementLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    PlansAbonnementDTO toDtoPlansAbonnementLibelle(PlansAbonnement plansAbonnement);

    @Named("paiementMontant")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "montant", source = "montant")
    PaiementDTO toDtoPaiementMontant(Paiement paiement);
}
