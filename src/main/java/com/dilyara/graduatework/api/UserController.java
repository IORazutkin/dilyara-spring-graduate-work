package com.dilyara.graduatework.api;

import com.dilyara.graduatework.dto.CreateNewUserDto;
import com.dilyara.graduatework.dto.UpdatePasswordDto;
import com.dilyara.graduatework.dto.UpdateUserContactDto;
import com.dilyara.graduatework.dto.UpdateUserDto;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<User> getAuthorizationUser(@AuthenticationPrincipal User auth) {
    return ResponseEntity.ok(auth);
  }

  @PostMapping
  public ResponseEntity<User> createUser (
    @RequestBody CreateNewUserDto userDto
  ) {
    return ResponseEntity.ok(userService.saveUser(userDto));
  }

  @PostMapping("/avatar")
  public ResponseEntity<User> updateAvatar (
    @AuthenticationPrincipal User auth,
    @RequestParam("file") MultipartFile file
  ) throws IOException {
    return ResponseEntity.ok(userService.updateAvatar(auth, file));
  }

  @PutMapping
  public ResponseEntity<User> updateUser (
    @AuthenticationPrincipal User auth,
    @RequestBody UpdateUserDto userDto
  ) {
    return ResponseEntity.ok(userService.updateUser(auth, userDto));
  }

  @PutMapping("/password")
  public ResponseEntity<Boolean> changePassword (
    @AuthenticationPrincipal User auth,
    @RequestBody UpdatePasswordDto passwordDto
  ) {
    return ResponseEntity.ok(userService.updatePassword(auth, passwordDto));
  }

  @PatchMapping("/contacts")
  public ResponseEntity<User> updateUserContacts (
    @AuthenticationPrincipal User auth,
    @RequestBody UpdateUserContactDto userContactDto
  ) {
    return ResponseEntity.ok(userService.updateUserContacts(auth, userContactDto));
  }
}
