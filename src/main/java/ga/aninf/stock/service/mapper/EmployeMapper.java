package ga.aninf.stock.service.mapper;

import ga.aninf.stock.domain.Employe;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.domain.User;
import ga.aninf.stock.service.dto.EmployeDTO;
import ga.aninf.stock.service.dto.StructureDTO;
import ga.aninf.stock.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employe} and its DTO {@link EmployeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeMapper extends EntityMapper<EmployeDTO, Employe> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    @Mapping(target = "structure", source = "structure", qualifiedByName = "structureLibelle")
    EmployeDTO toDto(Employe s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("structureLibelle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "libelle", source = "libelle")
    StructureDTO toDtoStructureLibelle(Structure structure);
}
