
INSERT INTO USUARIO (DTYPE, ID, EMAIL, NOME, SENHA, SOBRENOME, PONTUACAO_DO_ALUNO) 
VALUES ('Usuario', 1, 'teste@gmail.com', 'Usuario',/*(12345)*/ '$2a$10$rUmx8yw4tF9JF0FsRh8wWepjOTD6HWF.1gA.kI/qjgGToWSbNsKN6', 'Teste', 0);

/* Palavras já cadastradas*/
INSERT INTO PALAVRA (ID, DEFINICAO, INGLES, PORTUGUES, TERMO_TECNICO)
VALUES (1, 'PALAVRA 1', 'HOUSE', 'CASA', 0),
(2, 'PALAVRA 2', 'IF', 'SE', 1),
(3, 'PALAVRA 3', 'WHILE', 'ENQUANTO', 0),
(4, 'PALAVRA 4', 'ELSE', 'SE NÃO', 1),
(5, 'PALAVRA 5', 'BREAK', 'PARAR', 0);

/* Conteúdos já cadastrados*/
INSERT INTO CONTEUDO (ID, TITULO)
VALUES (1, 'loops'),
(2, 'verboToBE'),
(3, 'condicionais');

INSERT INTO PALAVRA_CONTEUDOS(PALAVRAS_ID, CONTEUDOS_ID)
VALUES (1, 3), (2, 3), (3, 3), (4, 1);

INSERT INTO TAREFA(ID, ENUNCIADO, NIVEL, PONTUACAO, TIPO_TAREFA, RESPOSTA_ID)
VALUES (1, 'Which of these is a conditional structure?', 1, 120, 5, 2),
(2, 'Which of these is used to stop a repeat loop?', 0, 500, 5, 5),
(3, 'Which of these is not a programming language?', 2, 666, 5, 1);

INSERT INTO ROLE(NOME)
VALUES ('ROLE_ADMIN');

INSERT INTO USUARIOS_ROLES(USUARIO_ID, ROLE_ID)
VALUES(1, 'ROLE_ADMIN');