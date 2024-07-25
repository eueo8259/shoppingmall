package com.example.shoppingMall.service;

import com.example.shoppingMall.api.CashedExRateProvider;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.ProductImg;
import com.example.shoppingMall.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        String mainUrl = imageSaveDirectory(mainImg, "main");
        List<String> subUrlList = imageSaveDirectoryList(subImg);
        subUrlList.add(mainUrl);

        Product product = new Product();
        Category category = em.find(Category.class, productDto.getCategoryCode());
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setCurrency(productDto.getCurrency());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImgList(new ArrayList<>());

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
    public ProductDto findProductOne(Long productCode) {
        Product product = em.find(Product.class, productCode);
        ProductDto productDto = productDtoFromEntity(product);
        return productDto;
    }



    public String imageSaveDirectory(MultipartFile file, String imgName) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot store empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        int i = originalFilename.lastIndexOf('.');
        if (i >= 0) {
            extension = originalFilename.substring(i);
        }

        String newFilename = imgName + UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(fileDir, newFilename).normalize(); // 상대 경로 정규화

        // 디렉토리가 없으면 생성
        if (Files.notExists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        Files.copy(file.getInputStream(), filePath);

        // 저장된 파일의 경로를 로그에 출력
//        System.out.println("File saved to: " + filePath.toAbsolutePath());

        // 상대 경로를 웹 경로로 변환하여 반환
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

    private ProductDto productDtoFromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductCode(product.getProductCode());
        productDto.setProductName(product.getProductName());

        BigDecimal priceInCurrency = exRateProvider.getCachedExRate(product.getCurrency())
                .multiply(product.getProductPrice());
        BigDecimal roundedPrice = priceInCurrency.setScale(0, RoundingMode.HALF_UP);

        productDto.setProductPrice(roundedPrice);
        productDto.setProductRate(product.getProductRate());
        productDto.setProductQuantity(product.getProductQuantity());
        productDto.setProductStatus(product.getStatus()); //나중에 상태값에 따라서 출력되고 출력되지않도록 쿼리문 수정
        productDto.setDescription(product.getDescription());
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
        public List<Category> test() {
            String sql = "SELECT c FROM Category c";
            TypedQuery<Category> query = em.createQuery(sql, Category.class);
            List<Category> categoryList = query.getResultList();
            return categoryList;
        }

}
