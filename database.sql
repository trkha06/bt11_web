-- MySQL 8.0 - De so 02, MSSV 24162054
DROP DATABASE IF EXISTS bookstore_02;
CREATE DATABASE bookstore_02 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bookstore_02;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(50) NOT NULL UNIQUE, fullname VARCHAR(50), phone INT,
  passwd VARCHAR(32) NOT NULL, signup_date DATETIME DEFAULT CURRENT_TIMESTAMP, last_login DATETIME NULL, is_admin BIT NOT NULL DEFAULT b'0'
);
CREATE TABLE books (
  bookid INT AUTO_INCREMENT PRIMARY KEY, isbn INT, title VARCHAR(200), publisher VARCHAR(100), price DECIMAL(6,2),
  description TEXT, publish_date DATE, cover_image VARCHAR(100), quantity INT
);
CREATE TABLE author (author_id INT AUTO_INCREMENT PRIMARY KEY, author_name VARCHAR(100), date_of_birth DATE);
CREATE TABLE book_author (
  bookid INT NOT NULL, author_id INT NOT NULL, PRIMARY KEY(bookid, author_id),
  CONSTRAINT fk_ba_book FOREIGN KEY(bookid) REFERENCES books(bookid),
  CONSTRAINT fk_ba_author FOREIGN KEY(author_id) REFERENCES author(author_id)
);
CREATE TABLE rating (
  userid INT NOT NULL, bookid INT NOT NULL, rating TINYINT NOT NULL, review_text TEXT,
  PRIMARY KEY(userid, bookid), CONSTRAINT fk_rating_user FOREIGN KEY(userid) REFERENCES users(id),
  CONSTRAINT fk_rating_book FOREIGN KEY(bookid) REFERENCES books(bookid)
);

CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  recipient_name VARCHAR(100) NOT NULL,
  recipient_phone VARCHAR(20) NOT NULL,
  recipient_address VARCHAR(255) NOT NULL,
  note TEXT,
  total_amount DECIMAL(10,2) NOT NULL,
  payment_method VARCHAR(50) NOT NULL DEFAULT 'COD',
  payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
  order_status VARCHAR(50) NOT NULL DEFAULT 'Đơn hàng mới',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_orders_user FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE order_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  book_id INT NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  total_price DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_od_order FOREIGN KEY(order_id) REFERENCES orders(id) ON DELETE CASCADE,
  CONSTRAINT fk_od_book FOREIGN KEY(book_id) REFERENCES books(bookid)
);

-- MD5('admin123') and MD5('user123'), compatible with passwd VARCHAR(32).
INSERT INTO users(email,fullname,phone,passwd,is_admin,last_login) VALUES
('admin@bookstore.local','Administrator',912345678,MD5('admin123'),b'1',NOW()),
('user@bookstore.local','Nguyen Van A',934567890,MD5('user123'),b'0',NOW()),
('tran.b@bookstore.local','Tran Van B',987654321,MD5('user123'),b'0',NULL),
('le.c@bookstore.local','Le Thi C',901234567,MD5('user123'),b'0',NULL);
INSERT INTO author(author_name,date_of_birth) VALUES
('Nguyen Nhat Anh','1955-05-07'),('To Hoai','1920-09-27'),('J. K. Rowling','1965-07-31'),('Nam Cao','1915-10-29'),('Haruki Murakami','1949-01-12'),('Paulo Coelho','1947-08-24');
INSERT INTO books(isbn,title,publisher,price,description,publish_date,cover_image,quantity) VALUES
(1001,'Mat Biec','Tre',85.00,'Tieu thuyet tuoi hoc tro.','2019-01-01','https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=300&q=80',20),
(1002,'Cho Toi Xin Mot Ve Di Tuoi Tho','Tre',72.00,'Ky uc tuoi tho.','2018-06-01','https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=300&q=80',15),
(1003,'Toi Thay Hoa Vang Tren Co Xanh','Tre',90.00,'Cau chuyen lang que.','2017-03-20','https://images.unsplash.com/photo-1495446815901-a7297e633e8d?auto=format&fit=crop&w=300&q=80',12),
(1004,'De Men Phieu Luu Ky','Kim Dong',45.00,'Tac pham thieu nhi kinh dien.','2016-05-01','https://images.unsplash.com/photo-1543002588-bfa74002ed7e?auto=format&fit=crop&w=300&q=80',30),
(1005,'Harry Potter and the Philosopher Stone','Bloomsbury',120.00,'Phieu luu phep thuat.','1997-06-26','https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=300&q=80',18),
(1006,'Harry Potter and the Chamber of Secrets','Bloomsbury',125.00,'Nam hoc thu hai tai Hogwarts.','1998-07-02','https://images.unsplash.com/photo-1511108690759-009324a90311?auto=format&fit=crop&w=300&q=80',16),
(1007,'Chi Pheo','Van Hoc',55.00,'Truyen ngan hien thuc.','1941-01-01','https://images.unsplash.com/photo-1526243741027-444d633d7365?auto=format&fit=crop&w=300&q=80',10),
(1008,'Lao Hac','Van Hoc',50.00,'Tinh nguoi va pham gia.','1943-01-01','https://images.unsplash.com/photo-1521587760476-6c12a4b040da?auto=format&fit=crop&w=300&q=80',10),
(1009,'Norwegian Wood','Kodansha',110.00,'Tieu thuyet Nhat Ban.','1987-09-04','https://images.unsplash.com/photo-1507842217343-583bb7270b66?auto=format&fit=crop&w=300&q=80',14),
(1010,'Kafka on the Shore','Knopf',130.00,'Hanh trinh ky bi.','2002-09-12','https://images.unsplash.com/photo-1497633762265-9d179a990aa6?auto=format&fit=crop&w=300&q=80',11),
(1011,'The Alchemist','HarperOne',95.00,'Theo duoi uoc mo.','1988-01-01','https://images.unsplash.com/photo-1491841651911-c44c30c34548?auto=format&fit=crop&w=300&q=80',25),
(1012,'Brida','HarperOne',88.00,'Cau chuyen tam linh.','1990-01-01','https://images.unsplash.com/photo-1476275466078-4007374efbbe?auto=format&fit=crop&w=300&q=80',9),
(1013,'Co Gai Den Tu Hom Qua','Tre',75.00,'Truyen dai cho tuoi tre.','1989-01-01','https://images.unsplash.com/photo-1455885666463-2e08a7b55e84?auto=format&fit=crop&w=300&q=80',17),
(1014,'Canh Dong Bat Tan','Tre',69.00,'Tac pham van hoc Viet Nam.','2005-01-01','https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?auto=format&fit=crop&w=300&q=80',13),
(1015,'Truyen Kieu','Giao Duc',60.00,'Kiet tac Nguyen Du.','1820-01-01','https://images.unsplash.com/photo-1516979187457-637abb4f9353?auto=format&fit=crop&w=300&q=80',22);
INSERT INTO book_author VALUES (1,1),(2,1),(3,1),(13,1),(4,2),(5,3),(6,3),(7,4),(8,4),(9,5),(10,5),(11,6),(12,6),(14,2),(15,4);
INSERT INTO rating(userid,bookid,rating,review_text) VALUES
(2,1,5,'Sach rat hay va day cam xuc.'),(3,1,4,'Noi dung gan gui.'),(2,4,5,'Sach thieu nhi kinh dien.'),(4,5,5,'The gioi phep thuat tuyet voi.'),(3,9,4,'Van phong rat dac biet.'),(2,11,5,'Tao dong luc rat tot.');

-- Sample orders covering 8 statuses for testing filter functionality
INSERT INTO orders(id,user_id,recipient_name,recipient_phone,recipient_address,note,total_amount,payment_method,payment_status,order_status,created_at) VALUES
(1,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Giao gio hanh chinh',157.00,'COD','PENDING','Đơn hàng mới',DATE_SUB(NOW(), INTERVAL 7 DAY)),
(2,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Goi truoc khi giao',90.00,'COD','PENDING','Đã xác nhận',DATE_SUB(NOW(), INTERVAL 6 DAY)),
(3,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Dong goi can than',45.00,'COD','PENDING','Chuẩn bị hàng',DATE_SUB(NOW(), INTERVAL 5 DAY)),
(4,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Giao buoi sang',120.00,'COD','PENDING','Vận chuyển',DATE_SUB(NOW(), INTERVAL 4 DAY)),
(5,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Giao tang 3',125.00,'COD','PENDING','Giao hàng',DATE_SUB(NOW(), INTERVAL 3 DAY)),
(6,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Da nhan sach',105.00,'COD','PAID','Đã giao',DATE_SUB(NOW(), INTERVAL 2 DAY)),
(7,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Khach doi y',110.00,'COD','CANCELLED','Đơn hàng hủy',DATE_SUB(NOW(), INTERVAL 1 DAY)),
(8,2,'Nguyen Van A','0934567890','1 Vo Van Ngan, Thu Duc, TP.HCM','Sach loi bia',130.00,'COD','REFUNDED','Đơn hàng hoàn',NOW());

INSERT INTO order_details(order_id,book_id,quantity,unit_price,total_price) VALUES
(1,1,1,85.00,85.00),
(1,2,1,72.00,72.00),
(2,3,1,90.00,90.00),
(3,4,1,45.00,45.00),
(4,5,1,120.00,120.00),
(5,6,1,125.00,125.00),
(6,7,1,55.00,55.00),
(6,8,1,50.00,50.00),
(7,9,1,110.00,110.00),
(8,10,1,130.00,130.00);
