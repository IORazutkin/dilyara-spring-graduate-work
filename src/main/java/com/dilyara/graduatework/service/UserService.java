package com.dilyara.graduatework.service;

import com.dilyara.graduatework.dto.CreateNewUserDto;
import com.dilyara.graduatework.dto.UpdatePasswordDto;
import com.dilyara.graduatework.dto.UpdateUserContactDto;
import com.dilyara.graduatework.dto.UpdateUserDto;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.exception.InternalException;
import com.dilyara.graduatework.exception.UserAlreadyExistException;
import com.dilyara.graduatework.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

  public Boolean userIsExist (String username) {
    return userRepo.findByUsername(username) != null;
  }

  public User saveUser (CreateNewUserDto userDto) {
    if (userIsExist(userDto.getUsername())) {
      throw new UserAlreadyExistException();
    }

    User user = userDto.toUser();
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepo.save(user);
  }

  public User updateAvatar (User user, MultipartFile file) throws IOException {
    String filePath = fileService.loadFile(file);
    fileService.removeFile(user.getAvatar());
    user.setAvatar(filePath);

    return userRepo.save(user);
  }

  public User updateUser (User user, UpdateUserDto userDto) {
    if (userDto.getFullName() != null) {
      user.setFullName(userDto.getFullName());
    }

    user.setBusinessDate(userDto.getBusinessDate());
    user.setStartCapital(userDto.getStartCapital());

    return userRepo.save(user);
  }

  public User updateUserContacts (User user, UpdateUserContactDto userContactDto) {
    if (userContactDto.getEmail() != null) {
      user.setEmail(userContactDto.getEmail());
    }

    if (userContactDto.getPhone() != null) {
      user.setPhone(userContactDto.getPhone());
    }

    return userRepo.save(user);
  }

  public Boolean updatePassword (User user, UpdatePasswordDto passwordDto) {
    if (!(passwordDto.isPasswordEquals() && passwordDto.isNewPasswordValid())) {
      throw new InternalException();
    }

    if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
      throw new InternalException();
    }

    user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
    userRepo.save(user);

    return true;
  }
}
