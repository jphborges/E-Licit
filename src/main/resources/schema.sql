CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    cnpj VARCHAR(30),
    catalogo TEXT,
    endereco VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS cotacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hospital_id INT NOT NULL,
    titulo VARCHAR(150),
    descricao TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_abertura DATETIME NOT NULL,
    data_fechamento DATETIME,
    validade DATETIME,
    FOREIGN KEY (hospital_id) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS proposta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cotacao_id INT NOT NULL,
    fornecedor_id INT NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    condicoes_pagamento TEXT,
    status VARCHAR(20) NOT NULL,
    data_envio DATETIME NOT NULL,
    validade DATE,
    FOREIGN KEY (cotacao_id) REFERENCES cotacao(id),
    FOREIGN KEY (fornecedor_id) REFERENCES usuario(id),
    CONSTRAINT uk_proposta_cotacao_fornecedor UNIQUE (cotacao_id, fornecedor_id)
);


