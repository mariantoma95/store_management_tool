package ro.interview.store_management_tool_test.mapper;

import org.mapstruct.Mapper;
import ro.interview.store_management_tool_test.dto.UserDto;
import ro.interview.store_management_tool_test.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUserToUserDto(User user);
}
