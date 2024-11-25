package com.mjc.linkx.boardfree;



import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeService;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserDto;
import com.mjc.linkx.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

@RequestMapping("/boardFree")
public class BoardFreeController implements IResponseController {

    private final IBoardFreeService boardFreeService;
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

            Integer total = this.boardFreeService.countAllByNameContains(searchBoardDto);
            searchBoardDto.setTotal(total);
            List<BoardFreeDto> list = this.boardFreeService.findAllByNameContains(searchBoardDto);

            model.addAttribute("boardList", list);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardFree_list";
    }

    // 게시글 등록 화면 return
    @GetMapping("/board_add")
    public String boardAdd(Model model, HttpSession session) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            makeResponseCheckLogin(session, model);

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
    public String boardInsert(@ModelAttribute BoardFreeDto dto, Model model, HttpSession session) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);
            this.boardFreeService.insert(dto, cuInfoDto.getLoginUser());

        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_list?page=1&searchName=";
    }

    // 자유게시글 상세보기 화면 return / 해당 글의 객체 전달
    @GetMapping("/board_view/{id}")
    public String boardView(@PathVariable Long id, Model model,HttpSession session) {
        try {
            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            // 조회수 증가
            this.boardFreeService.addViewQty(id, cuInfoDto.getLoginUser());
            // 게시글 조회
            IBoardFree find = this.boardFreeService.findById(id);

            // 게시글에 좋아요를 했는지 안했는지 체크 ( 페이지 로드 시 이미지를 선택하여 보여주기 위함)
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

            // Dto 형변환
            BoardFreeDto viewDto = BoardFreeDto.builder().build();
            viewDto.copyFields(find);
            viewDto.setBoardType(viewDto.getBoardType());

            // Model에 데이터 추가
            model.addAttribute("BoardFreeDto", viewDto);

        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardFree_view";
    }

    // 자유게시글 수정 화면 return / 해당 글의 객체 전달
    @GetMapping("/board_update/{id}")
    public String boardUpdate(@PathVariable Long id, Model model,HttpSession session) {
        try {

            // 세션에서 로그인 정보 갖고 옴
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session, model);

            IBoardFree find = this.boardFreeService.findById(id);

            // IBoardFree 타입인 find의 데이터를 BoardFreeDto 타입의 dto에 복사
            BoardFreeDto updateDto = BoardFreeDto.builder().build();
            updateDto.copyFields(find);

            // findById로 찾아온 BoardFreeDto 객체 뷰에 전달
            model.addAttribute("BoardFreeDto", updateDto);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardFree_update";
    }

    // 자유게시글 수정 후 다시 view 화면으로 redirect
    @PostMapping("/board_update")
    public String boardUpdate(@ModelAttribute BoardFreeDto dto, Model model,HttpSession session) {
        try {
            this.boardFreeService.update(dto);

        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }  catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:board_view/" + dto.getId();
    }

    // 해당 게시글 삭제 후 list 화면으로 redirect
    @GetMapping("/board_delete/{id}")
    public String boardDelete(@PathVariable Long id, Model model,HttpSession session) {
        try {

            this.boardFreeService.delete(id);
        }catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/session-login/login";
        }  catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/boardFree/board_list?page=1&searchName=";
    }
}
