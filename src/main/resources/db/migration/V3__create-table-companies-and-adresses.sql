CREATE TABLE companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    business_name VARCHAR(150) NOT NULL,
    trade_name VARCHAR(150),
    phone VARCHAR(20),
    contact_email VARCHAR(100),
    foundation_data DATE,
    company_situation VARCHAR(20) NOT NULL
);

CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(150) NOT NULL,
    number INT NOT NULL,
    complement VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state CHAR(2) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    company_id BIGINT NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (company_id)
        REFERENCES companies (id)
        ON DELETE CASCADE
);