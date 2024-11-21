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

@Controller
@RequiredArgsConstructor
@Slf4j

@RequestMapping("/petition")
public class PetitionController{

    private final IPetitionService petitionService;
    private final UserService userService;

    @GetMapping("/petition_list")               //청원글 리스트 조회
    public String petitionList(@ModelAttribute("searchPetiDto") SearchPetiDto searchPetiDto, Model model, HttpSession session) {
        try{
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

            Integer total = this.petitionService.countAllByNameContains(searchPetiDto);
            searchPetiDto.setTotal(total);
            List<PetitionDto> list = this.petitionService.findAllByNameContains(searchPetiDto);

            //D-Day값을 모델에 추가
            list.forEach(petition -> {
                long daysLeft = petition.getDaysLeft();
                model.addAttribute("daysLeft_" + petition.getId(), daysLeft);
            });

            IUser loginUser =(IUser) session.getAttribute("LoginUser");
            model.addAttribute("nickname",loginUser.getNickname());
            model.addAttribute("petitionList",list);
        }catch(LoginAccessException ex){
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "petition/petition_list";
    }

    @GetMapping("/petition_add")            //청원글 등록 화면 return
    public String petitionAdd(){
        try{

        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "petition/petition_add";
    }

    @PostMapping("/petition_insert")        //청원 게시글 등록 후 목록화면 return
    public String petitionInsert(@ModelAttribute PetitionDto dto, Model model, @SessionAttribute(name = "userId") Long userId){
        try{
            UserDto user = this.userService.getLoginUserById(userId);
            this.petitionService.insert(dto, user.getId());
        } catch (Exception ex){
            log.error(ex.toString());
        }
        return "redirect:petition_list?petiField=&searchName=";
    }

    // 청원 게시글 상세보기 화면 return / 해당 글의 객체 전달
    @GetMapping("/petition_view")
    public String petitionView(@RequestParam Long id, Model model, @SessionAttribute(name = "userId") Long userId){
        try{
            IUser user = this.userService.getLoginUserById(userId);
            //this.petitionService.addViewQty(id, user);        원래 이 항목은 조회수를 +1하는 항목이었지만 청원은 조회수 항목이 없어서 비활성화 / 추후 조회수가 필요하면 활성화할것
            IPetition find = this.petitionService.findById(id);

            //IPetition 타입의 find의 데이터를 petitionDto타입의 dto에 복사
            PetitionDto viewDto = PetitionDto.builder().build();
            viewDto.copyFields(find);

            //findById로 찾아온 PetitionDto 객체 뷰에 전달
            model.addAttribute("PetitionDto",viewDto);
            //findById로 찾아온 user객체 뷰에 전달
            model.addAttribute("User", user);
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
}
