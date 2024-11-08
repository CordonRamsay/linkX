package com.mjc.linkx.boardfree;



import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeService;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserDto;
import com.mjc.linkx.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

@RequestMapping("/boardFree")
public class BoardFreeController {

    private final IBoardFreeService boardFreeService;
    private final UserService userService;
    private final IBoardLikeService boardLikeService;

    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, Model model) {


        try {
            Integer total = this.boardFreeService.countAllByNameContains(searchBoardDto);
            searchBoardDto.setTotal(total);
            List<BoardFreeDto> list = this.boardFreeService.findAllByNameContains(searchBoardDto);


            model.addAttribute("boardList", list);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/login/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardFree_list";
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
    public String boardInsert(@ModelAttribute BoardFreeDto dto, Model model, @SessionAttribute(name = "userId") Long userId) {
        try {

            UserDto user = this.userService.getLoginUserById(userId);
            this.boardFreeService.insert(dto, user.getId());

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=";
    }

    // 자유게시글 상세보기 화면 return / 해당 글의 객체 전달
    @GetMapping("/board_view")
    public String boardView(@RequestParam Long id, Model model, @SessionAttribute(name = "userId") Long userId) {
        try {
            IUser user = this.userService.getLoginUserById(userId);
            this.boardFreeService.addViewQty(id, user);
            IBoardFree find = this.boardFreeService.findById(id);

            // 좋아요 개수 조회 후 updateDt값 넣기
            BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                    .boardType(new BoardFreeDto().getBoardType())
                    .createId(userId)
                    .boardId(id)
                    .build();
            Integer likeCount = this.boardLikeService.countByTypeAndIdAndUser(boardLikeDto);
            find.setUpdateDt(likeCount.toString());

            // IBoardFree 타입인 find의 데이터를 BoardFreeDto 타입의 viewDto에 복사
            BoardFreeDto viewDto = BoardFreeDto.builder().build();
            viewDto.copyFields(find);

            // findById로 찾아온 BoardFreeDto 객체 뷰에 전달
            model.addAttribute("BoardFreeDto", viewDto);
            // findById로 찾아온 User 객체 뷰에 전달
            model.addAttribute("User", user);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardFree_view";
    }

    // 자유게시글 수정 화면 return / 해당 글의 객체 전달
    @GetMapping("/board_update")
    public String boardModify(@RequestParam Long id, Model model) {
        try {
            IBoardFree find = this.boardFreeService.findById(id);

            // IBoardFree 타입인 find의 데이터를 BoardFreeDto 타입의 dto에 복사
            BoardFreeDto updateDto = BoardFreeDto.builder().build();
            updateDto.copyFields(find);

            // findById로 찾아온 BoardFreeDto 객체 뷰에 전달
            model.addAttribute("BoardFreeDto", updateDto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardFree_update";
    }

    // 자유게시글 수정 후 다시 view 화면으로 redirect
    @PostMapping("/board_update")
    public String boardUpdate(@ModelAttribute BoardFreeDto dto) {
        try {
            this.boardFreeService.update(dto);

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_view?id=" + dto.getId();
    }

    // 해당 게시글 삭제 후 list 화면으로 redirect
    @GetMapping("/board_delete")
    public String boardDelete(@RequestParam Long id) {
        try {
            this.boardFreeService.delete(id);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=";
    }
}
