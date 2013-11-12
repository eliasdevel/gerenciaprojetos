-- 
-- DELIMITER $$
-- 
-- CREATE TRIGGER definirPecentualTopicos AFTER INSERT
-- ON projetos_topicos pt
-- FOR EACH ROW
-- BEGIN
--    UPDATE Projetos SET Estoque = Estoque - NEW.Quantidade
-- WHERE Referencia = NEW.Produto;
-- END$$

DELIMITER $$

CREATE TRIGGER definirPecentualTopicos AFTER INSERT
ON projetos_topicos pt
FOR EACH ROW
BEGIN
   UPDATE Projetos p SET p.status = 100 where p.idprojeto = NEW.idprojeto;
END$$

select p. from projetos p
select @tituloPro