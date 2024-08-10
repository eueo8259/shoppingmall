package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.service.ProductService;
import com.example.shoppingMall.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final ProductService productService;


    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("management")
    public String approve(Model model,
                          @PageableDefault(page = 0, size = 1, sort = "product_code",
                                  direction = Sort.Direction.DESC) Pageable pageable){
        Page<ProductDto> productPage = productService.getProductAllList(pageable);
        int totalPage = productPage.getTotalPages();
        List<Integer> barNumbers = productService.getPaginationBarNumbers(
                pageable.getPageNumber(), totalPage);
        model.addAttribute("pagination", barNumbers);
        model.addAttribute("paging", productPage);

        return "admin/Management";
    }

    @PostMapping("approve/{productCode}")
    public String productApprove(@PathVariable("productCode") Long productCode,
                              @RequestParam(value = "page", defaultValue = "0") int page) {
        productService.productApprove(productCode);
        return "redirect:/admin/management?page=" + page;
    }

    @PostMapping("delete/{productCode}")
    public String productDelete(@PathVariable("productCode") Long productCode,
                              @RequestParam(value = "page", defaultValue = "0") int page) {
        productService.productDelete(productCode);
        return "redirect:/admin/management?page=" + page;
    }




}
