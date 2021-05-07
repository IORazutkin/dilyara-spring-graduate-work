package com.dilyara.graduatework.api;

import com.dilyara.graduatework.dto.facebook.InstagramPostListDto;
import com.dilyara.graduatework.dto.facebook.StatDto;
import com.dilyara.graduatework.dto.facebook.TargetDto;
import com.dilyara.graduatework.dto.facebook.WeeklyStatDto;
import com.dilyara.graduatework.entity.InstagramPost;
import com.dilyara.graduatework.entity.User;
import com.dilyara.graduatework.service.FacebookService;
import com.dilyara.graduatework.service.FacebookStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/facebook")
@RequiredArgsConstructor
public class FacebookController {
  private final FacebookService facebookService;
  private final FacebookStatService facebookStatService;

  @GetMapping
  public ResponseEntity<User> getAccessToken (
    @AuthenticationPrincipal User auth,
    @RequestParam String code,
    @RequestParam String state,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    response.setHeader("Location", "http://localhost:3000/main/profile");

    return new ResponseEntity<>(facebookService.saveNewToken(auth, code), HttpStatus.MOVED_PERMANENTLY);
  }

  @GetMapping("/target")
  public ResponseEntity<List<TargetDto>> getTarget (
    @AuthenticationPrincipal User auth,
    @RequestParam String index
  ) {
    return ResponseEntity.ok(facebookStatService.getWeeklyTarget(auth, index));
  }

  @GetMapping("/stat")
  public ResponseEntity<StatDto> getStat (
    @AuthenticationPrincipal User auth
  ) {
    return ResponseEntity.ok(facebookStatService.getStat(auth));
  }

  @GetMapping("/posts")
  public ResponseEntity<InstagramPostListDto> getPostList (
    @AuthenticationPrincipal User auth,
    @RequestParam(required = false, defaultValue = "12") String limit,
    @RequestParam(required = false, defaultValue = "") String after
  ) {
    return ResponseEntity.ok(facebookStatService.getPosts(auth, limit, after));
  }

  @GetMapping("/weekly-stat")
  public ResponseEntity<WeeklyStatDto> getWeeklyStat (
    @AuthenticationPrincipal User auth
  ) {
    return ResponseEntity.ok(facebookStatService.getWeeklyStat(auth));
  }

  @PostMapping("/post")
  public ResponseEntity<InstagramPost> getPost (
    @AuthenticationPrincipal User auth,
    @RequestBody InstagramPost post
  ) {
    return ResponseEntity.ok(facebookStatService.getPost(auth, post));
  }
}
