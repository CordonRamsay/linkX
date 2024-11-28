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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


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

            // 먼저 동의 여부 확인
            IPetition check = this.checkAgree(id, CUInfoDto.getLoginUser());
            if (check.getisSig()) {
                return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "동의", check);
            }else{
                // 청원 동의수 +1 / 동의테이블 행 추가
                this.petitionService.addagreeQty(id,user);
            }

            IPetition result = this.checkAgree(id, CUInfoDto.getLoginUser());


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

    private IPetition checkAgree(Long id, IUser loginUser) {
        IPetition result = this.petitionService.findById(id);


        // SingatureDto 생성
        SignatureDto signature = SignatureDto.builder()
                .petiId(id)
                .userId(loginUser.getId())
                .build();

        boolean agreeYn = this.petitionService.hasUserAgreed(signature);

        if (agreeYn) {
            result.setisSig(true);
        }else{
            result.setisSig(false);
        }
        return result;
    }

    @PostMapping("/updatestatuses")
    public ResponseEntity<String> updatePetitionStatuese(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PetitionDto petition = null;

        try{
            List<PetitionDto> petitions = petitionService.findAll();

            for(PetitionDto currentPetition : petitions){
                petition = currentPetition;
                LocalDate endDt = LocalDate.parse(petition.getEndDt(), formatter);
                boolean newPlayingStatus = !endDt.isBefore(today) && !endDt.isEqual(today);
                petitionService.updatePlaying(petition.getId(), newPlayingStatus);
            }

        }catch(DateTimeParseException e){
            System.out.println("날짜 형식 오류: " + (petition != null ? petition.getEndDt() : "알수 없는 날짜"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("날짜 형식 오류");
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }

        return ResponseEntity.ok("청원 상태 업데이트 완료");
     }
}
