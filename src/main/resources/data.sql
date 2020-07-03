CREATE TABLE customerZipCodes(
    id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    cep varchar(25),
    logradouro varchar(255),
    complemento varchar(25),
    bairro varchar(255),
    uf varchar(2),
    unidade varchar(255),
    ibge varchar(255),
    gia varchar(255)
);


INSERT INTO customer (id, name, email) VALUES
(1, 'Mariazinha', 'mariazinha@email.com'),
(2, 'Joãozinho', 'joaozinho@email.com');