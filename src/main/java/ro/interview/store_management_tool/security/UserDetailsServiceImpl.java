package ro.interview.store_management_tool.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.interview.store_management_tool.dto.UserDto;
import ro.interview.store_management_tool.security.user.AuthUser;
import ro.interview.store_management_tool.service.UserService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.getUserCredentialsByUsername(username);

        return new AuthUser(
                userDto.username(),
                userDto.roles(),
                userDto.passwordHash());
    }
}
