package com.mjc.linkx.petition;


import com.mjc.linkx.boardfree.BoardFreeDto;
import com.mjc.linkx.boardfree.IBoardFree;
import com.mjc.linkx.boardfree.IBoardFreeService;
import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeService;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
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
@RequestMapping("/api/v1/petition")
public class PetitionRestController implements IResponseController {

    @Autowired
    private IPetitionService petitionService;



    @GetMapping("/sig/{id}")
    public ResponseEntity<ResponseDto> addagreeQty(HttpSession session, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            // 세션에서 User 가져옴
            IUser user = (IUser) session.getAttribute("LoginUser");
            CUInfoDto CUInfoDto = makeResponseCheckLogin(user);


                IPetition result = this.insertSig(id, CUInfoDto.getLoginUser());
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


    
    
    private IPetition insertSig(Long id, IUser loginUser) {
        IPetition result = this.petitionService.findById(id);
        Boolean TorN;
        TorN = this.petitionService.hasUserAgreed(id, loginUser.getId());
        // SingatureDto 생성
        SignatureDto signature = SignatureDto.builder()
                .petiId(id)
                .userId(loginUser.getId())
                .build();
        if(!TorN) {
            this.petitionService.addSignature(signature);
            this.petitionService.addagreeQty(id);
        }
        //테스트를 위해 값이 증가하는 메소드를 if밖으로 꺼냄
        //this.petitionService.addagreeQty(id);
        result.setisSig(TorN);

        return result;
    }
}
