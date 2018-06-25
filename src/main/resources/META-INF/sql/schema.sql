DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `CATEGORY_ID` int(11) NOT NULL,
  `ISBN` varchar(10) DEFAULT NULL,
  `TITLE` varchar(500) DEFAULT NULL,
  `PRICE_DECIMAL` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(60) DEFAULT NULL,
  `LAST_NAME` varchar(60) DEFAULT NULL,
  `DESCRIPTION` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `author_book`;
CREATE TABLE `author_book` (
  `AUTHOR_ID` int(11) unsigned NOT NULL,
  `BOOK_ID` int(11) unsigned NOT NULL,
  PRIMARY KEY (`AUTHOR_ID`,`BOOK_ID`)
);





