package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.BulletinBoardDto;
import com.example.shoppingMall.entity.BulletinBoard;
import com.example.shoppingMall.entity.Cart;
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
    public String boardDT(@PathVariable("id") Long boardCode, Model model){
        boardService.upView(boardCode);
        BulletinBoardDto bulletinBoardDto = boardService.findOne(boardCode);
        log.info(bulletinBoardDto.toString());
        model.addAttribute("board", bulletinBoardDto);
        return "board/detail";
    }
}
