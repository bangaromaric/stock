package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.domain.Vente;
import ga.aninf.stock.service.dto.ProduitDTO;
import ga.aninf.stock.service.dto.StructureDTO;
import ga.aninf.stock.service.dto.VenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vente} and its DTO {@link VenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface VenteMapper extends EntityMapper<VenteDTO, Vente> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitLibelle")
    @Mapping(target = "structure", source = "structure", qualifiedByName = "structureLibelle")
    VenteDTO toDto(Vente s);

    @Named("produitLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    ProduitDTO toDtoProduitLibelle(Produit produit);

    @Named("structureLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    StructureDTO toDtoStructureLibelle(Structure structure);
}
