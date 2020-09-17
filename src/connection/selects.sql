--exmplo de CASE WHEN
select operacao, CASE operacao WHEN 'C' THEN 'Crédito' ELSE 'Débito' END as descritivo
from movimentos;

--1) Apresentar o saldo de cada conta. Mostrar nome da conta e saldo
SELECT c.conta, c.nome, 
SUM(CASE operacao WHEN 'C' THEN -m.valor ELSE m.valor END) as saldo
FROM contas c, movimentos m
WHERE c.id = m.id_conta
GROUP BY c.conta, c.nome;

--2) Apresentar o saldo de cada conta até a data de 20/08/2020. Mostrar nome 
--da conta e saldo
SELECT c.conta, c.nome, 
SUM(CASE operacao WHEN 'C' THEN -m.valor ELSE m.valor END) as saldo
FROM contas c, movimentos m
WHERE c.id = m.id_conta
AND m.data <= '2020-08-20'
GROUP BY c.conta, c.nome;

--3) Apresentar a quantidade de movimentos que cada conta tem. Mostrar nome 
--da conta e número de movimentos
SELECT c.nome, COUNT(m.id) as quant
FROM contas c
INNER JOIN movimentos m ON c.id = m.id_conta
GROUP BY c.nome

--exemplos de funções de agrupamento de dados: SUM(), COUNT()