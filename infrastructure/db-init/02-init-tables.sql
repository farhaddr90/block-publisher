CREATE TABLE block (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       number INT NOT NULL,
                       hash VARCHAR(255) NOT NULL,
                       raw_data LONGBLOB,

                       creation_date DATETIME,
                       last_modified_date DATETIME,

                       UNIQUE KEY uk_block_number (number),
                       UNIQUE KEY uk_block_hash (hash)
);

CREATE INDEX idx_block_number ON block(number);