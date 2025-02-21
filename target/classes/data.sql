-- Deletar usuário admin se existir
DELETE FROM usuarios WHERE username = 'admin@admin.com';

-- Inserir usuário admin
INSERT INTO usuarios (username, password, role) VALUES 
('admin@admin.com', '$2a$10$GQT/ftLe0/0xrR9B2W9F4.vTBXXsYKZU1t8cGnGV1Xn1mFR4OJgGy', 'ADMIN');

-- Inserir pacientes de exemplo
INSERT INTO pacientes (nome, numero_sus, telefone, rua, bairro) VALUES
('João Silva', '123456789012345', '(11) 98765-4321', 'Rua das Flores, 123', 'Jardim Primavera'),
('Maria Santos', '987654321098765', '(11) 91234-5678', 'Av. Principal, 456', 'Centro'),
('Pedro Oliveira', '456789012345678', '(11) 94567-8901', 'Rua do Comércio, 789', 'Vila Nova');

-- Inserir consultas de exemplo
INSERT INTO consultas (paciente_id, data_hora, status, observacoes, created_at) VALUES
((SELECT id FROM pacientes WHERE nome = 'João Silva'), 
 DATEADD('DAY', 1, CURRENT_TIMESTAMP), 'AGENDADA', 'Primeira consulta', CURRENT_TIMESTAMP),

((SELECT id FROM pacientes WHERE nome = 'Maria Santos'), 
 DATEADD('DAY', 2, CURRENT_TIMESTAMP), 'AGENDADA', 'Retorno', CURRENT_TIMESTAMP),

((SELECT id FROM pacientes WHERE nome = 'Pedro Oliveira'), 
 DATEADD('DAY', 3, CURRENT_TIMESTAMP), 'AGENDADA', 'Avaliação inicial', CURRENT_TIMESTAMP);
