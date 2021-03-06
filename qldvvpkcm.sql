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
  `soluongtrongkho` int NOT NULL,
  `gianiemyet` decimal(10,0) NOT NULL,
  `ngaysanxuat` date NOT NULL,
  `ngayhethan` date NOT NULL,
  `hinhanh` varchar(255) NOT NULL,
  `tinhtrang` bool NOT NULL DEFAULT True,
  `loaihanghoa_id` int NULL,
  
  PRIMARY KEY (`hanghoa_id`),
  KEY `FK_HANGHOA_LOAIHANGHOA_idx` (`loaihanghoa_id`),
  CONSTRAINT `FK_HANGHOA_LOAIHANGHOA` FOREIGN KEY (`loaihanghoa_id`) REFERENCES `loaihanghoa` (`loaihanghoa_id`)
) ;

CREATE TABLE `nhacungcap_hanghoa` (
  `nhacungcap_id` int NOT NULL,
  `hanghoa_id` int NOT NULL,
  `soluong` int NOT NULL,
  `ngaynhap` datetime NOT NULL,
  `gianhap`  decimal(10,0) NOT NULL,
  `ghichu` varchar(255) NULL,
  
  PRIMARY KEY (`nhacungcap_id`, `hanghoa_id`, `ngaynhap`),
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
  `ngayTaoDH` datetime NOT NULL, #s????a
  `nhanvien_id` int NOT NULL,
  `khachhang_id` int NULL,
  
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
  `giamgia` double NOT NULL DEFAULT 0,
  
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
  `ghichu` longtext NULL,
  
  PRIMARY KEY (`donhang_id`, `nhanvien_id`, `ngaygiocapnhat`),
  KEY `FK_CAPNHATHOADON_DONHANG_idx` (`donhang_id`),
  CONSTRAINT `FK_CAPNHATHOADON_DONHANG` FOREIGN KEY (`donhang_id`) REFERENCES `donhang` (`donhang_id`),
  KEY `FK_CAPNHATHOADON_NHANVIEN_idx` (`nhanvien_id`),
  CONSTRAINT `FK_CAPNHATHOADON_NHANVIEN` FOREIGN KEY (`nhanvien_id`) REFERENCES `user` (`user_id`)
  ) ;
  
  INSERT INTO `loaihanghoa` (`tenloai`) VALUES ("Th????c ??n cho cho??"), ("Th????c ??n cho me??o");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("City Zoo","146D4 Nguy???n V??n H?????ng, Ph??????ng Th???o ??i???n, Qu???n 2","TP H???? Chi?? Minh","Vi????t Nam","sales@cityzoo.vn","0834502000");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Nutrience","366 L?? V??n S???, Ph?????ng 14, Qu???n 3","TP H???? Chi?? Minh", "Vi????t Nam","info@petpro.vn","0901636696");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("DOCA","L?? 19 ??.04, KCN Ch??u ?????c, X?? Ngh??a Th??nh, Huy????n Ch??u ?????c,","T???nh B?? R???a - V??ng T??u","Vi????t Nam","docavn79.com@gmail.com","02546299797");
  INSERT INTO `nhacungcap` (`tencongty`,`diachi`,`tinhthanh`,`quocgia`,`email`,`sodt`) VALUES ("Fusion Group","L?? L1-06B-07B-08B Khu du l???ch sinh th??i cao c???p An Kh??nh, x?? An Kh??nh, huy???n Ho??i ?????c","Ha?? N????i","Vi????t Nam","Info@fusiongroup.vn","0436367676");
  
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("TH???C ??N ?????T ROYAL CANIN MAXI ADULT","ROYAL CANIN", 20,408000,"2020-12-31","2022-12-31","/image/ThucAnUotRoyalCaninMaxiAdult.jpg",1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR JUNIOR PASTE - GEL DINH D?????NG CHO CH?? CON","beaphar",100,162000,"2021-03-20","2022-03-20", "/image/BeapharJuniorPaste.jpg",1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR MULTI VITAMIN TOP 10 - VITAMIN T???NG H???P CHO CH??","beaphar",18,236000,"2021-05-09","2022-05-09","/image/BeapharMultiVitaminTop10.gif",1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR DUO ACTIVE JUNIOR PASTE CAT - GEL DINH D?????NG CHO M??O CON","beaphar",39,162000,"2021-03-20","2022-03-20","/image/BeapharDuoActiveJuniorPasteCat.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("BEAPHAR LACTOL KITTY MILK - S???A CHO M??O CON","beaphar",125,502000,"2021-01-21","2022-01-21","/image/BeapharLactolKittyMilk.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ("ROYAL CANIN URINARY CARE WET - H??? TR??? S???C KH???E TI???T NI???U","ROYAL CANIN", 24,391000,"2021-02-17","2022-02-17","/image/RoyalCaninUrinaryCareWet.jpg",1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('NT Grain Free cao c???p cho Ch?? - G?? t??y, c?? tr??ch, tr???ng g?? v?? rau c??? qu??? t??? nhi??n','Nutrience',32,128000,'2021-01-12','2022-01-12','/image/NTGrainFreeCaoCapCho.png',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('NT Subzero cho Ch?? - Th???t b??, c?? h???i v?? rau c??? qu??? t??? nhi??n (Cho m???i gi???ng ch?? ??? m???i l???a tu???i)','Nutrience',19,160000,'2021-01-12','2022-01-12','/image/NTSubzeroCho.png',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('NT Original cho Ch?? con - Th???t g?? v?? rau c??? qu??? t??? nhi??n (D?????i 12 th??ng tu???i)','Nutrience',111,120000,'2021-01-12','2022-01-12','/image/NTOriginalChoCon.png',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('NT Original cho M??o con - Th???t g?? v?? rau c??? qu??? t??? nhi??n (D?????i 12 th??ng tu???i)','Nutrience',12,520000,'2021-01-12','2022-01-12','/image/NTOriginalMeoCon.png',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('NT Original cho M??o tr?????ng th??nh - Th???t g?? v?? rau c??? qu??? t??? nhi??n','Nutrience',10,900000,'2021-01-12','2022-01-12','/image/NTOriginalMeoTruongThanh.png',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('NT Subzero cho M??o - Th???t g??, c?? h???i, c?? tr??ch v?? rau c??? qu??? t??? nhi??n (Cho m???i gi???ng m??o ??? m???i l???a tu???i)','Nutrience',15,1400000,'2022-01-12','2021-01-12','/image/NTSubzeroMeo.png',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n Cho Ch?? M???i L???a Tu???i - Doca Dog 450gr','FRESH TRINO',122,45000,'2021-04-23','2022-04-23','/image/DocaDog450gr.jpg',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n Ch?? Tr?????ng Th??nh - Regular Dog 7 450gr','FRESH TRINO',113,40000,'2021-04-23','2022-04-23','/image/RegularDog7450gr.jpg',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n Ch?? Con - Alphatrino 450gr','FRESH TRINO',134,45900,'2021-04-23','2022-04-23','/image/AlphatrinoPuppy450gr.jpg',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n M??o Tr?????ng Th??nh - Brutrino 450gr','FRESH TRINO',117,43200,'2021-04-23 ','2022-04-23','/image/BrutrinoCat450gr.jpg',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n M??o Con - Neutrino Cat 450gr','FRESH TRINO',123,49400,'2021-04-23','2022-04-23','/image/NeutrinoCat450gr.jpg',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('S???a M??? Kh?? Cho M??o - Msbilac Gold Cat 330gr','Ch??u Tha??nh JSC',20,120000,'2021-04-23','2022-04-23','/image/MsbilacGoldCat330gr.jpg',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n h???t h???u c?? cho ch?? ANF 6 Free v??? c?? h???i 2kg','ANF',14,440000,'2021-02-11','2022-02-11','/image/ANF6FreeViCaHoi2KgCho .jpg',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('H???t Dinh D?????ng Kh??ng Ng?? C???c V??? V???t Nutriwell D??nh Cho Ch?? M???i L???a Tu???i 1.5kg','Jeil PetFood',12,310000,'2021-03-29','2022-03-29','/image/NutriWellGrainFreeViThit1.5KgCho.jpg',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('H???t Th???c ??n Ch???c N??ng T???t Cho S???c Kh???e ???????ng Ru???t Nature\'s Kitchen (d??nh Cho Ch?? M???i L???a Tu???i)','ANF',23,360000,'2021-02-11','2022-02-11','/image/Nature\'sKitchenDog.jpg',1,1);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('Th???c ??n cho m??o m???i l???a tu???i 5kg - Today\'s dinner','Farmsco Corporation',37,395000,'2021-04-19','2022-04-19','/image/Today\'sdinner5KgCat.png',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('B??nh th?????ng dinh d?????ng cho m??o Gozip v??? c?? ng???','Jeil PetFood',200,48000,'2021-03-29','2022-03-29','/image/GozipViCaNgu60gMeo.jpg',1,2);
  INSERT INTO `hanghoa` (`tenhanghoa`,`thuonghieu`,`soluongtrongkho`,`gianiemyet`,`ngaysanxuat`,`ngayhethan`,`hinhanh`,`tinhtrang`,`loaihanghoa_id`) VALUES ('H???t dinh d?????ng l?? t?????ng d??nh cho m??o nh??? Ideal Recipe Kitten 1kg','Jeil PetFood',42,275000,'2021-03-29','2022-03-29','/image/IdealRecipeCatKitten1Kg.jpg',1,2);


  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (1,1,22,"2021-01-02",326000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (1,2,100,"2021-03-22",326000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (1,3,18,"2021-05-11",188000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (1,4,41,"2021-03-22",130000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (1,5,125,"2021-01-23",331000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (1,6,24,"2021-02-19",312000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (2,7,32,"2021-01-14",102000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (2,8,20,"2021-01-14",128000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (2,9,111,"2021-01-14",80000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (2,10,12,"2021-01-14",401000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (2,11,10,"2021-01-14",684000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (2,12,15,"2021-01-14",1050000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (3,13,122,"2021-04-25",31500,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (3,14,123,"2021-04-25",28000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (3,15,134,"2021-04-25",32000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (3,16,117,"2021-04-25",30000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (3,17,123,"2021-04-25",34000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (3,18,20,"2021-04-25",92000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (4,19,15,"2021-04-25",334000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (4,20,12,"2021-04-25",239000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (4,21,23,"2021-04-25",274000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (4,22,37,"2021-04-25",300000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (4,23,200,"2021-04-25",34000,NULL);
  INSERT INTO `nhacungcap_hanghoa` (`nhacungcap_id`,`hanghoa_id`,`soluong`,`ngaynhap`,`gianhap`,`ghichu`) VALUES (4,24,45,"2021-04-25",209000,NULL);
  
  INSERT INTO `loaiuser` (`tenloai`) VALUES ("Qua??n ly?? tr??????ng"), ("Thu?? kho"), ("Nh??n vi??n");
  
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`loaiuser_id`) VALUES
  ("Tr????ng Phong","1990-01-01","Nam","0222334455","truongphong","1","2018-03-02","truongphong@gmail.com","Qu????n 7, TP.HCM","0793278239",1);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`loaiuser_id`) VALUES
  ("Pha??m Lu??n","1980-04-27","Nam","033445567788","phamluan","1","2018-03-02","phamluan@gmail.com","Go?? V????p, TP.HCM","0987654321",2);
  INSERT INTO `user` (`hoten`,`ngaysinh`,`gioitinh`,`cmnd`,`taikhoan`,`matkhau`,`ngayVaoLam`,`email`,`diachi`,`sdt`,`loaiuser_id`) VALUES
  ("Huy??nh Thi?? Thanh","1999-03-21","N????","0364687732","huynhthithanh","1","2018-03-02","huynhthithanh@gmail.com","Bi??nh D????ng","0382349726",3);
  
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`)
  VALUES ("NULL","0000-00-00","NULL", "NULL","NULL");
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Nguy????n Minh Vi????t","1983-06-28","Nam", "1111 ????????ng 3/2, Ph??????ng 12, Qu????n 11","0932478390",1999);
  INSERT INTO `khachhang` (`hoten`,`ngaysinh`,`gioitinh`,`diachi`,`sdt`,`diemtichluy`)
  VALUES ("Hoa??ng V??n Thu","1968-04-27","N????", "333 ????????ng Nguy????n Duy D????ng, Ph??????ng 4, Qu????n 10","0793247628",999);
  
  INSERT INTO `thucung` (`ten`,`ngaysinh`,`gioitinh`,`mauLong`,`tinhtrangsuckhoe`,`khachhang_id`)
  VALUES ("Ken","2021-01-01","??????c","Tr????ng","Kho??e ma??nh", 2);
  
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2018-03-02",3,2);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2019-04-20",3,3);
  INSERT INTO `donhang`(`ngayTaoDH`,`nhanvien_id`,`khachhang_id`) VALUES ("2020-05-20",3,2);
  
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (1, 1, 2, 408000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (1, 8, 1, 160000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (1, 19, 1, 440000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (2, 4, 2, 130000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (2, 24, 3, 275000, 0);
  INSERT INTO `chitietdonhang`(`donhang_id`,`hanghoa_id`,`soluong`,`dongia`,`giamgia`) VALUES (3, 14, 10, 40000, 0.1);
  
  
