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
('6', '밥솥', '3', '40', 'EUR', '2024-07-11', '3',"판매","밥솥 입니다", "4");

--INSERT INTO product_img(img_code, product_code, img_url)
--VALUES
--('1', '1', "/productImg/main68dd774f-d82b-4f6c-9fa7-a9b501fa33fa.jpg"),
--('2', '1', "/productImg/subb78b2db2-6a01-4fc9-a97e-ff84e7ab71b3.jpg"),
--('3', '2', "/productImg/main044a3515-350a-42f5-b5c5-5ca15ec6c1ca.jpg"),
--('4', '2', "/productImg/sub2ada9c40-c416-44e0-8223-0a0a134ecb45.jpg");

-- user 암호 1111
INSERT INTO users (id, password, user_role) VALUES
('admin', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'ADMIN'),
('user1', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'USER'),
('user2', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'USER'),
('user3', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'USER'),
('seller', '$2a$10$gmwpP9rsFw25ilwcam.4s.eVil26pFMDZkbUlcAGcaxVW/CqNd2cS', 'SELLER');

INSERT INTO user_info (user_info_code, user_name ,RRN, phone_number, created_date, email,birth_date, grade, id , is_active)
VALUES
('1', '가' ,'990708-0000000','010-1111-1111','2024-07-11', 'a@zzzz.com','1999-07-08','일반','admin','Y'),
('2', '다' ,'990728-2222222','010-1111-3333','2024-07-13', 'c@zzzz.com','1999-07-28','일반','seller','Y'),
('3', '나' ,'990718-1111111','010-1111-2222','2024-07-12', 'b@zzzz.com','1999-07-18','일반','user1','Y'),
('4', '다' ,'990628-2222221','010-1111-3333','2024-07-13', 'c@zzzz.com','1999-06-28','VIP','user2','Y'),
('5', '다' ,'990528-2222223','010-1111-3333','2024-07-13', 'c@zzzz.com','1999-05-28','VIP','user3','Y');

INSERT INTO delivery (user_info_code, delivery_code ,postal_code, address, contact_number, contact_name)
VALUES
('3', '1' ,'000000','서울', '010-1111-1111','가'),
('4', '2' ,'111111','인천', '010-1111-2222','나'),
('5', '3' ,'222222','부산', '010-1111-3333','다');

INSERT INTO orders (order_code, product_code, user_info_code, order_quantity, order_status, delivery_code)
VALUES
('1', '1' ,'3', '4','배송중', '1'),
('2', '2' ,'4', '5','준비중', '2'),
('3', '3' ,'5', '6','배송완료', '3');

INSERT INTO cart (cart_code, product_code, id, quantity)
VALUES
('1', '1', 'user1','1'),
('2', '2', 'user2','2'),
('3', '3', 'user3','3'),
('4', '2', 'user1', '1'),
('5', '1', 'user2', '1'),
('6', '1', 'user3', '1');




