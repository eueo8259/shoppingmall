-- user 암호 1111
INSERT INTO category (category_code, category_name)
VALUES
('1', '전자제품'),
('2', '음식'),
('3', '가전제품');

INSERT INTO product (product_code, product_name, product_price, product_quantity, currency, product_register_date
, category_code, status, description, product_rate)
VALUES
('1', '컴퓨터', '7233', '50', 'USD', '2024-07-11', '1',"판매","컴퓨터 입니다", "4"),
('2', 'TV', '3', '40', 'EUR', '2024-07-11', '1',"판매","TV 입니다", "4"),
('3', '사과 2개', '3', '40', 'EUR', '2024-07-11', '2',"판매","사과 입니다", "4"),
('4', '달걀 1판', '3', '40', 'EUR', '2024-07-11', '2',"판매","달걀 입니다", "4"),
('5', '냉장고', '3', '40', 'EUR', '2024-07-11', '3',"판매","냉장고 입니다", "4"),
('6', '밥솥', '3', '40', 'EUR', '2024-07-11', '3',"판매","밥솥 입니다", "4"),
('7', '냉장고1', '3', '40', 'EUR', '2024-07-11', '3',"대기","냉장고 입니다", "4"),
('8', '냉장고2', '3', '40', 'EUR', '2024-07-11', '3',"대기","냉장고 입니다", "4"),
('9', '냉장고3', '3', '40', 'EUR', '2024-07-11', '3',"대기","냉장고 입니다", "4"),
('10', '냉장고4', '3', '40', 'EUR', '2024-07-11', '3',"대기","냉장고 입니다", "4"),
('11', '냉장고5', '3', '40', 'EUR', '2024-07-11', '3',"대기","냉장고 입니다", "4"),
('12', '냉장고6', '3', '40', 'EUR', '2024-07-11', '3',"대기","냉장고 입니다", "4"),
('999', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, "0");

INSERT INTO product_img(img_code, product_code, img_url)
VALUES
('1', '1', "/productImg/main68dd774f-d82b-4f6c-9fa7-a9b501fa33fa.jpg"),
('2', '1', "/productImg/subb78b2db2-6a01-4fc9-a97e-ff84e7ab71b3.jpg"),
('3', '2', "/productImg/main044a3515-350a-42f5-b5c5-5ca15ec6c1ca.jpg"),
('4', '2', "/productImg/sub2ada9c40-c416-44e0-8223-0a0a134ecb45.jpg");

INSERT INTO users (id, password, user_role) VALUES
('admin', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'ADMIN'),
('user1', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'USER'),
('user2', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'USER'),
('user3', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'USER'),
('seller', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'SELLER');

INSERT INTO user_info (user_info_code, user_name ,RRN, phone_number, created_date, email, birth_date, grade, id, current_point, is_active)
VALUES
('1', '가' ,'990708-0000000','010-1111-1111','2024-07-11', 'a@zzzz.com','1999-07-08','일반','admin', 0, 'Y'),
('2', '다' ,'990728-2222222','010-1111-3333','2024-07-13', 'c@zzzz.com','1999-07-28','일반','seller', 0, 'Y'),
('3', '나' ,'990718-1111111','010-1111-2222','2024-07-12', 'b@zzzz.com','1999-07-18','일반','user1', 5200, 'Y'),
('4', '다' ,'990628-2222221','010-1111-3333','2024-07-13', 'c@zzzz.com','1999-06-28','VIP','user2', 0, 'Y'),
('5', '다' ,'990528-2222223','010-1111-3333','2024-07-13', 'c@zzzz.com','1999-05-28','VIP','user3', 0, 'Y');

INSERT INTO delivery (user_info_code, delivery_code ,postal_code, address, contact_number, contact_name, default_yn)
VALUES
('3', '1' ,'03461','서울특별시 은평구 은평로 111', '010-1111-1111','가', 'Y'),
('4', '2' ,'11111','인천', '010-1111-2222','나', 'Y'),
('5', '3' ,'22222','부산', '010-1111-3333','다', 'Y'),
('3', '4' ,'10414','경기도 고양시 일산동구 중앙로 1193', '010-1111-1111','가', 'N');

INSERT INTO orders (order_date, order_code, user_info_code, payment_price, discount_amount,  delivery_code)
VALUES
('2024-08-01', '1', '4', '10000', '-500', '2'),
('2024-08-01', '2', '4', '11000', '-1000', '2'),
('2024-08-02', '3', '5', '14000', '-1000', '3'),
('2024-08-05', '4', '3', '45000', '-2000', '1'),
('2024-08-06', '5', '3', '160000', '-5000', '1');

INSERT INTO order_detail (order_num, order_code, product_code, order_quantity, order_price, order_status)
VALUES
('1', '1', '3', '1', '10500', "배송완료"),
('2', '2', '5', '1', '12000', "배송완료"),
('3', '3', '4', '1', '15000', "배송완료"),
('4', '4', '4', '1', '10000', "배송중"),
('5', '4', '5', '1', '20000', "배송중"),
('6', '4', '6', '2', '17000', "배송중"),
('7', '5', '5', '2', '165000', "배송완료");

INSERT INTO cart (product_code, user_info_code, quantity)
VALUES
('1', '3','1'),
('2', '4','2'),
('3', '5','3'),
('2', '3', '1'),
('1', '4', '1'),
('1', '5', '1');

INSERT INTO wish_list (product_code, user_info_code)
VALUES
('1','3'),
('2','3'),
('3','3'),
('1','4'),
('3','4'),
('4','4'),
('5','4'),
('1','5'),
('3','5'),
('6','5'),
('2','5');

INSERT INTO bulletin_board (board_title, product_code, content, user_info_code, views, has_comment)
VALUES
('주문 문제', 1, '컴퓨터 주문한 상품의 배송 상태가 궁금합니다. 언제 받을 수 있나요?', 1, 15, false),
('제품 및 재고', 2, 'TV 상품의 재고가 언제 다시 입고되나요?', 2, 22, true),
('반품 및 환불', 3, '사과 2개를 환불하고 싶습니다. 절차가 어떻게 되나요?', 3, 30, false),
('제품 및 재고', 4, '달걀 1판을 교환하고 싶습니다. 가능한가요?', 4, 18, true),
('내 계정 관리', 999, '냉장고 사용 설명서를 받을 수 있나요?', 5, 25, false),
('결제 및 프로모션', 6, '밥솥에 대한 할인 쿠폰을 적용하려면 어떻게 해야 하나요?', 1, 10, true),
('제품 및 재고', 1, '컴퓨터 제품의 상세 정보를 알고 싶습니다.', 2, 8, false),
('배송 및 배달', 2, 'TV의 배송지를 변경하고 싶습니다. 가능한가요?', 3, 12, true),
('제품 및 재고', 3, '사과 2개가 불량입니다. 어떻게 처리해야 하나요?', 4, 5, false),
('주문 문제', 4, '달걀 1판 주문을 취소하고 싶습니다. 절차가 어떻게 되나요?', 5, 20, true),
('제품 및 재고', 1, '컴퓨터 설치와 사용 방법이 궁금합니다.', 2, 14, false),
('반품 및 환불', 2, 'TV를 다른 모델로 교환할 수 있나요?', 3, 18, true),
('결제 및 프로모션', 3, '사과 주문 결제가 완료되었는지 확인 부탁드립니다.', 4, 22, false),
('배송 및 배달', 4, '달걀 배송이 지연되고 있습니다. 언제 받을 수 있을까요?', 5, 27, true),
('내 계정 관리', 5, '내 계정에 접근할 수 없습니다. 도움 부탁드립니다.', 1, 19, false),
('결제 및 프로모션', 6, '밥솥 프로모션 코드를 입력할 수 없어요. 어떻게 하나요?', 2, 13, true),
('반품 및 환불', 1, '구매한 컴퓨터의 모델을 변경하고 싶습니다.', 3, 11, false),
('배송 및 배달', 2, 'TV의 현재 배송 상태를 알고 싶습니다.', 4, 9, true),
('제품 및 재고', 3, '사과 제품이 손상되었습니다. 수리 가능한가요?', 5, 7, false),
('주문 문제', 4, '달걀 주문 내용을 변경하고 싶습니다. 어떻게 해야 하나요?', 1, 16, true);




INSERT INTO user_point (point_id, user_info_code, occur_date, charge_point, use_point, remarks)
VALUES
(1, 3 ,'2024-08-05 09:00:00', 100000, 0, '포인트충전'),
(2, 3 ,'2024-08-05 09:15:00', 0, 45000, '주문번호: 4'),
(3, 3 ,'2024-08-08 12:20:00', 200, 0, '리뷰이벤트적립금'),
(4, 3 ,'2024-08-06 11:20:00', 200000, 0, '포인트충전'),
(5, 3 ,'2024-08-06 11:30:00', 0, 160000, '주문번호: 5'),
(6, 3 ,'2024-08-10 15:20:00', 200, 0, '리뷰이벤트적립금');

INSERT INTO coupon (coupon_code, category_code, discount_rate, discount_amount)
VALUES
(1, 1 , 0.1, 0),
(2, 2 , 0, 2000),
(3, 3 , 0.15, 0),
(4, 1 , 0, 10000),
(5, 3 , 0, 5000);

INSERT INTO user_coupon (user_coupon_code, user_info_code, coupon_code)
VALUES
(1, 3, 1),
(2, 4, 2),
(3, 5, 3),
(4, 3, 1),
(5, 3, 2),
(6, 3, 3);