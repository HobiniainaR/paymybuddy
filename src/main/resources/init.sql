-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `paymybuddy` ;

-- -----------------------------------------------------
-- Table `paymybuddy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user` (
  `id` INT NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`connection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`connection` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `origin_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `connection_ibfk_1` (`user_id` ASC) VISIBLE,
  INDEX `connection_ibfk_2_idx` (`origin_id` ASC) VISIBLE,
  CONSTRAINT `connection_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`user` (`id`),
  CONSTRAINT `connection_ibfk_2`
    FOREIGN KEY (`origin_id`)
    REFERENCES `paymybuddy`.`connection` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_id` INT NOT NULL,
  `receiver_id` INT NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `amount` DOUBLE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `transaction_ibfk_1` (`sender_id` ASC) VISIBLE,
  INDEX `transaction_ibfk_2` (`receiver_id` ASC) VISIBLE,
  CONSTRAINT `transaction_ibfk_1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `paymybuddy`.`user` (`id`),
  CONSTRAINT `transaction_ibfk_2`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `paymybuddy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
ALTER TABLE User ADD COLUMN balance DOUBLE DEFAULT 0;
