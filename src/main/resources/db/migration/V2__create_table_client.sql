CREATE TABLE PUBLIC.CLIENTE (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    fk_cidade BIGINT null,
    nome_completo VARCHAR(80) NOT NULL,
    idade INTEGER,
    sexo CHAR(1),
    data_nascimento DATE NOT NULL
);

ALTER TABLE CLIENTE ADD FOREIGN KEY (fk_cidade) REFERENCES cidade (id);