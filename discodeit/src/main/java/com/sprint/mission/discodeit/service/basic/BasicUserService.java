package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.UserCreateRequest;
import com.sprint.mission.discodeit.dto.UserResponse;
import com.sprint.mission.discodeit.dto.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

//이 클래스는 어떻게 생성할지에 대한 실제 코드
@RequiredArgsConstructor
@Service
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    //유저 만들 때 .save만 하는게 아니고 상태도 .save해야 해서
    private final BinaryContentRepository binaryContentRepository;
    //유저 가입할 때 프로필 이미지 등록하니까(선택)

    @Override
    public User create(UserCreateRequest request, Optional<BinaryContentCreateRequest> optionalProfileCreateRequest) {
        if (userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        UUID nullableProfileId = optionalProfileCreateRequest
                .map(profileRequest -> {
                    String fileName = profileRequest.fileName();
                    String contentType = profileRequest.contentType();
                    byte[] bytes = profileRequest.bytes();
                    BinaryContent binaryContent = new BinaryContent(fileName, (long)bytes.length, contentType, bytes);
                    return binaryContentRepository.save(binaryContent).getId();
                })
                .orElse(null);

        //중복이 없으면 이 유저 정보로 만들기
        User user = new User(request.username(), request.email(), request.password(), nullableProfileId);
        User createdUser = userRepository.save(user);
        Instant now = Instant.now();
        UserStatus userStatus = new UserStatus(createdUser.getId(), now);
        userStatusRepository.save(userStatus);
        return createdUser;

    }

    @Override
    public UserResponse find(UUID userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
            UserStatus userStatus = userStatusRepository.findById(userId)
                    .orElse(null);
            return  new UserResponse(user.getId(), user.getUsername(), user.getEmail(), userStatus);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserStatus status = userStatusRepository.findById(user.id()).orElse(null);
                    return new UserResponse(user.id(), user.username(), user.email(), status);
                })
                .toList();
    }

    @Override
    public User update(UUID userId, String newUsername, String newEmail, String newPassword, UUID newProfileId) {
        return null; //오류 없앨려고 임시로 더 만듬
    }

//    @Override
//    public User update(UUID userId, UserUpdateRequest request,Optional<BinaryContentCreateRequest> newProfileId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
//        user.update(request.userName(),request.email(), request.password(), newProfileId);
//        return userRepository.save(user);
//    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }
}
