package ro.interview.store_management_tool_test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool_test.dto.UserDto;
import ro.interview.store_management_tool_test.exception.UserNotFoundException;
import ro.interview.store_management_tool_test.mapper.UserMapper;
import ro.interview.store_management_tool_test.model.User;
import ro.interview.store_management_tool_test.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUserCredentialsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        return userMapper.mapUserToUserDto(user);
    }
}
