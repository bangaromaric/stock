package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Categorie;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.service.dto.CategorieDTO;
import ga.aninf.stock.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorieLibelle")
    ProduitDTO toDto(Produit s);

    @Named("categorieLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    CategorieDTO toDtoCategorieLibelle(Categorie categorie);
}
