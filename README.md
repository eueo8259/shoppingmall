# 🛒 ShoppingMall
직구 쇼핑몰 사이트 구현

---

# 📆 프로젝트 기간
2024.07.08~2024.08.12

---

# 🧑‍🤝‍🧑 맴버구성
- 팀원 1 : 이정민 - 상품목록, 상품상세페이지, 반품 및 리뷰, 상품관리
- 팀원 2 : 하정인 - 장바구니, 위시리스트, 문의게시판 및 공지사항/이벤트, 쿠폰
- 팀원 3 : 이나래 - 회원가입, 로그인, 메인페이지, 주문/결제/할인, 마이페이지,배송지 관리, 포인트 관리, 회원관리


---


# 🛠 기술 스택
## _Frontend_
- HTML
- CSS
- JavaScript


## _Backend_
#### 1)_Language_
- JAVA 17
- JSP

#### 2)_FrameWork_
- Spring Boot

#### 3)_Server_
- Tomcat

#### 4)_DataBase_
- MySQL

  ----
- **JAVA 17**: 최신 기능과 성능 향상을 제공하는 프로그래밍 언어
- **Spring Boot**: 빠르고 간편한 설정으로 효율적인 개발을 지원
  - Spring Data JPA: 데이터베이스와의 효율적인 연동
  - Thymeleaf: 서버 사이드 템플릿 엔진
  - MySQL: 신뢰성과 성능이 뛰어난 데이터베이스
  - Lombok: 반복되는 코드를 줄여주는 개발 도구
  - Spring Boot DevTools: 개발 편의성을 위한 도구
  - Spring Security: 강력한 보안 기능 제공
  - Validation: 입력 데이터의 유효성 검증
  - Spring Web: 웹 애플리케이션 개발을 위한 필수 모듈


---



# 📌 주요 기능
### 로그인
- DB값 검증


### 회원가입
- DB 저장
- 유효성 검사
- ID 중복 체크
- 유효성 검사


### 마이 메뉴
- 회원정보 수정
- 금액 충전
- 배송지 관리
- 주문 내역 확인
- 회원 탈퇴(이 때 특정 값으로 변경해 탈퇴한 회원으로 처리하고 실제 DB에서 삭제처리를 하지는 않는다)


### 상품 주문
- 카테고리를 통해 상품 리스트로 이동
- 상품 이미지 선택 시 상품 상세페이지로 이동
- 장바구니 및 위시리스트 저장가능
- 구매시 구매페이지로 이동


### 구매 페이지
- 구매자 정보 출력 및 상품 정보 출
- 쿠폰 할인 적용
- 신규 배송지 등록
- 배송지 선택


### 주문 내역 페이지
- 배송확정 기능
- 리뷰 기능
- 반품 신청


### 판매자
- 상품 등록
- 상품 관리 및 수정
- 반품 관리
- 베송 관리
- 판매 현황 관리


  
### 관리자
- 고객 관리(등급 및 user/seller 역할 관)
- 물품 등록 승인 관리
- 이벤트 및 공지사항 등록


### 게시판
- 문의 및 답변 기능

---
# ERD
![shoppingmall ERD](https://github.com/user-attachments/assets/d73b48fe-a884-4fea-9b13-a7076675f52a)

---
# 화면구성
### 메인페이지
![메인페이지](https://github.com/user-attachments/assets/3f826653-a9f1-476b-b191-9fd42c50119c)

### 회원 가입 페이지
![회원가입 페이지](https://github.com/user-attachments/assets/2dded77c-b942-426f-8582-8158c932034b)

### 주문 결제 페이지
![주문결제 페이지](https://github.com/user-attachments/assets/b2346d4f-f262-4fa7-bd99-4086aabe1f7a)
![배송지 팝업](https://github.com/user-attachments/assets/750ce694-db3b-4e97-8ccb-5cab84acf520)

### 주문 내역 페이지
![주문내역 페이지](https://github.com/user-attachments/assets/14e9e843-c668-4b48-beef-2135cbf74f1c)

### 상품 등록 페이지
![상품등록 페이지](https://github.com/user-attachments/assets/9be2476e-4d10-4355-a7ba-163f8104d89d)

### 상품 관리 페이지
![상품관리 페이지](https://github.com/user-attachments/assets/1be4bdbf-0f73-4c4a-ae7b-8265b7f8ee87)

### 게시판
![게시판](https://github.com/user-attachments/assets/16812d8f-2077-456b-a2d8-c4988aae46f8)
![게시글 페이지](https://github.com/user-attachments/assets/bb8141b6-6ebb-4339-afee-b40ab6182db2)





