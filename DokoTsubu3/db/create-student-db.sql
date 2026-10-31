-- Program Name: create-student-db.sql
-- Language: SQL
-- Function: Create an empty dokotsubu schema and Target Schema USERS and MUTTERS
-- Created: 2026-10-08
-- Last Updated: 2026-10-08
-- Author: Takashi Oikawa
-- AI: Cursor Grok 4.7
-- Memo: Initial creation for an empty environment only. No DROP, TRUNCATE, DELETE, IF NOT EXISTS, FK, extra index, CHECK, or default.

CREATE DATABASE dokotsubu;

USE dokotsubu;

CREATE TABLE USERS (
    ID INT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(100) NOT NULL,
    PASS VARCHAR(255) NOT NULL,
    GENDER VARCHAR(16) NOT NULL,
    AGE_FEELING VARCHAR(32) NOT NULL,
    CREATED_AT DATETIME NOT NULL,
    UPDATED_AT DATETIME NOT NULL,
    DELETED_AT DATETIME NULL,
    PRIMARY KEY (ID),
    UNIQUE (NAME)
);

CREATE TABLE MUTTERS (
    ID INT NOT NULL AUTO_INCREMENT,
    USER_ID INT NOT NULL,
    TEXT VARCHAR(255) NOT NULL,
    CREATED_AT DATETIME NOT NULL,
    UPDATED_AT DATETIME NOT NULL,
    DELETED_AT DATETIME NULL,
    PRIMARY KEY (ID)
);
