package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardfree.IBoardFree;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/boarddept")
public class BoardDeptRestController implements IResponseController {

    @Autowired
    private IBoardDeptService boardDeptService;

    @Autowired
    private IBoardLikeService boardLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(HttpSession session, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            // 세션에서 User 가져옴
            IUser user = (IUser) session.getAttribute("LoginUser");
            CUInfoDto CUInfoDto = makeResponseCheckLogin(user);


            // 좋아요 서비스 메소드 호출
            this.boardDeptService.addLikeQty(id,CUInfoDto.getLoginUser());
            // 좋아요 후 이미지를 바꿔주기 위해 update필드에 값을 받아옴
            IBoardDept result = this.getBoardAndLike(id, CUInfoDto.getLoginUser());
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
    public ResponseEntity<ResponseDto> subLikeQty(HttpSession session, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,"입력 매개변수 에러", null);
            }

            // 세션에서 User 가져옴
            IUser user = (IUser) session.getAttribute("LoginUser");
            CUInfoDto CUInfoDto = makeResponseCheckLogin(user);

            // 좋아요 취소 서비스 메소드 호출
            this.boardDeptService.subLikeQty(id,CUInfoDto.getLoginUser());
            // 좋아요 취소 후 이미지를 바꿔주기 위해 update필드에 값을 받아옴
            IBoardDept result = this.getBoardAndLike(id, CUInfoDto.getLoginUser());


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
    
    
    private IBoardDept getBoardAndLike(Long id, IUser loginUser) {
        IBoardDept result = this.boardDeptService.findById(id);
        
        // BoardLikeDto 생성
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .boardType(new BoardDeptDto().getBoardType())
                .createId(loginUser.getId())
                .boardId(id)
                .build();
        
        // 좋아요상태 -> 1 / 좋아요 x 상태 -> 0 리턴 하는 메소드 호출
        Integer likeCount = this.boardLikeService.countByTypeAndIdAndUser(boardLikeDto);
        
        // BoardFreeDto의 update 필드에  0 / 1 대입
        result.setUpdateDt(likeCount.toString());
        return result;
    }
}
