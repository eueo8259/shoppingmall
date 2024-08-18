package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.BulletinBoardDto;
import com.example.shoppingMall.dto.CommentDto;
import com.example.shoppingMall.entity.BulletinBoard;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Comment;
import com.example.shoppingMall.service.BoardService;
import com.example.shoppingMall.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public String mainView(Model model,
                           @PageableDefault(page = 0, size = 10, sort = "boardCode",
                                   direction = Sort.Direction.DESC) Pageable pageable){
        Page<BulletinBoard> boardPage = boardService.findAll(pageable);

        int totalPage = boardPage.getTotalPages();
        List<Integer> barNumbers = boardService.getPaginationBarNumbers(
                pageable.getPageNumber(), totalPage);
        model.addAttribute("pagination", barNumbers);
        model.addAttribute("paging", boardPage);

        List<String> boardTitle = boardService.boardTitle();
        model.addAttribute("boardTitle", boardTitle);
        return "board/main";
    }

    @GetMapping("insert")
    public String insertBoard(Principal principal,Model model){
        if(principal != null) {
            String user = principal.getName();
            model.addAttribute("user", user);
            List<Map<String, String>> productName = productService.getName();
            model.addAttribute("product",productName);
            return "board/insert";
        }
        return "user/login";
    }

    @PostMapping("insert")
    public String insertBoard(@ModelAttribute("BoardDto") BulletinBoardDto bulletinBoardDto,
                              Principal principal){
        String user = principal.getName();
        boardService.insert(bulletinBoardDto, user);
        return "redirect:/board";
    }

    @GetMapping("details/{id}")
    public String boardDT(@PathVariable("id") Long boardCode, Model model,Principal principal){
        if(principal != null) {
            //조회수
            boardService.upView(boardCode);
            // 관리자 확인
            String admin = principal.getName();
            model.addAttribute("admin", admin);
            // 문의정보
            BulletinBoardDto bulletinBoardDto = boardService.findOne(boardCode);
            model.addAttribute("board", bulletinBoardDto);
            if (bulletinBoardDto.isHasComment()){
                // 답글정보
                Comment comment = boardService.findOneComment(boardCode);
                model.addAttribute("comment",comment);
                log.info(comment.toString());
            }

            return "board/detail";
        }
        return "user/login";
    }

    @PostMapping("comment")
    public String Comment(CommentDto comment, RedirectAttributes redirectAttributes,
                          @RequestParam("admin") String admin){
        // has_comment true로 변경
        boardService.commentOK(comment.getBoardCode().getBoardCode());
        // Comment 테이블 추가
        boardService.AddComment(comment,admin);
        redirectAttributes.addFlashAttribute("message", "댓글이 성공적으로 작성되었습니다.");
        return "redirect:/board";
    }

}
