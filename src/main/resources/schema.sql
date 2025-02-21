-- Drop tables if they exist
DROP TABLE IF EXISTS consultas;
DROP TABLE IF EXISTS pacientes;
DROP TABLE IF EXISTS usuarios;

-- Create usuarios table
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Create pacientes table
CREATE TABLE pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    numero_sus VARCHAR(15) UNIQUE,
    telefone VARCHAR(20),
    rua VARCHAR(255),
    bairro VARCHAR(255)
);

-- Create consultas table
CREATE TABLE consultas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    observacoes TEXT,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);
