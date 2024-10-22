package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.common.LoginAccessException;
import com.mjc.linkx.user.IUser;
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

@RequestMapping("/boardDept")
public class BoardDeptController {

    private final IBoardDeptService boardDeptService;
    private final UserService userService;

    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, Model model) {


        try {
            Integer total = this.boardDeptService.countAllByNameContains(searchBoardDto);
            searchBoardDto.setTotal(total);
            List<BoardDeptDto> list = this.boardDeptService.findAllByNameContains(searchBoardDto);


            model.addAttribute("boardList", list);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/login/login";
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
    public String boardView(@RequestParam Long id, Model model, @SessionAttribute(name = "userId") Long userId) {
        try {
            IUser user = this.userService.getLoginUserById(userId);
            this.boardDeptService.addViewQty(id, user);

            IBoardDept find = this.boardDeptService.findById(id);
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
        Long majorId = null;
        try {
            this.boardDeptService.delete(id);
            IBoardDept find = this.boardDeptService.findById(id);
            majorId = find.getMajorId();
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=&majorId="+majorId;
    }
}
