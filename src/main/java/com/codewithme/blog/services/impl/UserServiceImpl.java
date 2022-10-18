package com.codewithme.blog.services.impl;

import com.codewithme.blog.entities.UserEntity;
import com.codewithme.blog.exceptions.ResourceNotFoundException;
import com.codewithme.blog.payloads.UserDTO;
import com.codewithme.blog.repositories.UserRepository;
import com.codewithme.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = convertDTOtoEntity(userDTO);
        UserEntity savedUser = userRepository.save(userEntity);

        return convertEntityToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        UserEntity userEntity =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(" User ", " Id ", userId));
        // userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setAbout(userDTO.getAbout());

        UserEntity updateUserd = userRepository.save(userEntity);
        return convertEntityToDTO(updateUserd);
//        return userRepository.save(userEntity);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(" User ", " id ", userId));

        return convertEntityToDTO(userEntity);
    }

    @Override
    public List<UserDTO> getAllUser() {
        // java7
//        List<UserDTO> userDTOList = new ArrayList<>();
//        List<UserEntity> userEntity = userRepository.findAll();
//        for (UserEntity user : userEntity) {
//            userDTOList.add(convertEntityToDTO(user));
//        }
//        return userDTOList;
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDTO> userDTOS = userEntities.stream().map(userEntity -> convertEntityToDTO(userEntity))
                .collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public void deleteUser(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(" User ", " Id ", userId));
        userRepository.delete(userEntity);
    }
    public UserEntity convertDTOtoEntity(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        return userEntity;
    }
    public UserDTO convertEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
    }

   /* public UserEntity convertDTOtoEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setAbout(userDTO.getAbout());
        return userEntity;
    }
    public UserDTO convertEntityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setAbout(userEntity.getAbout());
        return userDTO;
    }*/
}
