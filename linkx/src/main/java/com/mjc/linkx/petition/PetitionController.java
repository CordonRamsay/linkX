package com.mjc.linkx.petition;


import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j

@RequestMapping("/petition")
public class PetitionController implements IResponseController {

    private final IPetitionService petitionService;
    private final UserService userService;

    @GetMapping("/petition_list")               //청원글 리스트 조회
    public String petitionList(@ModelAttribute("searchPetiDto") SearchPetiDto searchPetiDto, Model model, HttpSession session) {
        try{
            IUser loginUser =(IUser) session.getAttribute("LoginUser");
            if(loginUser != null) {
                model.addAttribute("nickname",loginUser.getNickname());
                model.addAttribute("major", loginUser.getMajorName());
            }
            // petiField 값이 검색 중에도 유지되도록 확인
            String petiField = searchPetiDto.getPetiField();
            if (petiField == null || petiField.trim().isEmpty()) {
                searchPetiDto.setPetiField(""); // 기본값 설정
            }
            // searchName 값이 검색 중에도 유지되도록(일단 위에랑 같이 해봄)
            String searchName = searchPetiDto.getSearchName();
            if (searchName == null || searchName.trim().isEmpty()) {
                searchPetiDto.setSearchName("");
            }

            //동의자 수 상위 5개 청원 가져오기
            List<PetitionDto> topAgreedPetitions = this.petitionService.findTopAgreedPetitions();
            model.addAttribute("topAgreedPetitions", topAgreedPetitions);

            Integer total = this.petitionService.countAllByContains(searchPetiDto);
            searchPetiDto.setTotal(total);
            List<PetitionDto> list = this.petitionService.findAllByNameContains(searchPetiDto);

            //D-Day값을 모델에 추가
            list.forEach(petition -> {
                Map<String, Object> remainingTime = petition.getRemainingTime();
                model.addAttribute("daysLeft_"+petition.getId(),remainingTime.get("daysLeft"));
                model.addAttribute("hoursLeft_"+petition.getId(),remainingTime.get("hoursLeft"));
                model.addAttribute("minutesLeft_"+petition.getId(),remainingTime.get("minutesLeft"));
                model.addAttribute("secondesLeft_"+petition.getId(),remainingTime.get("secondesLeft"));
            });



            model.addAttribute("petitionList",list);
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "petition/petition_list";
    }

    @GetMapping("/petition_add")            //청원글 등록 화면 return
    public String petitionAdd(HttpSession session, Model model) {
        try {
            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
            } else {
                throw new LoginAccessException("로그인이 필요합니다");
            }
        } catch (LoginAccessException ex) {
            return "redirect:/session-login/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "petition/petition_add";
    }

    @PostMapping("/petition_insert")        //청원 게시글 등록 후 목록화면 return
    public String petitionInsert(@ModelAttribute PetitionDto dto, Model model, HttpSession session){
        try{
            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
            } else {
                throw new LoginAccessException("로그인이 필요합니다");
            }
            UserDto user = this.userService.getLoginUserById(loginUser.getId());
            this.petitionService.insert(dto, user.getId());
        } catch (LoginAccessException ex) {
            return "redirect:/session-login/login";
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "redirect:petition_list?petiField=&searchName=";
    }

    // 청원 게시글 상세보기 화면 return / 해당 글의 객체 전달
    @GetMapping("/petition_view")
    public String petitionView(@RequestParam Long id, Model model, @SessionAttribute(name = "userId") Long userId){
        try{
            IUser user = this.userService.getLoginUserById(userId);
            IPetition find = this.petitionService.findById(id);

            //IPetition 타입의 find의 데이터를 petitionDto타입의 dto에 복사
            PetitionDto viewDto = PetitionDto.builder().build();
            viewDto.copyFields(find);

            //findById로 찾아온 PetitionDto 객체 뷰에 전달
            model.addAttribute("PetitionDto",viewDto);
            model.addAttribute("errorMessage","이미 동의 하셨습니다.");
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "petition/petition_view";
    }

    // 청원 게시글 수정화면 관련은 청원에는 해당 수정 기능이 없음으로 구현 X
    // 관리자의 의해 수정된다고 가정 시 구현 필요/현재는 모르겠음

    //청원글 삭제 후 list 화면으로 redirect
    @GetMapping("/petition_delete")
    public String petitionDelete(@RequestParam Long id){
        try{
            this.petitionService.delete(id);
        } catch(Exception ex){
            log.error(ex.toString());
        }
        return "redirect:petition_list?page=1&searchName=";
    }

//    @PostMapping("/petition_agree")
//    public String agreePetition(@RequestParam Long id, @SessionAttribute(name="userId") Long userId, Model model){
//        try{
//            if(!petitionService.hasUserAgreed(id, userId)){
//                SignatureDto signature = SignatureDto.builder()
//                        .petiId(id)
//                        .userId(userId)
//                        .build();
//                petitionService.addSignature(signature);
//                petitionService.addagreeQty(id);
//                model.addAttribute("status", "success");
//                model.addAttribute("message", "동의했습니다.");
//            } else{
//                model.addAttribute("status", "error");
//                model.addAttribute("message","이미 동의 하셨습니다.");
//            }
//        }catch(Exception ex){
//            log.error(ex.toString());
//            model.addAttribute("errorMessage", "동의 하는 중 오류가 발생했습니다.");
//        }
//        return "redirect:/petition/petition_view?id=" + id;
//    }

}
