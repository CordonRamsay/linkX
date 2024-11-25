package com.mjc.linkx.boardfree;


import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeService;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.dto.ResponseCode;
import com.mjc.linkx.common.dto.ResponseDto;
import com.mjc.linkx.common.exception.IdNotFoundException;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/boardfree")
public class BoardFreeRestController implements IResponseController {

    @Autowired
    private IBoardFreeService boardFreeService;

    @Autowired
    private IBoardLikeService boardLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(HttpSession session,Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            // 세션에서 User 가져옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            // 좋아요 서비스 메소드 호출
            this.boardFreeService.addLikeQty(id,cuInfoDto.getLoginUser());
            // 좋아요 후 이미지를 바꿔주기 위해 likeYn 값을 받아옴
            IBoardFree result = this.getBoardAndLike(id, cuInfoDto.getLoginUser());

            return makeResponseEntity(HttpStatus.OK.value(),HttpStatus.OK,"성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(HttpSession session,Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }

            // 세션에서 User 가져옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            // 좋아요 취소 서비스 메소드 호출
            this.boardFreeService.subLikeQty(id,cuInfoDto.getLoginUser());
            // 좋아요 취소 후 이미지를 바꿔주기 위해 likeYn 값을 받아옴
            IBoardFree result = this.getBoardAndLike(id, cuInfoDto.getLoginUser());


            return makeResponseEntity(HttpStatus.OK.value(),HttpStatus.OK, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.OK.value(),HttpStatus.FORBIDDEN,  ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }
    
    
    private IBoardFree getBoardAndLike(Long id, IUser loginUser) {
        IBoardFree result = this.boardFreeService.findById(id);
        
        // BoardLikeDto 생성
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .boardType(new BoardFreeDto().getBoardType())
                .createId(loginUser.getId())
                .boardId(id)
                .build();
        
        // 좋아요상태 -> 1 / 좋아요 x 상태 -> 0 리턴 하는 메소드 호출
        Integer likeCount = this.boardLikeService.countByTypeAndIdAndUser(boardLikeDto);
        
        // BoardFreeDto의 likeYn 필드에  0 / 1 대입
        if (likeCount == 1) {
            result.setLikeYn(true);
        } else {
            result.setLikeYn(false);
        }
        return result;
    }
}
