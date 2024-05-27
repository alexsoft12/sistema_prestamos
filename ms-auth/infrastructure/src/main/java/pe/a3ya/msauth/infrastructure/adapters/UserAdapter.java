package pe.a3ya.msauth.infrastructure.adapters;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.a3ya.msauth.domain.aggregates.dto.ReniecDto;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;
import pe.a3ya.msauth.domain.ports.out.UserServiceOut;
import pe.a3ya.msauth.infrastructure.clients.ApisNetReniecClient;
import pe.a3ya.msauth.infrastructure.dao.UserRepository;
import pe.a3ya.msauth.infrastructure.entities.UserEntity;
import pe.a3ya.msauth.infrastructure.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserServiceOut {

    private final UserRepository userRepository;
    private final ApisNetReniecClient reniecClient;

    @Value("${token.apis_net}")
    private String token;

    private ReniecDto getExecReniec(String numeroDocumento) {
        String authorization = "Bearer " + token;
        return reniecClient.getReniecInfo(numeroDocumento, authorization);
    }

    private UserEntity getUserEntity(UserRequest userRequest) {
        ReniecDto reniecDto = getExecReniec(userRequest.getDocumentNumber());
        UserEntity userEntity = new UserEntity();
        userEntity.setDocumentType(reniecDto.getTipoDocumento());
        userEntity.setDocumentNumber(reniecDto.getNumeroDocumento());
        userEntity.setName(reniecDto.getNombres());
        userEntity.setLastName(reniecDto.getApellidoPaterno());
        userEntity.setMotherLastName(reniecDto.getApellidoMaterno());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setDateBirth(userRequest.getBirthDate());
        return userEntity;
    }

    @Override
    public UserDto save(UserRequest userRequest) {
        UserEntity userEntity = getUserEntity(userRequest);
        return UserMapper.fromEntityToDto(userRepository.save(userEntity));
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(UserMapper.fromEntityToDto(userEntity));
    }

    @Override
    public List<UserDto> getAll() {
        return UserMapper.fromEntityToDtoList(userRepository.findAll());
    }

    @Override
    public UserDto update(Long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            return null;
        }
        updateUserEntity(userEntity, userRequest);
        return UserMapper.fromEntityToDto(userRepository.save(userEntity));
    }

    private void updateUserEntity(UserEntity userEntity, UserRequest userRequest) {
        ReniecDto reniecDto = getExecReniec(userRequest.getDocumentNumber());
        userEntity.setDocumentType(reniecDto.getTipoDocumento());
        userEntity.setDocumentNumber(reniecDto.getNumeroDocumento());
        userEntity.setName(reniecDto.getNombres());
        userEntity.setLastName(reniecDto.getApellidoPaterno());
        userEntity.setMotherLastName(reniecDto.getApellidoMaterno());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setDateBirth(userRequest.getBirthDate());
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null) {
            userEntity.onDeleted();
            userRepository.delete(userEntity);
        }
    }


}
