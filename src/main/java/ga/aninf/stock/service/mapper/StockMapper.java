package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.domain.Stock;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.dto.ProduitDTO;
import ga.aninf.stock.service.dto.StockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring")
public interface StockMapper extends EntityMapper<StockDTO, Stock> {
    @Mapping(target = "entrepot", source = "entrepot", qualifiedByName = "entrepotLibelle")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitLibelle")
    StockDTO toDto(Stock s);

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
