package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.CategoryDto;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.service.CategoryService;
import com.example.shoppingMall.service.ExchangeService;
import com.example.shoppingMall.service.ProductService;
import com.example.shoppingMall.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    private final ExchangeService exchangeService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    public ProductController(ExchangeService exchangeService, ProductService productService, CategoryService categoryService, UserService userService) {
        this.exchangeService = exchangeService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("test")
    public String test(Model model){
        List<Category> categoryList = productService.categoryList();
        model.addAttribute("categoryList", categoryList);
        return "product/test";
    }

    @GetMapping("product/list")
    public String productCategoryPage(@RequestParam("categoryName") String categoryName, Model model){
        List<ProductDto>productDtoList = productService.printProduct(categoryName);
        model.addAttribute("productDtoList", productDtoList);
        return "product/list_page";
    }

    @GetMapping("product/detail/{productCode}")
    public String productDetail(@PathVariable("productCode") Long productCode, Model model){
        ProductDto productDto = productService.findProductOne(productCode);
        model.addAttribute("productDto", productDto);
        return "product/detail";
    }

    @GetMapping("product/update/{productCode}")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String productUpdateView(@PathVariable("productCode") Long productCode, Model model){
        ProductDto productDto = productService.findProductOne(productCode);

        model.addAttribute("productDto", productDto);

        List<CategoryDto> categoryDtoList = categoryService.findAll();
        model.addAttribute("categoryDtoList", categoryDtoList);

        return "product/update";
    }

    @PostMapping("product/update")
    public String productUpdate(@ModelAttribute("productDto")ProductDto productDto,
                         @RequestParam("mainImage") MultipartFile mainImg,
                         @RequestParam("subImages")List<MultipartFile> subImg) throws IOException {
        System.out.println("mainImg size =========================="+ mainImg.getSize());
        System.out.println("subImg size =========================="+subImg.size());
        productService.productUpdate(productDto, mainImg, subImg);
        System.out.println("===================update메서드 종료========================");
        return "redirect:/seller/list";
    }






}
