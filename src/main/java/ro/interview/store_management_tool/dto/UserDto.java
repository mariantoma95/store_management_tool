package ro.interview.store_management_tool.dto;

import ro.interview.store_management_tool.util.Role;

import java.util.List;

public record UserDto(
        Long id,
        String username,
        String passwordHash,
        List<Role> roles,
        Boolean active) {
}
