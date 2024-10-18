package com.mjc.linkx.boardfree;



import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.common.LoginAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

@RequestMapping("/boardFree")
public class BoardFreeController {

    private final IBoardFreeService boardFreeService;

    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto")SearchBoardDto searchBoardDto, Model model){


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
        return "boardfree/board_list";
    }

    // 자유게시글 등록 화면 return
    @GetMapping("/board_add")
    public String boardAdd() {
        try {

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "boardfree/board_add";
    }

    // 자유게시글 등록 후 목록화면 return
    @PostMapping("/board_insert")
    public String boardInsert(@ModelAttribute BoardFreeDto dto, Model model) {
        try {
            this.boardFreeService.insert(dto,1L);

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list";
    }

}
