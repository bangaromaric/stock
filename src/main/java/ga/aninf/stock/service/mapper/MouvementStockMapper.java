package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.MouvementStock;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.dto.MouvementStockDTO;
import ga.aninf.stock.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MouvementStock} and its DTO {@link MouvementStockDTO}.
 */
@Mapper(componentModel = "spring")
public interface MouvementStockMapper extends EntityMapper<MouvementStockDTO, MouvementStock> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitLibelle")
    @Mapping(target = "entrepot", source = "entrepot", qualifiedByName = "entrepotLibelle")
    MouvementStockDTO toDto(MouvementStock s);

    @Named("produitLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    ProduitDTO toDtoProduitLibelle(Produit produit);

    @Named("entrepotLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    EntrepotDTO toDtoEntrepotLibelle(Entrepot entrepot);
}
