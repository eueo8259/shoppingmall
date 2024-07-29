package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.CategoryDto;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.service.CategoryService;
import com.example.shoppingMall.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class SellerController {
    private final ProductService productService;
    private final CategoryService categoryService;
    public SellerController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("product/insert")
    public String productInsert(Model model){
        List<CategoryDto> categoryDtoList = categoryService.findAll();
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("productDto", new ProductDto());
        return "product/insert";
    }

    @PostMapping("product/insert")
    public String insert(@ModelAttribute("productDto")ProductDto productDto,
                         @RequestParam("mainImage") MultipartFile mainImg,
                         @RequestParam("subImages")List<MultipartFile> subImg) throws IOException {
        productService.insertProduct(productDto, mainImg, subImg);
        return "redirect:/product/insert";
    }


}
