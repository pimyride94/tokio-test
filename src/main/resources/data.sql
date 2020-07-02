CREATE TABLE customerZipCodes(
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    id_customer int,
    zip_code varchar(25)
);


INSERT INTO customer (id, name, email) VALUES
(1, 'Mariazinha', 'mariazinha@email.com'),
(2, 'Joãozinho', 'joaozinho@email.com');