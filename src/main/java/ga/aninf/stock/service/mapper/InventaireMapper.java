package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.Inventaire;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.dto.InventaireDTO;
import ga.aninf.stock.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventaire} and its DTO {@link InventaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface InventaireMapper extends EntityMapper<InventaireDTO, Inventaire> {
    @Mapping(target = "entrepot", source = "entrepot", qualifiedByName = "entrepotLibelle")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitLibelle")
    InventaireDTO toDto(Inventaire s);

    @Named("entrepotLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    EntrepotDTO toDtoEntrepotLibelle(Entrepot entrepot);

    @Named("produitLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    ProduitDTO toDtoProduitLibelle(Produit produit);
}
