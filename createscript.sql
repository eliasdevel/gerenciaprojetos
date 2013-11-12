SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `gerenciaprojetos` DEFAULT CHARACTER SET latin1 ;
USE `gerenciaprojetos` ;

-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`categorias` (
  `idcategoria` INT(11) NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NULL DEFAULT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idcategoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`estados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`estados` (
  `sigla` VARCHAR(2) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`sigla`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`cidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`cidades` (
  `idcidade` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(200) NOT NULL,
  `cep` VARCHAR(45) NULL DEFAULT NULL,
  `siglaestado` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`idcidade`),
  INDEX `fk_cidades_estados1_idx` (`siglaestado` ASC),
  CONSTRAINT `fk_cidades_estados1`
    FOREIGN KEY (`siglaestado`)
    REFERENCES `gerenciaprojetos`.`estados` (`sigla`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 539935
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`clientes` (
  `idcliente` INT(11) NOT NULL AUTO_INCREMENT,
  `idcidade` INT(11) NOT NULL,
  `nome` VARCHAR(200) NULL DEFAULT NULL,
  `telefone` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `adicional` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`idcliente`),
  INDEX `fk_clientes_cidades1_idx` (`idcidade` ASC),
  CONSTRAINT `fk_clientes_cidades1`
    FOREIGN KEY (`idcidade`)
    REFERENCES `gerenciaprojetos`.`cidades` (`idcidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`desenvolvedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`desenvolvedores` (
  `iddesenvolvedor` INT(11) NOT NULL AUTO_INCREMENT,
  `idcategoria` INT(11) NOT NULL,
  `nome` VARCHAR(200) NULL DEFAULT NULL,
  `telefone` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`iddesenvolvedor`),
  INDEX `fk_desenvolvedores_categoriadesenvolvedor_idx` (`idcategoria` ASC),
  CONSTRAINT `fk_desenvolvedores_categoriadesenvolvedor`
    FOREIGN KEY (`idcategoria`)
    REFERENCES `gerenciaprojetos`.`categorias` (`idcategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`projetos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`projetos` (
  `idprojeto` INT(11) NOT NULL AUTO_INCREMENT,
  `idcliente` INT(11) NOT NULL,
  `titulo` VARCHAR(45) NULL DEFAULT NULL,
  `descricao` TEXT NULL DEFAULT NULL,
  `status` DECIMAL(10,0) NULL DEFAULT NULL,
  `pronto` TINYINT(1) NULL DEFAULT NULL,
  `dataInicial` DATE NULL,
  `dataPrevisao` DATE NULL,
  PRIMARY KEY (`idprojeto`),
  INDEX `fk_projetos_clientes1_idx` (`idcliente` ASC),
  CONSTRAINT `fk_projetos_clientes1`
    FOREIGN KEY (`idcliente`)
    REFERENCES `gerenciaprojetos`.`clientes` (`idcliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`projetos_desenvolvedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`projetos_desenvolvedores` (
  `idprojeto` INT(11) NOT NULL,
  `iddesenvolvedor` INT(11) NOT NULL,
  PRIMARY KEY (`idprojeto`, `iddesenvolvedor`),
  INDEX `fk_projetos_has_desenvolvedores_desenvolvedores1_idx` (`iddesenvolvedor` ASC),
  INDEX `fk_projetos_has_desenvolvedores_projetos1_idx` (`idprojeto` ASC),
  CONSTRAINT `fk_projetos_has_desenvolvedores_desenvolvedores1`
    FOREIGN KEY (`iddesenvolvedor`)
    REFERENCES `gerenciaprojetos`.`desenvolvedores` (`iddesenvolvedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projetos_has_desenvolvedores_projetos1`
    FOREIGN KEY (`idprojeto`)
    REFERENCES `gerenciaprojetos`.`projetos` (`idprojeto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`topicos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`topicos` (
  `idtopico` INT(11) NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NULL DEFAULT NULL,
  `descricao` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`idtopico`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `gerenciaprojetos`.`projetos_topicos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gerenciaprojetos`.`projetos_topicos` (
  `idtopico` INT(11) NOT NULL,
  `idprojeto` INT(11) NOT NULL,
  `pronto` TINYINT(1) NULL DEFAULT NULL,
  `situacao` CHAR(1) NULL,
  PRIMARY KEY (`idtopico`, `idprojeto`),
  INDEX `fk_topicosprojeto_has_projetos_projetos1_idx` (`idprojeto` ASC),
  INDEX `fk_topicosprojeto_has_projetos_topicosprojeto1_idx` (`idtopico` ASC),
  CONSTRAINT `fk_topicosprojeto_has_projetos_projetos1`
    FOREIGN KEY (`idprojeto`)
    REFERENCES `gerenciaprojetos`.`projetos` (`idprojeto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_topicosprojeto_has_projetos_topicosprojeto1`
    FOREIGN KEY (`idtopico`)
    REFERENCES `gerenciaprojetos`.`topicos` (`idtopico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
