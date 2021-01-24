CREATE TABLE `jc_country_struct` (
  `area_id` varchar(12) NOT NULL,
  `area_name` varchar(200) NOT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `area_id_UNIQUE` (`area_id`),
  UNIQUE KEY `area_name_UNIQUE` (`area_name`)
); 

CREATE TABLE `jc_passport_office` (
  `office_id` int NOT NULL,
  `office_name` varchar(200) NOT NULL,
  `office_area_id` varchar(12) NOT NULL,
  PRIMARY KEY (`office_id`),
  UNIQUE KEY `office_id_UNIQUE` (`office_id`),
  UNIQUE KEY `office_name_UNIQUE` (`office_name`),
  KEY `fk_office_area_id_idx` (`office_area_id`),
  CONSTRAINT `fk_office_area_id` FOREIGN KEY (`office_area_id`) REFERENCES `jc_country_struct` (`area_id`) ON DELETE RESTRICT
);

CREATE TABLE `jc_register_office` (
  `r_office_id` int NOT NULL,
  `r_office_name` varchar(200) NOT NULL,
  `r_area_id` varchar(12) NOT NULL,
  PRIMARY KEY (`r_office_id`),
  UNIQUE KEY `office_id_UNIQUE` (`r_office_id`),
  UNIQUE KEY `office_name_UNIQUE` (`r_office_name`),
  KEY `fk_office_area_id_idx` (`r_area_id`),
  CONSTRAINT `fk_r_office_area_id` FOREIGN KEY (`r_area_id`) REFERENCES `jc_country_struct` (`area_id`) ON DELETE RESTRICT
);

CREATE TABLE `jc_street` (
  `street_code` int NOT NULL,
  `street_name` varchar(300) NOT NULL,
  PRIMARY KEY (`street_code`),
  UNIQUE KEY `street_code_UNIQUE` (`street_code`),
  UNIQUE KEY `street_name_UNIQUE` (`street_name`)
);

CREATE TABLE `jc_student_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `h_sur_name` varchar(100) NOT NULL,
  `h_given_name` varchar(100) NOT NULL,
  `h_patronymic` varchar(100) NOT NULL,
  `h_birth_date` date NOT NULL,
  `h_post_index` varchar(10) NOT NULL,
  `h_street_code` int NOT NULL,
  `h_building` varchar(10) NOT NULL,
  `h_extension` varchar(10) DEFAULT NULL,
  `h_apartment` varchar(10) DEFAULT NULL,
  `w_sur_name` varchar(100) NOT NULL,
  `w_given_name` varchar(100) NOT NULL,
  `w_patrnymic` varchar(100) NOT NULL,
  `w_birth_date` date NOT NULL,
  `w_post_index` varchar(10) NOT NULL,
  `w_street_code` int NOT NULL,
  `w_building` varchar(10) NOT NULL,
  `w_extension` varchar(10) DEFAULT NULL,
  `w_apartment` varchar(10) DEFAULT NULL,
  `certificate_id` varchar(20) NOT NULL,
  `register_office_id` int NOT NULL,
  `marriage_date` date NOT NULL,
  `h_passport_seria` varchar(10) NOT NULL,
  `h_passport_number` varchar(10) NOT NULL,
  `h_passport_date` date NOT NULL,
  `h_passport_office_id` int NOT NULL,
  `w_passport_seria` varchar(10) NOT NULL,
  `w_passport_number` varchar(10) NOT NULL,
  `w_passport_date` date NOT NULL,
  `w_passport_office_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_h_street_code_idx` (`h_street_code`),
  KEY `fk_w_street_code_idx` (`w_street_code`),
  KEY `fk_register_office_id_idx` (`register_office_id`),
  KEY `fk_h_passport_office_id_idx` (`h_passport_office_id`),
  KEY `fk_w_passport_office_id_idx` (`w_passport_office_id`),
  CONSTRAINT `fk_h_passport_office_id` FOREIGN KEY (`h_passport_office_id`) REFERENCES `jc_passport_office` (`office_id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_h_street_code` FOREIGN KEY (`h_street_code`) REFERENCES `jc_street` (`street_code`) ON DELETE RESTRICT,
  CONSTRAINT `fk_register_office_id` FOREIGN KEY (`register_office_id`) REFERENCES `jc_register_office` (`r_office_id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_w_passport_office_id` FOREIGN KEY (`w_passport_office_id`) REFERENCES `jc_passport_office` (`office_id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_w_street_code` FOREIGN KEY (`w_street_code`) REFERENCES `jc_street` (`street_code`) ON DELETE RESTRICT
);

CREATE TABLE `st_address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `post_code` varchar(6) NOT NULL,
  `street` varchar(256) NOT NULL,
  `building` varchar(10) DEFAULT NULL,
  `extension` varchar(10) DEFAULT NULL,
  `apartment` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  UNIQUE KEY `id_UNIQUE` (`address_id`)
);

CREATE TABLE `jc_student_child` (
  `student_child_id` int NOT NULL AUTO_INCREMENT,
  `student_order_id` int NOT NULL,
  `c_sur_name` varchar(100) NOT NULL,
  `c_given_name` varchar(100) NOT NULL,
  `c_patronymic` varchar(100) NOT NULL,
  `c_birth_date` date NOT NULL,
  `c_certificate_number` varchar(20) NOT NULL,
  `c_registration_date` date NOT NULL,
  `c_register_office_id` int NOT NULL,
  `c_post_index` varchar(10) NOT NULL,
  `c_street_code` int NOT NULL,
  `c_building` varchar(10) NOT NULL,
  `c_extension` varchar(10) DEFAULT NULL,
  `c_apartment` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`student_child_id`),
  UNIQUE KEY `id_UNIQUE` (`student_child_id`),
  KEY `fk_student_order_id_idx` (`student_order_id`),
  KEY `fk_c_register_office_id_idx` (`c_register_office_id`),
  KEY `fk_c_street_code_idx` (`c_street_code`),
  CONSTRAINT `fk_c_register_office_id` FOREIGN KEY (`c_register_office_id`) REFERENCES `jc_register_office` (`r_office_id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_c_street_code` FOREIGN KEY (`c_street_code`) REFERENCES `jc_street` (`street_code`) ON DELETE RESTRICT,
  CONSTRAINT `fk_student_order_id` FOREIGN KEY (`student_order_id`) REFERENCES `jc_student_order` (`id`) ON DELETE RESTRICT
);

