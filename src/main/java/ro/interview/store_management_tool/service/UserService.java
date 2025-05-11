package ro.interview.store_management_tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool.dto.UserDto;
import ro.interview.store_management_tool.exception.UserNotFoundException;
import ro.interview.store_management_tool.mapper.UserMapper;
import ro.interview.store_management_tool.model.User;
import ro.interview.store_management_tool.repository.UserRepository;

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
