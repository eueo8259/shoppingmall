package com.example.shoppingMall.controller;

import com.example.shoppingMall.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("")
    public String mainView(){
        return "board/main";
    }
}
