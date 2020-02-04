
INSERT INTO USUARIO (DTYPE, ID, EMAIL, NOME, SENHA, SOBRENOME, PONTUACAO_DO_ALUNO) 
VALUES ('Usuario', 1, 'teste@gmail.com', 'Usuario',/*(12345)*/ '$2a$10$rUmx8yw4tF9JF0FsRh8wWepjOTD6HWF.1gA.kI/qjgGToWSbNsKN6', 'Teste', 0),
('Usuario', 2, 'teste2@gmail.com', 'Usuario',/*(12345)*/ '$2a$10$rUmx8yw4tF9JF0FsRh8wWepjOTD6HWF.1gA.kI/qjgGToWSbNsKN6', 'Teste', 0);

/* Palavras já cadastradas*/
INSERT INTO PALAVRA (ID, DEFINICAO, INGLES, PORTUGUES, TERMO_TECNICO)
VALUES (1, 'PALAVRA 1', 'SWITCH-CASE', 'VÁRIOS CASOS', 0),
(2, 'PALAVRA 2', 'IF', 'SE', 0),
(3, 'PALAVRA 3', 'WHILE', 'ENQUANTO', 0),
(4, 'PALAVRA 4', 'ELSE', 'SE NÃO', 0),
(5, 'PALAVRA 5', 'BREAK', 'PARAR', 0),
(6, 'PALAVRA 6', 'VOID', 'VAZIO', 0),
(7, 'PALAVRA 7', 'INT', 'INT', 0),
(8, 'PALAVRA 8', 'DOUBLE', 'DUPLO', 0),
(9, 'PALAVRA 9', 'CHAR', 'CARACTERES', 0),
(10, 'PALAVRA 10', 'FI', 'FI', 0),
(11, 'PALAVRA 11', 'CONTINUE', 'CONTINUE', 0);

/* Conteúdos já cadastrados*/
INSERT INTO CONTEUDO (ID, TITULO)
VALUES (1, 'loops'),
(2, 'logica'),
(3, 'condicionais');

INSERT INTO PALAVRA_CONTEUDOS(PALAVRAS_ID, CONTEUDOS_ID)
VALUES (11, 1), (4, 1), (5, 1), (1, 1),
       (7, 2), (8, 2), (9, 2), (6, 2),
       (2, 3), (3, 3), (10, 3), (1, 3);

INSERT INTO TAREFA(ID, ENUNCIADO, NIVEL, PONTUACAO, TIPO_TAREFA, RESPOSTA_ID)
VALUES (1, 'Which of these is a conditional structure?', 1, 30, 5, 2),
(2, 'Which of these is used to stop a repeat loop?', 0, 30, 5, 5),
(3, 'Using your knowledge, which of these is used for floating numbers?', 2, 30, 5, 8);

INSERT INTO ROLE(NOME)
VALUES ('ROLE_ADMIN');

INSERT INTO USUARIOS_ROLES(USUARIO_ID, ROLE_ID)
VALUES(1, 'ROLE_ADMIN');