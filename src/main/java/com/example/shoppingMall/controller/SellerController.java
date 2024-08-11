package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.*;
import com.example.shoppingMall.service.CategoryService;
import com.example.shoppingMall.service.OrderService;
import com.example.shoppingMall.service.ProductService;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("seller")
@Slf4j
public class SellerController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final OrderService orderService;
    public SellerController(ProductService productService, CategoryService categoryService, UserService userService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.orderService = orderService;
    }



    @GetMapping("insert")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String productInsert(Model model){

        List<CategoryDto> categoryDtoList = categoryService.findAll();
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("productDto", new ProductDto());
        return "seller/insert";
    }

    @PostMapping("insert")
    public String insert(@ModelAttribute("productDto")ProductDto productDto,
                         @RequestParam("mainImage") MultipartFile mainImg,
                         @RequestParam("subImages")List<MultipartFile> subImg) throws IOException {
        UserInfoDto userInfoDto = userService.loginUserInfoDto();
        productDto.setProductStatus("대기");
        productDto.setUserInfoCode(userInfoDto.getUserInfoCode());
        productService.insertProduct(productDto, mainImg, subImg);
        return "redirect:/seller/list";
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String productList(Model model) {
        UserInfoDto userInfoDto = userService.loginUserInfoDto();
        List<ProductDto> productDtoList = productService.findMyProductList(userInfoDto.getUserInfoCode());
        model.addAttribute("productList", productDtoList);
        return "seller/product_list";
    }

    @GetMapping("returnList")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String productReturnList(Model model){
        UserInfoDto userInfoDto = userService.loginUserInfoDto();
        Long userInfoCode = userInfoDto.getUserInfoCode();
        List<OrderDetailDto> orderDeatailDtoList = orderService.findReturnProductList(userInfoCode);
        log.info(orderDeatailDtoList.toString());
        model.addAttribute("orderDetail", orderDeatailDtoList);
        return "seller/returnList";
    }

    @PostMapping("return/{orderNum}")
    public String approveReturn(@PathVariable("orderNum") Long orderNum){
        orderService.productStatusChange(orderNum, "반품완료");
        userService.pointReturn(orderNum);
        productService.productReturn(orderNum);
        return "redirect:/seller/returnList";
    }

    @GetMapping("delivery")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String productDelivery(Model model){
        UserInfoDto userInfoDto = userService.loginUserInfoDto();
        Long userInfoCode = userInfoDto.getUserInfoCode();
        List<OrderDetailDto> orderDeatailDtoList = orderService.findDeliveryProductList(userInfoCode);
        log.info(orderDeatailDtoList.toString());
        model.addAttribute("orderDetail", orderDeatailDtoList);
        return "seller/deliveryList";
    }

    @PostMapping("delivery/{orderNum}")
    public String approveDelivery(@PathVariable("orderNum") Long orderNum){

        orderService.productStatusChange(orderNum, "배송중");
        return "redirect:/seller/returnList";
    }

    @GetMapping("sales")
    @PreAuthorize("hasRole('ROLE_SELLER') or hasRole('ROLE_ADMIN')")
    public String SalesStatus(Model model){
        UserInfoDto userInfoDto = userService.loginUserInfoDto();
        Long userInfoCode = userInfoDto.getUserInfoCode();
        List<Object[]> salesStatusProduct = productService.findsalesStatusProduct(userInfoCode);
        model.addAttribute("productSales", salesStatusProduct);
        return "seller/salesStatus";
    }






}
