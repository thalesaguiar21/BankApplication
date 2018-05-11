-- MySQL Script generated by MySQL Workbench
-- Fri May 11 11:45:02 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema bankapp_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bankapp_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bankapp_db` DEFAULT CHARACTER SET utf8 ;
USE `bankapp_db` ;

-- -----------------------------------------------------
-- Table `bankapp_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankapp_db`.`user` (
  `user_id` INT AUTO_INCREMENT,
  `name` VARCHAR(90) NULL,
  `cpf` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankapp_db`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankapp_db`.`account` (
  `account_id` INT AUTO_INCREMENT,
  `acc_number` INT(32) NOT NULL,
  `balance` DOUBLE NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`account_id`),
  INDEX `fk_account_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_account_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `bankapp_db`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bankapp_db`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankapp_db`.`log` (
  `log_id` INT AUTO_INCREMENT,
  `msg` VARCHAR(100) NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`log_id`),
  INDEX `fk_log_account1_idx` (`account_id` ASC),
  CONSTRAINT `fk_log_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `bankapp_db`.`account` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
