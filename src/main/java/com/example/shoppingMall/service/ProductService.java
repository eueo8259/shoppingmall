package com.example.shoppingMall.service;

import com.example.shoppingMall.api.CashedExRateProvider;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.ProductImg;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.ProductRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Data
public class ProductService {
    @Autowired
    EntityManager em;

    @Value("${file.dir}")
    private String fileDir;

    @Value("${file.url}")
    private String fileUrl;


    private final ProductRepository productRepository;
    private final CashedExRateProvider exRateProvider;

    public ProductService(ProductRepository productRepository, CashedExRateProvider exRateProvider) {
        this.productRepository = productRepository;
        this.exRateProvider = exRateProvider;
    }



    @Transactional
    public void insertProduct(ProductDto productDto, MultipartFile mainImg, List<MultipartFile> subImg) throws IOException {

        Product product = new Product();
        Category category = em.find(Category.class, productDto.getCategoryCode());
        UserInfo userInfo = em.find(UserInfo.class, productDto.getUserInfoCode());

        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setCurrency(productDto.getCurrency());
        product.setCategory(category);
        product.setProductRegisterDate(LocalDateTime.now());
        product.setUserInfo(userInfo);
        product.setDescription(productDto.getDescription());
        product.setImgList(new ArrayList<>());

        String mainUrl = imageSaveDirectory(mainImg, "main");
        List<String> subUrlList = imageSaveDirectoryList(subImg);
        subUrlList.add(mainUrl);

        for (String s : subUrlList){
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);
            productImg.setImgUrl(s);
            product.getImgList().add(productImg);
        }

        em.persist(product);
    }

    @Transactional
    public List<ProductDto> printProduct(String categoryName) {
        String sql = "SELECT p FROM Product p WHERE p.category.categoryName = :categoryName";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        query.setParameter("categoryName", categoryName); // Named parameter에 값 설정
        List<Product> productList = query.getResultList();

        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = productDtoFromEntity(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Transactional
    public void productUpdate(ProductDto productDto, MultipartFile mainImg, List<MultipartFile> subImg) throws IOException {
        Product product = em.find(Product.class, productDto.getProductCode());
        Category category = em.find(Category.class, productDto.getCategoryCode());

        product.setProductName(productDto.getProductName());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setProductPrice(productDto.getOriginalPrice());
        product.setCurrency(productDto.getCurrency());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setStatus(productDto.getProductStatus());

        if (!mainImg.isEmpty()){
            System.out.println("================================main is not empty======================");
            String newMainImgUrl = imageSaveDirectory(mainImg, "main");
            List<ProductImg> productImgList = findProductImgList(productDto.getProductCode(), "main");

            List<String> imgUrlList = productImgList.stream()
                    .map(ProductImg::getImgUrl)
                    .collect(Collectors.toList());

            for (String s : imgUrlList){
                deleteFile(s);
            }
            for (ProductImg p : productImgList){
                p.setImgUrl(newMainImgUrl);
            }
        }
        List<String> newSubImgUrlList = imageSaveDirectoryList(subImg);
        String flag = null;
        if (subImg.size() == 1){
            for (String s : newSubImgUrlList){
                flag = s;
            }
        }
        if (flag != "empty") {
            System.out.println("================================sub is not empty======================");
            List<ProductImg> productImgList = findProductImgList(productDto.getProductCode(), "sub");
            if (!productImgList.isEmpty()) {
                List<String> imgUrlList = productImgList.stream()
                        .map(ProductImg::getImgUrl)
                        .collect(Collectors.toList());

                for (String s : imgUrlList) {
                    deleteFile(s);
                }
                for (ProductImg p : productImgList) {
                    em.remove(p);
                }
            }
            for (String s : newSubImgUrlList) {
                ProductImg productImg = new ProductImg();
                productImg.setProduct(product);
                productImg.setImgUrl(s);
                product.getImgList().add(productImg);
            }
        }
        em.merge(product);
    }

    public String imageSaveDirectory(MultipartFile file, String fileName) throws IOException {

        if (file.isEmpty()) {
            return "empty";
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        int i = originalFilename.lastIndexOf('.');
        if (i >= 0) {
            extension = originalFilename.substring(i);
        }

        String newFilename = fileName + UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(fileDir, newFilename).normalize(); // 상대 경로 정규화

        // 디렉토리가 없으면 생성
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        Files.copy(file.getInputStream(), filePath);

        return fileUrl+ "/" + newFilename;
    }

    public List<String> imageSaveDirectoryList(List<MultipartFile> files) throws IOException {

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String filePath = imageSaveDirectory(file, "sub");
            filePaths.add(filePath);

        }

        return filePaths;
    }

    public void deleteFile(String relativeFilePath) throws IOException {

        String relativePath = relativeFilePath.replace("/productImg/", "");
        Path filePath = Paths.get(fileDir, relativePath).normalize();

        // 파일이 존재하는지 확인
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new FileNotFoundException("File not found: " + filePath.toAbsolutePath());
        }
    }

    public List<ProductImg> findProductImgList(Long productCode, String keyword){
        String sql = "SELECT p FROM ProductImg p WHERE p.product.productCode = :productCode AND p.imgUrl LIKE :keyword";
        TypedQuery<ProductImg> query = em.createQuery(sql, ProductImg.class);
        query.setParameter("productCode", productCode);
        query.setParameter("keyword", "%" + keyword + "%"); // `%`를 파라미터에서 조합
        return query.getResultList();
    }

    @Transactional
    public List<ProductDto> findMyProductList(Long userInfoCode) {
        List<ProductDto> productDtoList = new ArrayList<>();

        String sql = "SELECT p FROM Product p WHERE p.userInfo.userInfoCode = :userInfoCode";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        query.setParameter("userInfoCode", userInfoCode);
        List<Product> productList = query.getResultList();

        for (Product product : productList){
            ProductDto productDto = productDtoFromEntity(product);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
    private ProductDto productDtoFromEntity(Product product) {

        ProductDto productDto = new ProductDto();

        BigDecimal priceInCurrency = exRateProvider.getCachedExRate(product.getCurrency())
                .multiply(product.getProductPrice());
        BigDecimal roundedPrice = priceInCurrency.setScale(0, RoundingMode.HALF_UP);

        productDto.setProductCode(product.getProductCode());
        productDto.setProductName(product.getProductName());
        productDto.setProductPrice(roundedPrice);
        productDto.setOriginalPrice(product.getProductPrice());
        productDto.setCurrency(product.getCurrency());
        productDto.setProductRate(product.getProductRate());
        productDto.setProductQuantity(product.getProductQuantity());
        productDto.setProductStatus(product.getStatus()); //나중에 상태값에 따라서 출력되고 출력되지않도록 쿼리문 수정
        productDto.setDescription(product.getDescription());
        productDto.setCategoryName(product.getCategory().getCategoryName());
        productDto.setImgList(new ArrayList<>());
        for (ProductImg image : product.getImgList()) {
            if (image.getImgUrl().contains("main")) { // "main"이 포함된 이미지를 찾음
                productDto.setMainImg(image.getImgUrl());
                System.out.println(productDto.getMainImg());
            }else {
                productDto.getImgList().add(image.getImgUrl());
            }
        }
        return productDto;
    }

    @Transactional
    public List<Category> categoryList() {
        String sql = "SELECT c FROM Category c";
        TypedQuery<Category> query = em.createQuery(sql, Category.class);
        List<Category> categoryList = query.getResultList();
        return categoryList;
    }

    @Transactional
    public ProductDto findProductOne(Long productCode) {
        Product product = em.find(Product.class, productCode);
        ProductDto productDto = productDtoFromEntity(product);
        return productDto;
    }



//    public Page<ProductDto> getProductsAwaitingApproval(Pageable pageable) {
//        Page<Product> productPage = productRepository.getProductsAwaitingApproval(pageable);
//
//        List<ProductDto> productDtoList = productPage.getContent().stream()
//                .map(this::productDtoFromEntity)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
//
//    }

    public Page<ProductDto> getProductAllList(Pageable pageable) {
        Page<Product> productPage = productRepository.getProductsList(pageable);

        List<ProductDto> productDtoList = productPage.getContent().stream()
                .map(this::productDtoFromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
    }


    private static final int BAR_LENGTH=5;

    public List<Integer> getPaginationBarNumbers(int pageNumber, int totalPage) {
        int startNumber = Math.max(pageNumber-(BAR_LENGTH/2),0);

        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPage);

        return IntStream.range(startNumber,endNumber).boxed().toList();

    }
    @Transactional
    public void productApprove(Long productCode) {
        Product product = em.find(Product.class, productCode);
        product.setStatus("판매");
        em.merge(product);
    }
    @Transactional
    public void productDelete(Long productCode) {
        Product product = em.find(Product.class, productCode);
        em.remove(product);
    }
    public void buyProduct(Long productCode, int orderQuantity) {
        productRepository.updateQuantity(productCode, orderQuantity);
        Product product = productRepository.findById(productCode).orElse(null);
        if(product.getProductQuantity() == 0){
            product.setStatus("판매종료");
        }
    }
    public Product findOrderItem(Long orderItem) {
        return productRepository.findById(orderItem).orElse(null);
    }
}
