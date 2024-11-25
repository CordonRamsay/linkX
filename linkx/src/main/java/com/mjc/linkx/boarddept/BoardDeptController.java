package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeService;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

@RequestMapping("/boardDept")
public class BoardDeptController implements IResponseController {

    private final IBoardDeptService boardDeptService;
    private final IBoardLikeService boardLikeService;

    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, Model model, HttpSession session) {
        try {

            // 로그인 유저가 있으면 Model에 닉네임과 학과 추가
            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
                model.addAttribute("major", loginUser.getMajorName());
            }


            Integer total = this.boardDeptService.countAllByNameContains(searchBoardDto);
            searchBoardDto.setTotal(total);
            List<BoardDeptDto> list = this.boardDeptService.findAllByNameContains(searchBoardDto);

            model.addAttribute("boardList", list);

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardDept_list";
    }

    // 게시글 등록 화면 return
    @GetMapping("/board_add")
    public String boardAdd(Model model, HttpSession session) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/board_add";
    }

    // 자유게시글 등록 후 목록화면 return
    @PostMapping("/board_insert")
    public String boardInsert(@ModelAttribute BoardDeptDto dto, Model model,  HttpSession session) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            this.boardDeptService.insert(dto, cuInfoDto.getLoginUser());

        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }  catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=&majorId=" + dto.getMajorId();
    }

    @GetMapping("/board_view/{id}")
    public String boardView(@PathVariable Long id, Model model, HttpSession session) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            // 조회수 증가
            this.boardDeptService.addViewQty(id, cuInfoDto.getLoginUser());
            // 게시글 조회
            IBoardDept find = this.boardDeptService.findById(id);

            //썸머노트로 인한 content HTML 태그 제거
            String plainTextContent = Jsoup.parse(find.getContent()).text();
            find.setContent(plainTextContent);

            // 게시글에 좋아요를 했는지 안했는지 체크 ( 페이지 로드 시 이미지를 선택하여 보여주기 위함 )
            BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                    .boardType(find.getBoardType())
                    .createId(cuInfoDto.getLoginUser().getId())
                    .boardId(id)
                    .build();

            Integer likeCount = this.boardLikeService.countByTypeAndIdAndUser(boardLikeDto);
            if (likeCount == 1) {
                find.setLikeYn(true);
            }else{
                find.setLikeYn(false);
            }

            BoardDeptDto viewDto = BoardDeptDto.builder().build();
            viewDto.copyFields(find);

            model.addAttribute("BoardDeptDto", find);
        }catch(LoginAccessException ex){
           return "redirect:/session-login/login";
        }
        catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardDept_view";
    }



    @GetMapping("/board_update/{id}")
    public String boardUpdate(HttpSession session,Model model,@PathVariable Long id) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            IBoardDept find = this.boardDeptService.findById(id);

            //썸머노트로 인한 content HTML 태그 제거
            String plainTextContent = Jsoup.parse(find.getContent()).text();
            find.setContent(plainTextContent);

            BoardDeptDto dto = BoardDeptDto.builder().build();
            dto.copyFields(find);

            // findById로 찾아온 BoardDeptDto 객체 뷰에 전달
            model.addAttribute("BoardDeptDto", dto);
        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }  catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardDept_update";
    }

    @PostMapping("/board_update")
    public String boardUpdate(HttpSession session,Model model,@ModelAttribute BoardDeptDto dto) {
        try {
            this.boardDeptService.update(dto);
        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }  catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_view/" +dto.getId();
    }
    // 해당 게시글 삭제 후 해당 학과 게시글 목록화면으로 이동
    @GetMapping("/board_delete/{id}")
    public String boardDelete(@PathVariable Long id) {
        // majorId 를 try - catch 문 바깥부분에 선언 - > return문에서도 사용하기 위함
        Long majorId = null;
        try {
            this.boardDeptService.delete(id);
            IBoardDept find = this.boardDeptService.findById(id);
            majorId = find.getMajorId();
        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }  catch (Exception ex) {
            log.error(ex.toString());
        }
        // 저장해놓은 majorId 값으로 해당 글 삭제후 원래 위치하고있었던 학과 게시판으로 이동
        if(majorId != null) {
            return "redirect:board_list?page=1&searchName=&majorId=" + majorId;
        }else{
            return "redirect:board_list?page=1&searchName=";
        }
    }
}
