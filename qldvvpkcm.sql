CREATE DATABASE qldvvpkcm /*!40100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE qldvvpkcm;

CREATE TABLE `loaihanghoa` (
  `loaihanghoa_id` int NOT NULL AUTO_INCREMENT,
  `tenloai` varchar(255) NOT NULL,
  
  PRIMARY KEY (`loaihanghoa_id`)
) ;

CREATE TABLE `nhacungcap` (
  `nhacungcap_id` int NOT NULL AUTO_INCREMENT,
  `tencongty` varchar(255) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `tinhthanh` varchar(255) NOT NULL,
  `quocgia` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `sodt` varchar(11) NOT NULL,
  
  PRIMARY KEY (`nhacungcap_id`)
) ;

CREATE TABLE `hanghoa` (
  `hanghoa_id` int NOT NULL AUTO_INCREMENT,
  `tenhanghoa` varchar(255) NOT NULL,
  `thuonghieu` varchar(255) NOT NULL,
  `soluongtrongkho` decimal(10,0) NOT NULL,
  `gianhap` decimal(10,0) NOT NULL,
  `giaban` decimal(10,0) NOT NULL,
  `ngaysanxuat` datetime NOT NULL,
  `ngayhethan` datetime NOT NULL,
  `hinhanh` varchar(255) NOT NULL,
  `loaihanghoa_id` int NOT NULL,
  
  PRIMARY KEY (`hanghoa_id`),
  KEY `FK_HANGHOA_LOAIHANGHOA_idx` (`loaihanghoa_id`),
  CONSTRAINT `FK_HANGHOA_LOAIHANGHOA` FOREIGN KEY (`loaihanghoa_id`) REFERENCES `loaihanghoa` (`loaihanghoa_id`)
) ;

CREATE TABLE `nhacungcap_hanghoa` (
  `nhacungcap_id` int NOT NULL,
  `hanghoa_id` int NOT NULL,
  `ngaynhap` datetime NOT NULL, #mới
  `ghichu` varchar(255) NULL,
  
  PRIMARY KEY (`nhacungcap_id`, `hanghoa_id`),
  KEY `FK_NHACUNGCAP_HANGHOA_NHACUNGCAP_idx` (`nhacungcap_id`),
  KEY `FK_NHACUNGCAP_HANGHOA_HANGHOA_idx` (`hanghoa_id`),
  CONSTRAINT `FK_NHACUNGCAP_HANGHOA_NHACUNGCAP` FOREIGN KEY (`nhacungcap_id`) REFERENCES `nhacungcap` (`nhacungcap_id`),
  CONSTRAINT `FK_NHACUNGCAP_HANGHOA_HANGHOA` FOREIGN KEY (`hanghoa_id`) REFERENCES `hanghoa` (`hanghoa_id`)
) ;

CREATE TABLE `loaiuser` (
  `loaiuser_id` int NOT NULL AUTO_INCREMENT,
  `tenloai` varchar(255) NOT NULL,
  
  PRIMARY KEY (`loaiuser_id`)
) ;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `hoten` varchar(255) NOT NULL,
  `ngaysinh` date NOT NULL,
  `gioitinh` varchar(4) NOT NULL,
  `cmnd` varchar(12) NOT NULL,
  `taikhoan` varchar(20) NOT NULL,
  `matkhau` varchar(16) NOT NULL,
  `ngayVaoLam` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `sdt` varchar(11) NOT NULL,
  `loaiuser_id` int NOT NULL,
  
  PRIMARY KEY (`user_id`),
  KEY `FK_USER_LOAIUSER_idx` (`loaiuser_id`),
  CONSTRAINT `FK_USER_LOAIUSER` FOREIGN KEY (`loaiuser_id`) REFERENCES `loaiuser` (`loaiuser_id`)
) ;

CREATE TABLE `khachhang` (
  `khachhang_id` int NOT NULL AUTO_INCREMENT,
  `hoten` varchar(255) NOT NULL,
  `ngaysinh` date NOT NULL,
  `gioitinh` varchar(4) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `sdt` varchar(11) NOT NULL,
  `diemtichluy` decimal NOT NULL DEFAULT 0,
  
  PRIMARY KEY (`khachhang_id`)
  ) ;

CREATE TABLE `thucung` (
  `thucung_id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) NOT NULL,
  `ngaysinh` date NOT NULL,
  `gioitinh` varchar(4) NOT NULL,
  `mauLong` varchar(255) NOT NULL,
  `tinhtrangsuckhoe` varchar(255) NOT NULL,
  `khachhang_id` int NOT NULL,
  
  PRIMARY KEY (`thucung_id`),
  KEY `FK_THUCUNG_KHACHHANG_idx` (`khachhang_id`),
  CONSTRAINT `FK_THUCUNG_KHACHHANG` FOREIGN KEY (`khachhang_id`) REFERENCES `khachhang` (`khachhang_id`)
  ) ;

CREATE TABLE `donhang` (
  `donhang_id` int NOT NULL AUTO_INCREMENT,
  `ngayTaoDH` datetime NOT NULL, #sửa
  `giamgiatheodon` decimal(3,2) NOT NULL DEFAULT 0,
  `nhanvien_id` int NOT NULL,
  `khachhang_id` int NOT NULL,
  
  PRIMARY KEY (`donhang_id`),
  KEY `FK_DONHANG_NHANVIEN_idx` (`nhanvien_id`),
  CONSTRAINT `FK_DONHANG_NHANVIEN` FOREIGN KEY (`nhanvien_id`) REFERENCES `user` (`user_id`),
  KEY `FK_DONHANG_KHACHHANG_idx` (`khachhang_id`),
  CONSTRAINT `FK_DONHANG_KHACHHANG` FOREIGN KEY (`khachhang_id`) REFERENCES `khachhang` (`khachhang_id`)
  ) ;
  
  CREATE TABLE `chitietdonhang` (
  `donhang_id` int NOT NULL,
  `hanghoa_id` int NOT NULL,
  `soluong` int NOT NULL,
  `dongia` decimal(10,0) NOT NULL,
  `giamgiatheohang` decimal(3,2) NOT NULL DEFAULT 0,
  `ngaygiamgia` date NULL,
  
  PRIMARY KEY (`donhang_id`, `hanghoa_id`),
  KEY `FK_CHITIETDONHANG_DONHANG_idx` (`donhang_id`),
  CONSTRAINT `FK_CHITIETDONHANG_DONHANG` FOREIGN KEY (`donhang_id`) REFERENCES `donhang` (`donhang_id`),
  KEY `FK_CHITIETDONHANG_HANGHOA_idx` (`hanghoa_id`),
  CONSTRAINT `FK_CHITIETDONHANG_HANGHOA` FOREIGN KEY (`hanghoa_id`) REFERENCES `hanghoa` (`hanghoa_id`)
  ) ;
  
  CREATE TABLE `capnhathoadon` (
  `donhang_id` int NOT NULL,
  `nhanvien_id` int NOT NULL,
  `ngaygiocapnhat` datetime NOT NULL,
  
  PRIMARY KEY (`donhang_id`, `nhanvien_id`, `ngaygiocapnhat`),
  KEY `FK_CAPNHATHOADON_DONHANG_idx` (`donhang_id`),
  CONSTRAINT `FK_CAPNHATHOADON_DONHANG` FOREIGN KEY (`donhang_id`) REFERENCES `donhang` (`donhang_id`),
  KEY `FK_CAPNHATHOADON_NHANVIEN_idx` (`nhanvien_id`),
  CONSTRAINT `FK_CAPNHATHOADON_NHANVIEN` FOREIGN KEY (`nhanvien_id`) REFERENCES `user` (`user_id`)
  ) ;
  
  INSERT INTO `loaihanghoa` (`tenloai`) VALUES ("Thức ăn cho chó"), ("Thức ăn cho mèo");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("City Zoo","146D4 Nguyễn Văn Hưởng, Phường Thảo Điền, Quận 2","TP Hồ Chí Minh","Việt Nam","sales@cityzoo.vn","0834502000");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Nutrience","366 Lê Văn Sỹ, Phường 14, Quận 3","TP Hồ Chí Minh", "Việt Nam","info@petpro.vn","0901636696");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("DOCA","Lô 19 Đ.04, KCN Châu Đức, Xã Nghĩa Thành, Huyện Châu Đức,","Tỉnh Bà Rịa - Vũng Tàu","Việt Nam","docavn79.com@gmail.com","02546299797");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Fusion Group","Lô L1-06B-07B-08B Khu du lịch sinh thái cao cấp An Khánh, xã An Khánh, huyện Hoài Đức","Hà Nội","Việt Nam","Info@fusiongroup.vn","0436367676");
  
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ("THỨC ĂN ƯỚT ROYAL CANIN MAXI ADULT","ROYAL CANIN", 20,326000,408000,"2020-12-31","2022-12-31","/img/ThucAnUotRoyalCaninMaxiAdult.jpg",1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ("BEAPHAR JUNIOR PASTE - GEL DINH DƯỠNG CHO CHÓ CON","beaphar",100,113000,162000,"2021-03-20","2022-03-20", "/img/BeapharJuniorPaste.jpg",1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ("BEAPHAR MULTI VITAMIN TOP 10 - VITAMIN TỔNG HỢP CHO CHÓ","beaphar", 18,188000 ,236000,"2021-05-09","2022-05-09","/img/BeapharMultiVitaminTop10.gif",1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ("BEAPHAR DUO ACTIVE JUNIOR PASTE CAT - GEL DINH DƯỠNG CHO MÈO CON","beaphar",39, 130000,162000,"2021-03-20","2022-03-20","/img/BeapharDuoActiveJuniorPasteCat.jpg",2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ("BEAPHAR LACTOL KITTY MILK - SỮA CHO MÈO CON","beaphar",125,331000,502000,"2021-01-21","2022-01-21","/img/BeapharLactolKittyMilk.jpg",2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ("ROYAL CANIN URINARY CARE WET - HỖ TRỢ SỨC KHỎE TIẾT NIỆU","ROYAL CANIN", 24,312000,391000,"2021-02-17","2022-02-17","/img/RoyalCaninUrinaryCareWet.jpg",2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('NT Grain Free cao cấp cho Chó - Gà tây, cá trích, trứng gà và rau củ quả tự nhiên ','Nutrience',32,102000,128000,'2021-01-12 00:00:00','2022-01-12 00:00:00','/img/NTGrainFreeCaoCapCho.png',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('NT Subzero cho Chó - Thịt bò, cá hồi và rau củ quả tự nhiên (Cho mọi giống chó ở mọi lứa tuổi) ','Nutrience',19,128000,160000,'2021-01-12 00:00:00','2022-01-12 00:00:00','/img/NTSubzeroCho.png',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('NT Original cho Chó con - Thịt gà và rau củ quả tự nhiên (Dưới 12 tháng tuổi) ','Nutrience',111,80000,120000,'2021-01-12 00:00:00','2022-01-12 00:00:00','/img/NTOriginalChoCon.png',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('NT Original cho Mèo con - Thịt gà và rau củ quả tự nhiên (Dưới 12 tháng tuổi) ','Nutrience',12,401000,520000,'2021-01-12 00:00:00','2022-01-12 00:00:00','/img/NTOriginalMeoCon.png',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('NT Original cho Mèo trưởng thành - Thịt gà và rau củ quả tự nhiên ','Nutrience',10,684000,900000,'2021-01-12 00:00:00','2022-01-12 00:00:00','/img/NTOriginalMeoTruongThanh.png',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('NT Subzero cho Mèo - Thịt gà, cá hồi, cá trích và rau củ quả tự nhiên (Cho mọi giống mèo ở mọi lứa tuổi) ','Nutrience',15,1050000,1400000,'2022-01-12 00:00:00','2021-01-12 00:00:00','/img/NTSubzeroMeo.png',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức Ăn Cho Chó Mọi Lứa Tuổi - Doca Dog 450gr','FRESH TRINO',122,31500,45000,'2021-04-23 00:00:00','2022-04-23 00:00:00','/img/DocaDog450gr.jpg',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức Ăn Chó Trưởng Thành - Regular Dog 7 450gr','FRESH TRINO',113,28000,40000,'2021-04-23 00:00:00','2022-04-23 00:00:00','/img/RegularDog7450gr.jpg',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức Ăn Chó Con - Alphatrino 450gr','FRESH TRINO',134,32000,45900,'2021-04-23 00:00:00','2022-04-23 00:00:00','/img/AlphatrinoPuppy450gr.jpg',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức Ăn Mèo Trưởng Thành - Brutrino 450gr','FRESH TRINO',117,30000,43200,'2021-04-23 00:00:00','2022-04-23 00:00:00','/img/BrutrinoCat450gr.jpg',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức Ăn Mèo Con - Neutrino Cat 450gr','FRESH TRINO',123,34000,49400,'2021-04-23 00:00:00','2022-04-23 00:00:00','/img/NeutrinoCat450gr.jpg',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Sữa Mẹ Khô Cho Mèo - Msbilac Gold Cat 330gr','Châu Thành JSC',20,92000,120000,'2021-04-23 00:00:00','2022-04-23 00:00:00','/img/MsbilacGoldCat330gr.jpg',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức ăn hạt hữu cơ cho chó ANF 6 Free vị cá hồi 2kg ','ANF',14,334000,440000,'2021-02-11 00:00:00','2022-02-11 00:00:00','/img/ANF6FreeViCaHoi2KgCho .jpg',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Hạt Dinh Dưỡng Không Ngũ Cốc Vị Vịt Nutriwell Dành Cho Chó Mọi Lứa Tuổi 1.5kg','Jeil PetFood',12,239000,310000,'2021-03-29 00:00:00','2022-03-29 00:00:00','/img/NutriWellGrainFreeViThit1.5KgCho.jpg',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Hạt Thức ăn Chức Năng Tốt Cho Sức Khỏe đường Ruột Nature\'s Kitchen (dành Cho Chó Mọi Lứa Tuổi)','ANF',23,274000,360000,'2021-02-11 00:00:00','2022-02-11 00:00:00','/img/Nature\'sKitchenDog.jpg',1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Thức ăn cho mèo mọi lứa tuổi 5kg - Today\'s dinner ','Farmsco Corporation',37,300000,395000,'2021-04-19 00:00:00','2022-04-19 00:00:00','/img/Today\'sdinner5KgCat.png',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Bánh thưởng dinh dưỡng cho mèo Gozip vị cá ngừ ','Jeil PetFood',200,34000,48000,'2021-03-29 00:00:00','2022-03-29 00:00:00','/img/GozipViCaNgu60gMeo.jpg',2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianhap`,`giaban`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`loaihanghoa_id`) VALUES ('Hạt dinh dưỡng lý tưởng dành cho mèo nhỏ Ideal Recipe Kitten 1kg ','Jeil PetFood',42,209000,275000,'2021-03-29 00:00:00','2022-03-29 00:00:00','/img/IdealRecipeCatKitten1Kg.jpg',2);


  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (1,1,"2021-01-02",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (1,2,"2021-03-22",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (1,3,"2021-05-11",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (1,4,"2021-03-22",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (1,5,"2021-01-23",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (1,6,"2021-02-19",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (2,7,"2021-01-14",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (2,8,"2021-01-14",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (2,9,"2021-01-14",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (2,10,"2021-01-14",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (2,11,"2021-01-14",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (2,12,"2021-01-14",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (3,13,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (3,14,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (3,15,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (3,16,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (3,17,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (3,18,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (4,19,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (4,20,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (4,21,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (4,22,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (4,23,"2021-04-25",NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`ngaynhap`,`ghichu`) VALUES (4,24,"2021-04-25",NULL);
  
  INSERT INTO `loaiuser` (`tenloai`) VALUES ("Quản lý trưởng"), ("Thủ kho"), ("Nhân viên");
  
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`loaiuser_id`) VALUES
  ("Trương Phong","1990-01-01","Nam","0222334455","truongphong","1","2018-03-02","truongphong@gmail.com","Quận 7, TP.HCM","0793278239",1);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`loaiuser_id`) VALUES
  ("Phạm Luân","1980-04-27","Nam","033445567788","phamluan","1","2018-03-02","phamluan@gmail.com","Gò Vấp, TP.HCM","0987654321",2);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`loaiuser_id`) VALUES
  ("Huỳnh Thị Thanh","1999-03-21","Nữ","0364687732","huynhthithanh","1","2018-03-02","huynhthithanh@gmail.com","Bình Dương","0382349726",3);
  
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Nguyễn Minh Việt","1983-06-28","Nam", "1111 đường 3/2, Phường 12, Quận 11","0932478390",1999);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Hoàng Văn Thu","1968-04-27","Nữ", "333 đường Nguyễn Duy Dương, Phường 4, Quận 10","0793247628",999);
  
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Ken","2021-01-01","Đực","Trắng","Khỏe mạnh", 1);
  
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`,`giamgiatheodon`) VALUES ("2018-03-02",3,1,0);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`,`giamgiatheodon`) VALUES ("2019-04-20",3,2,0);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`,`giamgiatheodon`) VALUES ("2020-05-20",3,1,0);
  
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgiatheohang`,`ngaygiamgia`) VALUES (1, 1, 2, 408000, 0, NULL);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgiatheohang`,`ngaygiamgia`) VALUES (1, 8, 1, 160000, 0, NULL);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgiatheohang`,`ngaygiamgia`) VALUES (1, 19, 1, 440000, 0, NULL);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgiatheohang`,`ngaygiamgia`) VALUES (2, 4, 2, 130000, 0, NULL);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgiatheohang`,`ngaygiamgia`) VALUES (2, 24, 3, 275000, 0, NULL);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgiatheohang`,`ngaygiamgia`) VALUES (3, 14, 10, 40000, 0.1, NULL);
  
  
