package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.controller.api.LoginApi;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginApi {


  private final AuthService authService;

  @Override
  @PostMapping("/api/auth/login")
  public ResponseEntity<User> login(
      @RequestBody LoginRequest loginRequest
  ) {
    User loginUser = authService.login(loginRequest);
    return ResponseEntity.ok(loginUser);

  }
}
