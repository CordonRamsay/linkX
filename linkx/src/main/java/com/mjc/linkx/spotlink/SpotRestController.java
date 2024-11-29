package com.mjc.linkx.spotlink;

import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/spotlink/data")
public class SpotRestController {

    @Autowired
    private SpotService spotService;

    // 외부 API 호출 후 데이터 저장
    @GetMapping("")
    public String spot() {
        log.debug("Taste data request received.");
        return spotService.fetchAndSaveTasteData();  // 데이터 저장 로직을 서비스로 위임
    }

    // 스팟 리스트 가져오기
    @GetMapping("/list")
    public List<SpotDto> getTasteList() {
        return spotService.getSpotList();  // 데이터 조회 로직을 서비스로 위임
    }

    // 특정 스팟의 리뷰 리스트 가져오기
    @GetMapping("/reviews/{spotId}")
    public List<SpotReviewDto> getReviewsByRestaurant(@PathVariable Long spotId, HttpSession session) {
        return spotService.getReviewsBySpotId(spotId, session);
    }


    // 리뷰 추가 (로그인 사용자 정보 사용)
    @PostMapping("/reviews")
    public ResponseEntity<?> addReview(@RequestBody SpotReviewDto reviewDto, HttpSession session) {
        try {
            // 로그인 사용자 확인
            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            if (loginUser == null) {
                throw new LoginAccessException("로그인이 필요합니다.");
            }

            // 리뷰 데이터 설정
            reviewDto.setUserId(loginUser.getId());
            reviewDto.setReviewDate(LocalDateTime.now());
            reviewDto.setUserNickName(loginUser.getNickname());
            spotService.addReview(reviewDto);

            // 성공 응답
            return ResponseEntity.ok(Map.of("message", "리뷰 작성이 완료되었습니다."));
        } catch (LoginAccessException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            log.error("리뷰 작성 중 오류 발생: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "리뷰 작성 중 문제가 발생했습니다."));
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, HttpSession session) {
        try {
            // 세션에서 로그인한 사용자 확인
            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            if (loginUser == null) {
                throw new LoginAccessException("로그인이 필요합니다.");
            }

            // 리뷰 삭제
            spotService.deleteReview(reviewId, loginUser.getId());

            // 성공 응답
            return ResponseEntity.ok(Map.of("message", "리뷰가 성공적으로 삭제되었습니다."));
        } catch (LoginAccessException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            log.error("리뷰 삭제 중 오류 발생: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "리뷰 삭제 중 문제가 발생했습니다."));
        }
    }
    @GetMapping("/spot/{spotId}")
    public ResponseEntity<SpotDto> getSpotById(@PathVariable Long spotId) {
        SpotDto spot = spotService.getSpotById(spotId);
        return ResponseEntity.ok(spot);
    }
}
