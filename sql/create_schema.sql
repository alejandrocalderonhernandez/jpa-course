CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE code AS ENUM ('HOME', 'OFFICE', 'NONE');

CREATE OR REPLACE FUNCTION random_date(days INTEGER) RETURNS DATE AS $$
BEGIN
    RETURN DATE(CURRENT_DATE - TRUNC(RANDOM() * days) * '1 DAY'::INTERVAL);
END;
$$ LANGUAGE plpgsql;

CREATE TABLE bill (
    id VARCHAR(64) PRIMARY KEY,
    total_amount NUMERIC NULL,
    client_rfc VARCHAR(14) NOT NULL
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    client_name VARCHAR(32) NOT NULL,
    id_bill VARCHAR(64) UNIQUE NOT NULL,
    FOREIGN KEY (id_bill) REFERENCES bill(id) ON DELETE CASCADE
);

CREATE TABLE products_catalog (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    product_name VARCHAR(64) NOT NULL UNIQUE,
    brand_name VARCHAR(64) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price NUMERIC NOT NULL,
    rating SMALLINT,
    launching_date DATE,
    is_discount BOOLEAN
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    quantity INT DEFAULT 1,
    id_product_catalog UUID,
    id_order BIGINT,
    FOREIGN KEY (id_order) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (id_product_catalog) REFERENCES products_catalog(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    code code DEFAULT 'NONE',
    description VARCHAR(255) NOT NULL
);

CREATE TABLE product_join_category (
    id_category BIGINT,
    id_product UUID,
    FOREIGN KEY (id_category) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY (id_product) REFERENCES products_catalog(id) ON DELETE CASCADE
);

CREATE OR REPLACE PROCEDURE count_total_products_by_brand(IN brand VARCHAR, OUT response INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT COUNT(*) INTO response
    FROM products_catalog
    WHERE brand_name = brand
    GROUP BY brand_name;
END;
$$;
