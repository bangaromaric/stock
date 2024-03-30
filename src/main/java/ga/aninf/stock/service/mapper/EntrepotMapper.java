package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.dto.StructureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entrepot} and its DTO {@link EntrepotDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntrepotMapper extends EntityMapper<EntrepotDTO, Entrepot> {
    @Mapping(target = "structure", source = "structure", qualifiedByName = "structureLibelle")
    EntrepotDTO toDto(Entrepot s);

    @Named("structureLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    StructureDTO toDtoStructureLibelle(Structure structure);
}
