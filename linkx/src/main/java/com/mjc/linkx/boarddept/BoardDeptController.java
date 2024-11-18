package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeService;
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
public class BoardDeptController {

    private final IBoardDeptService boardDeptService;
    private final IBoardLikeService boardLikeService;

    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, Model model, HttpSession session) {


        try {
            Integer total = this.boardDeptService.countAllByNameContains(searchBoardDto);
            searchBoardDto.setTotal(total);
            List<BoardDeptDto> list = this.boardDeptService.findAllByNameContains(searchBoardDto);


            IUser loginUser = (IUser) session.getAttribute("LoginUser");
            // 로그인이 되어있으면 nickname을 화면으로 보내고, 안 되어있으면 로그인 페이지로 리다이렉트
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
            }else{
                throw new LoginAccessException("로그인을 해주세요");
            }
            model.addAttribute("boardList", list);

        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardDept_list";
    }

    // 게시글 등록 화면 return
    @GetMapping("/board_add")
    public String boardAdd() {
        try {

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/board_add";
    }

    // 자유게시글 등록 후 목록화면 return
    @PostMapping("/board_insert")
    public String boardInsert(@ModelAttribute BoardDeptDto dto, Model model, @SessionAttribute(name = "userId") Long userId) {
        try {

            this.boardDeptService.insert(dto, userId);

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=&majorId=" + dto.getMajorId();
    }

    @GetMapping("/board_view")
    public String boardView(@RequestParam Long id, Model model, HttpSession session) {
        try {
            //유저 가져오기
            IUser loginUser = (IUser)session.getAttribute("LoginUser");
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
            }else{
                throw new LoginAccessException("로그인 필요");
            }

            // 조회수 증가
            this.boardDeptService.addViewQty(id, loginUser);
            // 게시글 조회
            IBoardDept find = this.boardDeptService.findById(id);

            //썸머노트로 인한 content HTML 태그 제거
            String plainTextContent = Jsoup.parse(find.getContent()).text();
            find.setContent(plainTextContent);

            // 게시글에 좋아요를 했는지 안했는지 체크 ( 페이지 로드 시 이미지를 선택하여 보여주기 위함 )
            BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                    .boardType(find.getBoardType())
                    .createId(loginUser.getId())
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
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardDept_view";
    }

    @GetMapping("/board_delete")
    public String boardDelete(@RequestParam Long id) {
        // majorId 를 try - catch 문 바깥부분에 선언 - > return문에서도 사용하기 위함
        Long majorId = null;
        try {
            this.boardDeptService.delete(id);
            IBoardDept find = this.boardDeptService.findById(id);
            majorId = find.getMajorId();
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        // 저장해놓은 majorId 값으로 해당 글 삭제후 원래 위치하고있었던 학과 게시판으로 이동
        if(majorId != null) {
            return "redirect:board_list?page=1&searchName=&majorId=" + majorId;
        }else{
            return "redirect:board_list?page=1&searchName=";
        }
    }

    @GetMapping("/board_update")
    public String boardUpdate(@RequestParam Long id, Model model) {
        try {
            IBoardDept find = this.boardDeptService.findById(id);
            BoardDeptDto dto = BoardDeptDto.builder().build();
            dto.copyFields(find);
            model.addAttribute("BoardDeptDto", dto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardDept_update";
    }

    @PostMapping("/board_update")
    public String boardUpdate(@ModelAttribute BoardDeptDto dto) {
        try {
            IBoardDept update = this.boardDeptService.update(dto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_view?id="+dto.getId();
    }
}
