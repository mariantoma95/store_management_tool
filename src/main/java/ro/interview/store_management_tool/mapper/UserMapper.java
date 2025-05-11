package ro.interview.store_management_tool.mapper;

import org.mapstruct.Mapper;
import ro.interview.store_management_tool.dto.UserDto;
import ro.interview.store_management_tool.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUserToUserDto(User user);
}
