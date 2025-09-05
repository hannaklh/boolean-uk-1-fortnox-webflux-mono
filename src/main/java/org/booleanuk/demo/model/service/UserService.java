package org.booleanuk.demo.model.service;

import org.booleanuk.demo.model.dto.UserRequestDto;
import org.booleanuk.demo.model.dto.UserResponseDto;
import org.booleanuk.demo.model.dto.UserResponseWithMessagesDto;
import org.booleanuk.demo.model.jpa.User;
import org.booleanuk.demo.model.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }
@Transactional(readOnly=true)
    public UserResponseWithMessagesDto getById(long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
        return new UserResponseWithMessagesDto(user);
    }

    public UserResponseDto createUser(UserRequestDto userDto) {
        User user = new User(userDto);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public UserResponseDto updateUser(long id, UserRequestDto userRequestDto) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        user.setUsername(userRequestDto.getUsername());
        userRepository.save(user);

        return new UserResponseDto(user);


    }
    public void deleteUser(long id)throws Exception{
        User user = userRepository.findById(id)
                .orElseThrow(()-> new Exception("User not found"));
        userRepository.delete(user);
    }
}
