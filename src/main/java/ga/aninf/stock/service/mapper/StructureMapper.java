package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.service.dto.StructureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Structure} and its DTO {@link StructureDTO}.
 */
@Mapper(componentModel = "spring")
public interface StructureMapper extends EntityMapper<StructureDTO, Structure> {}
