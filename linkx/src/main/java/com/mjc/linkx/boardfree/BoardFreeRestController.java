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
    public ResponseEntity<ResponseDto> addLikeQty(HttpSession session, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            CUInfoDto CUInfoDto = makeResponseCheckLogin(loginUser);
            this.boardFreeService.addLikeQty(id,CUInfoDto.getLoginUser());
            IBoardFree result = this.getBoardAndLike(id, CUInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

//    @GetMapping("/unlike/{id}")
//    public ResponseEntity<ResponseDto> subLikeQty(Model model, @Validated @PathVariable Long id) {
//        try {
//            if (id == null || id <= 0) {
//                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
//            }
//            CUInfoDto CUInfoDto = makeResponseCheckLogin(model);
//            this.boardFreeService.subLikeQty(id,CUInfoDto.getLoginUser());
//            IBoardFree result = this.getBoardAndLike(id, CUInfoDto.getLoginUser());
//            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
//        } catch (LoginAccessException ex) {
//            log.error(ex.toString());
//            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
//        } catch (IdNotFoundException ex) {
//            log.error(ex.toString());
//            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
//        } catch (Exception ex) {
//            log.error(ex.toString());
//            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
//        }
//    }
    private IBoardFree getBoardAndLike(Long id, IUser loginUser) {
        IBoardFree result = this.boardFreeService.findById(id);
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .boardType(new BoardFreeDto().getBoardType())
                .createId(loginUser.getId())
                .boardId(id)
                .build();
        Integer likeCount = this.boardLikeService.countByTypeAndIdAndUser(boardLikeDto);
        result.setUpdateDt(likeCount.toString());
        return result;
    }
}
