ALTER TABLE `gerenciaprojetos`.`projetos` ADD COLUMN `dataFim` DATE NULL DEFAULT NULL  AFTER `pronto` , ADD COLUMN `dataInicio` DATE NULL DEFAULT NULL  AFTER `dataFim` , ADD COLUMN `dataPrevisao` DATE NULL DEFAULT NULL  AFTER `dataInicio` ;


