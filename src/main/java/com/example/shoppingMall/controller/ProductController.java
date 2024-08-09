package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.CategoryDto;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.service.CategoryService;
import com.example.shoppingMall.service.ExchangeService;
import com.example.shoppingMall.service.ProductService;
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

    public ProductController(ExchangeService exchangeService, ProductService productService, CategoryService categoryService) {
        this.exchangeService = exchangeService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("test")
    public String test(Model model){
        List<Category> categoryList = productService.test();
        model.addAttribute("categoryList", categoryList);
        return "product/test";
    }

    @GetMapping("product/list")
    public String productCategoryPage(@RequestParam("categoryName") String categoryName, Model model){
        List<ProductDto>productDtoList = productService.printProduct(categoryName);
        model.addAttribute("productDtoList", productDtoList);
        return "product/listPage";
    }

    @GetMapping("product/detail/{productCode}")
    public String productDetail(@PathVariable("productCode") Long productCode, Model model){
        ProductDto productDto = productService.findProductOne(productCode);
        model.addAttribute("productDto", productDto);
        return "product/detail";
    }









}
