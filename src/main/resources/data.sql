
INSERT INTO USUARIO (DTYPE, ID, EMAIL, NOME, SENHA, SOBRENOME, PONTUACAO_DO_ALUNO) 
VALUES ('Usuario', 1, 'teste@gmail.com', 'Usuario',/*(12345)*/ '$2a$10$rUmx8yw4tF9JF0FsRh8wWepjOTD6HWF.1gA.kI/qjgGToWSbNsKN6', 'Teste', 0),
('Usuario', 2, 'teste2@gmail.com', 'Usuario',/*(12345)*/ '$2a$10$rUmx8yw4tF9JF0FsRh8wWepjOTD6HWF.1gA.kI/qjgGToWSbNsKN6', 'Teste', 0);

/* Palavras já cadastradas*/
INSERT INTO PALAVRA (ID, DEFINICAO, INGLES, PORTUGUES, TERMO_TECNICO)
VALUES (1, 'PALAVRA 1', 'DOCUMENT', 'DOCUMENTO', 0),
(2, 'PALAVRA 2', 'SCRIPT', 'SCRIPT', 0),
(3, 'PALAVRA 3', 'ELEMENT', 'ELEMENTO', 0),
(4, 'PALAVRA 4', 'CASE', 'CASO', 0),
(5, 'PALAVRA 5', 'WRITE DOCUMENT', 'ESCREVER DOCUMENTO', 0),
(6, 'PALAVRA 6', 'WRITE SCRIPT', 'ESCREVER SCRIPT', 0),
(7, 'PALAVRA 7', 'MATH ROUND', 'ARREDONDAR', 0),
(8, 'PALAVRA 8', 'SWITCH CASE', 'ESTRUTURA DE CONDIÇÃO', 0),
(9, 'PALAVRA 9', 'RETURN', 'VOLTAR', 0),
(10, 'PALAVRA 10', 'BREAK', 'INTERROMPER', 0),
(11, 'PALAVRA 11', 'CASE', 'CASO', 0),
(12, 'PALAVRA 12', 'HEAD', 'CABEÇA', 0),
(13, 'PALAVRA 13', 'TELA GRÁFICA QUE EXIBE INFORMAÇÕES', '-', 0),
(14, 'PALAVRA 14', 'COMANDO PARA TROCAR OPÇÕES', '-', 0),
(15, 'PALAVRA 15', 'É UMA FUNÇÃO', '-', 0),
(16, 'PALAVRA 16', 'É UMA LINGUAGEM DE PROGRAMAÇÃO', '-',0),
(17, 'PALAVRA 17', 'SEARCH', 'BUSCA', 0),
(18, 'PALAVRA 18', 'SCREEN', 'TELA', 0),
(19, 'PALAVRA 19', 'SWITCH', 'SWITCH', 0),
(20, 'PALAVRA 20', 'SOURCE', 'FONTE', 0),
(21, 'PALAVRA 21', 'UM ASTRONAUTA AMERICANO', '-', 0),
(22, 'PALAVRA 22', 'UM MAGNATA AMERICANO', '-', 0),
(23, 'PALAVRA 23', 'UM PROFESSOR AMERICANO', '-', 0),
(24, 'PALAVRA 24', 'UM PRESIDENTE AMERICANO', '-', 0),
(25, 'PALAVRA 25', 'CRIADOR DE SOFTWARE, INVESTIDOR E FILANTROPO', '-', 0),
(26, 'PALAVRA 26', 'CRIADOR DE SOFTWARE, INVENTOR E FILANTROPO', '-', 0),
(27, 'PALAVRA 27', 'BANQUEIRO, INVENTOR E FILANTROPO', '-', 0),
(28, 'PALAVRA 28', 'FAZENDEIRO, INVESTIDOR E FILANTROPO', '-', 0),
(29, 'PALAVRA 29', 'BILL GATES FOI PRESIDENTE DA MICROSOFT', '-', 0),
(30, 'PALAVRA 30', 'ELE FUNDOU A MICROSOFT SOZINHO', '-', 0),
(31, 'PALAVRA 31', 'ELE NASCEU E FOI CRIADO EM SEATTLE', '-', 0),
(32, 'PALAVRA 32', 'HOUVE UMA ÉPOCA EM QUE ELE FOI CRITICADO PELO SEU MODO DE NEGOCIAR', '-', 0),
(33, 'PALAVRA 33', 'ORGANIZAÇÕES CARAS', '-', 0),
(34, 'PALAVRA 34', 'INSTITUIÇÕES CARAS', '-', 0),
(35, 'PALAVRA 35', 'INSTITUIÇÕES DE CARIDADE', '-', 0),
(36, 'PALAVRA 36', 'ORFANATOS', '-', 0),
(37, 'PALAVRA 37', 'IMPROVE GLOBAL HEALTH', 'MELHORAR A SAÚDE GLOBAL', 0),
(38, 'PALAVRA 38', 'WEALTH TO PHILANTHROPY', 'RIQUEZA PARA A FILANTROPIA', 0),
(39, 'PALAVRA 39', 'ELIMINATE PÓLIO', 'ELEMINAR PÓLIO', 0),
(40, 'PALAVRA 40', 'SAVE LIVES', 'SALVAR VIDAS', 0);

/* Conteúdos já cadastrados*/
INSERT INTO CONTEUDO (ID, TITULO)
VALUES (1, 'javascript1'),
(2, 'javascript2'),
(3, 'programação'),
(4, 'S.O.'),
(5, 'geral'),
(6, 'textbill1'),
(7, 'textbill2'),
(8, 'textbill3'),
(9, 'textbill4'),
(10, 'textbill5');

INSERT INTO PALAVRA_CONTEUDOS(PALAVRAS_ID, CONTEUDOS_ID)
VALUES (1, 1), (2, 1), (3, 1), (4, 1),
        (5, 2), (6, 2), (7, 2), (8, 2),
        (9, 3), (10, 3), (11, 3), (12, 3),
        (13, 4), (14, 4), (15, 4), (16, 4),
        (17, 5), (18, 5), (19, 5), (20, 5),
        (21, 6), (22, 6), (23, 6), (24, 6),
        (25, 7), (26, 7), (27, 7), (28, 7),
        (29, 8), (30, 8), (31, 8), (32, 8),
        (33, 9), (34, 9), (35, 9), (36, 9),
        (37, 10), (38, 10), (39, 10), (40, 10);

INSERT INTO TAREFA(ID, ENUNCIADO, NIVEL, PONTUACAO, TIPO_TAREFA, RESPOSTA_ID)
VALUES (1, 'Escolha a alternativa que complete apropriadamente a frase: "Get __________ by id."', 1, 30, 5, 3),
    (2, 'Qual desses comandos é um método que exibe informações no arquivo?', 2, 30, 5, 5),
    (3, 'Que comando é usado para solicitar uma pausa/intervalo?', 0, 30, 5, 10),
    (4, 'O que é PROMPT?', 0, 30, 5, 13),
    (5, 'O que significa a sigla "src"?', 0, 30, 5, 20),
    (6, 'Quem é Bill Gates?', 0, 30, 5, 22),
    (7, 'Bill Gates também é...', 0, 30, 5, 25),
    (8, 'De acordo com o texto, qual das alternativas abaixo não é VERDADEIRA?', 0, 30, 5, 30),
    (9, 'O que significa "charitable organizations"?', 0, 30, 5, 35),
    (10, 'Qual das expressões abaixo significa "salvar vidas"?', 0, 30, 5, 40);

INSERT INTO ROLE(NOME)
VALUES ('ROLE_ADMIN');

INSERT INTO USUARIOS_ROLES(USUARIO_ID, ROLE_ID)
VALUES(1, 'ROLE_ADMIN');