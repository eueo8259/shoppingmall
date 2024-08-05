package com.example.shoppingMall.controller;

import com.example.shoppingMall.configuration.PrincipalDetails;
import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.CategoryDto;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.service.CategoryService;
import com.example.shoppingMall.service.ProductService;
import com.example.shoppingMall.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("seller")
public class SellerController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    public SellerController(ProductService productService, CategoryService categoryService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
    }



    @GetMapping("insert")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String productInsert(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
            //일단 이렇게 처리하고 나중에 @Secured 어노테이션 사용해보자
            // 여기서 userDetails에서 사용자 정보 추출
            Users user = userDetails.getUsers();
            String id = user.getId();
            UserDto userDto = userService.getOneUser(id);

            if (userDto.getUserRole() == UserRole.SELLER){
                List<CategoryDto> categoryDtoList = categoryService.findAll();
                model.addAttribute("categoryDtoList", categoryDtoList);
                model.addAttribute("productDto", new ProductDto());
                return "seller/insert";
            }else {
                return "redirect:/";
            }
        } else {
            return "/user/login";
        }
    }

    @PostMapping("insert")
    public String insert(@ModelAttribute("productDto")ProductDto productDto,
                         @RequestParam("mainImage") MultipartFile mainImg,
                         @RequestParam("subImages")List<MultipartFile> subImg) throws IOException {
        UserInfoDto userInfoDto = userService.loginUserInfoDto();

        productDto.setUserInfoCode(userInfoDto.getUserInfoCode());
        productService.insertProduct(productDto, mainImg, subImg);
        return "redirect:/seller/list";
    }

    @GetMapping("list")
    public String productList(Model model) {
        UserInfoDto userInfoDto = userService.loginUserInfoDto();
        List<ProductDto> productDtoList = productService.findMyProductList(userInfoDto.getUserInfoCode());
        model.addAttribute("productList", productDtoList);
        return "seller/product_list";
    }


}
