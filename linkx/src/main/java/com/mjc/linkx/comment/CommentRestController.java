package com.mjc.linkx.comment;


import com.mjc.linkx.commentlike.CommentLikeDto;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.dto.ResponseCode;
import com.mjc.linkx.common.dto.ResponseDto;
import com.mjc.linkx.common.exception.IdNotFoundException;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentRestController implements IResponseController {
    private final CommentServiceImpl commentService;


    // 댓글 추가
    @PostMapping
    public ResponseEntity<ResponseDto> insert(@RequestBody CommentDto dto, HttpSession session) {
        try {
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }

            CUInfoDto cuInfoDto = makeResponseCheckLogin(session);
            IUser user = cuInfoDto.getLoginUser();

            IComment result = this.commentService.insert(user, dto);
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    // 댓글 가져오기
    @GetMapping("/board/{boardType}/{boardId}/comments")
    public ResponseEntity<ResponseDto> findAllComments(@PathVariable String boardType, @PathVariable Long boardId, HttpSession session) {
        try {
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session);
            IUser user = cuInfoDto.getLoginUser();

            if (boardType == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (boardId == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            SearchCommentDto dto = SearchCommentDto.builder()
                    .boardType(boardType)
                    .boardId(boardId)
                    .build();


            List<CommentDto> result = this.commentService.findAllByBoardTypeId(dto, user);

            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    // 댓글 상세정보 조회
    @GetMapping("/board/{boardType}/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> findCommentById(@PathVariable String boardType, @PathVariable Long boardId, @PathVariable Long id, HttpSession session) {
        try {
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session);
            IUser user = cuInfoDto.getLoginUser();

            if (boardType == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (boardId == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (id == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }

            CommentDto find = this.commentService.findByCommentId(id);
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", find);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    // 댓글 수정
    @PatchMapping("/board/{boardType}/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable String boardType,
                                                     @PathVariable Long boardId, @PathVariable Long id,
                                                     @RequestBody CommentDto dto, HttpSession session) {
        try {
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session);
            IUser user = cuInfoDto.getLoginUser();


            if (boardType == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (boardId == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (id == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }


            IComment result = this.commentService.update(dto);

            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/board/{boardType}/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable String boardType,
                                                     @PathVariable Long boardId, @PathVariable Long id,
                                                     HttpSession session) {
        try {
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session);
            IUser user = cuInfoDto.getLoginUser();


            if (boardType == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (boardId == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            if (id == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            this.commentService.delete(id);
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", id);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }
    @GetMapping("/board/{boardType}/{boardId}/comments/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @Validated @PathVariable Long id,HttpSession session) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            CUInfoDto cuInfoDto = makeResponseCheckLogin(session);
            IUser user = cuInfoDto.getLoginUser();

            this.commentService.addLikeQty(id,user);
            IComment result = this.getCommentAndLike(user, id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.toString(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.toString(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @GetMapping("/board/{boardType}/{boardId}/comments/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IUser loginUser = (IUser) model.getAttribute(SecurityConfig.LOGINUSER);
            this.commentService.subLikeQty(loginUser, id);
            IBoardComment result = this.getCommentAndLike(loginUser, id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }
    private IBoardComment getCommentAndLike(IUser loginUser, Long id) {
        IBoardComment result = this.commentService.findById(id);
        CommentLikeDto boardLikeDto = CommentLikeDto.builder()
                .createId(loginUser.getId())
                .commentId(id)
                .build();
        Integer likeCount = this.commentLikeService.countByCommentTableUserBoard(boardLikeDto);
        result.setUpdateDt(likeCount.toString());
        return result;
    }
}
