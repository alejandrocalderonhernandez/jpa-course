DELIMITER //
CREATE FUNCTION random_date(days INTEGER) RETURNS DATE
BEGIN
    RETURN DATE_SUB(CURDATE(), INTERVAL FLOOR(RAND() * days) DAY);
END //
DELIMITER ;

CREATE TABLE bill (
    id VARCHAR(64) PRIMARY KEY,
    total_amount DECIMAL(10, 2) NULL,
    client_rfc VARCHAR(14) NOT NULL
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    client_name VARCHAR(32) NOT NULL,
    id_bill VARCHAR(64) UNIQUE NOT NULL,
    FOREIGN KEY (id_bill) REFERENCES bill(id) ON DELETE CASCADE
);

CREATE TABLE products_catalog (
    id CHAR(36) PRIMARY KEY,
    product_name VARCHAR(64) NOT NULL UNIQUE,
    brand_name VARCHAR(64) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    rating SMALLINT,
    launching_date DATE,
    is_discount BOOLEAN
);

-- Trigger para generar UUID automáticamente
DELIMITER //
CREATE TRIGGER before_insert_products_catalog
BEFORE INSERT ON products_catalog
FOR EACH ROW
BEGIN
    IF NEW.id IS NULL THEN
        SET NEW.id = UUID();
    END IF;
END //
DELIMITER ;

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT DEFAULT 1,
    id_product_catalog CHAR(36),
    id_order BIGINT,
    FOREIGN KEY (id_order) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (id_product_catalog) REFERENCES products_catalog(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code ENUM('HOME', 'OFFICE', 'NONE') DEFAULT 'NONE',
    description VARCHAR(255) NOT NULL
);

CREATE TABLE product_join_category (
    id_category BIGINT,
    id_product CHAR(36),
    FOREIGN KEY (id_category) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY (id_product) REFERENCES products_catalog(id) ON DELETE CASCADE
);

CREATE TABLE reject_products (
    product_name VARCHAR(64) NOT NULL,
    brand_name VARCHAR(64) NOT NULL,
    quantity INT,
    PRIMARY KEY (product_name, brand_name)
);

DELIMITER //
CREATE PROCEDURE count_total_products_by_brand(IN brand VARCHAR(64), OUT response INTEGER)
BEGIN
    SELECT COUNT(*) INTO response
    FROM products_catalog
    WHERE brand_name = brand
    GROUP BY brand_name;
END //
DELIMITER ;


INSERT INTO bill (id, total_amount, client_rfc) VALUES
    ('b-1', 8101.76, 'ERT655687JHY'),
    ('b-2', 4301.88, 'AZ45NM78BC79'),
    ('b-3', 8101.76, 'PXLSO9382228'),
    ('b-4', 8101.76, 'ST655687JHY7'),
    ('b-5', 4301.88, 'RC45NM78BC79'),
    ('b-6', 8101.76, 'JRT655687JHY'),
    ('b-7', 4301.88, 'AK45NM78BC72'),
    ('b-8', 8101.76, 'PMOON9382221'),
    ('b-9', 6789.45, 'XYZP8765432Q'),
    ('b-10', 2345.67, 'LMNO9876543R'),
    ('b-11', 9876.54, 'ABCD1234567S'),
    ('b-12', 7654.32, 'EFGH5678901T'),
    ('b-13', 3456.78, 'IJKL9012345U'),
    ('b-14', 8765.43, 'MNOP2345678V'),
    ('b-15', 5432.10, 'QRST6789012W'),
    ('b-16', 6543.21, 'UVWX0123456X');

INSERT INTO orders (created_at, client_name, id_bill) VALUES
    (NOW(), 'Ronda Rousey', 'b-1'),
    (NOW(), 'Amanda Nunes', 'b-2'),
    (NOW(), 'Conor McGregor', 'b-3'),
    (NOW(), 'Jon Jones', 'b-4'),
    (NOW(), 'Cody Garbrandt', 'b-5'),
    (NOW(), 'Dominick Cruz', 'b-6'),
    (NOW(), 'Demetrious Johnson', 'b-7'),
    (NOW(), 'Daniel Cormier', 'b-8'),
    (NOW(), 'Khabib Nurmagomedov', 'b-9'),
    (NOW(), 'Israel Adesanya', 'b-10'),
    (NOW(), 'Stipe Miocic', 'b-11'),
    (NOW(), 'Kamaru Usman', 'b-12'),
    (NOW(), 'Dustin Poirier', 'b-13'),
    (NOW(), 'Max Holloway', 'b-14'),
    (NOW(), 'Robert Whittaker', 'b-15'),
    (NOW(), 'Tony Ferguson', 'b-16');

INSERT INTO products_catalog (id, launching_date, brand_name, product_name, description, price, rating, is_discount) VALUES
    (UUID(), random_date(3000), 'ESP', 'Guitarra electrica - home', 'Is a guitar for home', 3400.99, 10, FALSE),
    (UUID(), random_date(3000), 'Fender', 'Bajo electrico  - home', 'Is a bass for home', 3200.99, 6, TRUE),
    (UUID(), random_date(3000), 'Pearl', 'Bateria - home', 'Is a drums for home', 6800.89, 5, FALSE),
    (UUID(), random_date(3000), 'Apple', 'Macbook air', 'Is a pc for home', 4700.77, 7, FALSE),
    (UUID(), random_date(3000), 'Apple', 'Macbook pro', 'Is a pc - office', 4700.77, 7, TRUE),
    (UUID(), random_date(3000), 'LG', 'TV 65', 'tv for home', 900.89, 8, FALSE),
    (UUID(), random_date(3000), 'LG', 'TV 75', 'tv for home', 1000.89, 2, FALSE),
    (UUID(), random_date(3000), 'Samsung', 'Monitor 27', 'monitor for home and office', 200.89, 9, FALSE),
    (UUID(), random_date(3000), 'Samsung', 'Monitor 24', 'monitor for home and office', 99.89, 10, TRUE),
    (UUID(), random_date(3000), 'Desktop', 'Desktop for pc', 'for office', 209.21, 8, FALSE),
    (UUID(), random_date(3000), 'Amazon', 'Alexa small', 'for enjoy in  home and office', 500.89, 1, TRUE),
    (UUID(), random_date(3000), 'Amazon', 'Alexa medium', 'for enjoy in  home and office', 650.89, 5, FALSE),
    (UUID(), random_date(3000), 'Amazon', 'Alexa large', 'for enjoy in  home and office', 900.89, 10, FALSE),
    (UUID(), random_date(3000), 'Lenovo', 'Pc gamer', 'pc for home', 7000.89, 10, FALSE),
    (UUID(), random_date(3000), 'Lenovo', 'Pc office', 'pc for office', 6500.89, 3, TRUE),
    (UUID(), random_date(3000), 'Lenovo', 'Pc student', 'pc for home', 2000.89, 10, FALSE),
    (UUID(), random_date(3000), 'Amazon', 'Alexa super', 'for enjoy in  home and office', 200.89, 4, TRUE),
    (UUID(), random_date(3000), 'Lenovo', 'Pc gamer max', 'pc for home', 7000.89, 4, FALSE),
    (UUID(), random_date(3000), 'Lenovo', 'Pc office max', 'pc for office', 6500.89, 6, FALSE),
    (UUID(), random_date(3000), 'Lenovo', 'Pc student max', 'pc for home and office', 2000.89, 2, FALSE),
    (UUID(), random_date(3000), 'Fresh', 'Air-conditioning 70', 'for home', 4400.34, 7, FALSE),
    (UUID(), random_date(3000), 'Fresh', 'Air-conditioning 80', 'pc for home and office', 5500.57, 9, FALSE),
    (UUID(), random_date(3000), 'Microsoft', 'office home', 'for enjoy in  home', 100.00, 10, FALSE),
    (UUID(), random_date(3000), 'Microsoft', 'office pro', 'for enjoy in  office', 180.00, 5, TRUE),
    (UUID(), random_date(3000), 'Microsoft', 'xbox series s', 'console for home', 1500.89, 5, FALSE),
    (UUID(), random_date(3000), 'Microsoft', 'xbox series x', 'console for home', 1900.89, 10, FALSE),
    (UUID(), random_date(3000), 'Sony', 'PS-5', 'console for home', 1800.89, 9, FALSE),
    (UUID(), random_date(3000), 'Nike', 'backpack N', 'pc for home and office', 30.57, 9, FALSE),
    (UUID(), random_date(3000), 'Puma', 'backpack P', 'for enjoy in home and office', 40.20, 10, FALSE),
    (UUID(), random_date(3000), 'Adidas', 'backpack A', 'for enjoy in  office and home', 23.00, 5, TRUE),
    (UUID(), random_date(3000), 'Champion', 'backpack c', 'for office nad home', 16.89, 5, FALSE),
    (UUID(), random_date(3000), 'Nintendo', 'Switch', 'console for home', 1100.89, 8, FALSE),
    (UUID(), random_date(3000), 'Gibson', 'Guitarra acústica - home', 'Guitar for home', 2800.99, 9, FALSE),
    (UUID(), random_date(3000), 'Yamaha', 'Piano digital - home', 'For home', 4500.89, 8, TRUE),
    (UUID(), random_date(3000), 'Roland', 'Batería electrónica - home', 'Drums home', 5200.79, 7, FALSE),
    (UUID(), random_date(3000), 'Dell', 'Laptop Inspiron', 'home', 3900.67, 6, FALSE),
    (UUID(), random_date(3000), 'HP', 'Laptop Pavilion', 'office', 4100.89, 7, TRUE),
    (UUID(), random_date(3000), 'Samsung', 'TV QLED 55"', 'TV home', 1200.99, 9, FALSE),
    (UUID(), random_date(3000), 'Sony', 'TV OLED 65"', 'TV office', 2500.79, 10, FALSE),
    (UUID(), random_date(3000), 'BenQ', 'Monitor 32"', 'home and office', 300.89, 8, FALSE),
    (UUID(), random_date(3000), 'Asus', 'Monitor 27" Gamer', 'home and office', 450.99, 9, TRUE),
    (UUID(), random_date(3000), 'HP', 'PC de escritorio', 'PC for office', 455.99, 9, TRUE);

INSERT INTO categories (code, description) VALUES
    ('HOME', 'for home'),
    ('OFFICE', 'for office');