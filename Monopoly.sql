-- phpMyAdmin SQL Dump
-- version 4.6.4deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 30, 2017 at 11:51 PM
-- Server version: 5.7.18-0ubuntu0.16.10.1
-- PHP Version: 7.0.15-0ubuntu0.16.10.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Monopoly`
--

-- --------------------------------------------------------

--
-- Table structure for table `Terrain`
--

CREATE TABLE `Terrain` (
  `editable` tinyint(1) NOT NULL,
  `number` int(11) NOT NULL,
  `name` text NOT NULL,
  `color` text NOT NULL,
  `price` int(11) NOT NULL,
  `rent` int(11) NOT NULL,
  `house_price` int(11) NOT NULL,
  `house1` int(11) NOT NULL,
  `house2` int(11) NOT NULL,
  `house3` int(11) NOT NULL,
  `house4` int(11) NOT NULL,
  `house5` int(11) NOT NULL,
  `type` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Terrain`
--

INSERT INTO `Terrain` (`editable`, `number`, `name`, `color`, `price`, `rent`, `house_price`, `house1`, `house2`, `house3`, `house4`, `house5`, `type`) VALUES
(0, 1, 'Go', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'GO'),
(1, 2, 'Boulevard de Belleville', 'BROWN', 6000, 200, 5000, 1000, 3000, 9000, 16000, 25000, 'PROPERTY'),
(0, 3, 'Communauty card', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'CARDS'),
(1, 4, 'Rue Lecourbe', 'BROWN', 6000, 400, 5000, 2000, 6000, 18000, 32000, 45000, 'PROPERTY'),
(1, 5, 'Impots', 'NONE', -1, 20000, 0, -1, -1, -1, -1, -1, 'PUBLIC'),
(1, 6, 'Gare Montparnasse', 'NONE', 20000, -1, 0, -1, -1, -1, -1, -1, 'STATION'),
(1, 7, 'Rue de Vaugirard', 'LIGHT_BLUE', 10000, 600, 5000, 3000, 9000, 27000, 40000, 55000, 'PROPERTY'),
(0, 8, 'Luck card', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'CARDS'),
(1, 9, 'Rue de Courcelles', 'LIGHT_BLUE', 10000, 600, 5000, 3000, 9000, 27000, 45000, 55000, 'PROPERTY'),
(1, 10, 'Avenue de la République', 'LIGHT_BLUE', 12000, 800, 5000, 4000, 10000, 30000, 45000, 60000, 'PROPERTY'),
(0, 11, 'Jail', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'JAIL'),
(1, 12, 'Boulevard de la Vilette', 'PURPLE', 14000, 1000, 10000, 5000, 15000, 45000, 62500, 75000, 'PROPERTY'),
(1, 13, 'Electricite', 'NONE', 15000, -1, 0, -1, -1, -1, -1, -1, 'COMPANY'),
(1, 14, 'Avenue de Neuilly', 'PURPLE', 14000, 1000, 10000, 5000, 15000, 45000, 62500, 75000, 'PROPERTY'),
(1, 15, 'Rue de Paradis', 'PURPLE', 16000, 1200, 10000, 6000, 18000, 50000, 70000, 90000, 'PROPERTY'),
(1, 16, 'Gare Lyon', 'NONE', 20000, -1, 0, -1, -1, -1, -1, -1, 'STATION'),
(1, 17, 'Avenue Mozart', 'ORANGE', 18000, 1400, 10000, 7000, 20000, 55000, 75000, 95000, 'PROPERTY'),
(0, 18, 'Communauty card', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'CARDS'),
(1, 19, 'Boulevard St-Michel', 'ORANGE', 18000, 1400, 10000, 7000, 20000, 55000, 75000, 95000, 'PROPERTY'),
(1, 20, 'Place Pigalle', 'ORANGE', 20000, 1600, 10000, 8000, 22000, 60000, 80000, 100000, 'PROPERTY'),
(0, 21, 'Parking', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'PARKING'),
(1, 22, 'Avenue Matignon', 'RED', 22000, 1800, 15000, 9000, 25000, 70000, 87500, 105000, 'PROPERTY'),
(0, 23, 'Luck card', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'CARDS'),
(1, 24, 'Boulevard Malesherbes', 'RED', 22000, 1800, 15000, 9000, 25000, 70000, 87500, 105000, 'PROPERTY'),
(1, 25, 'Avenue Henri-Martin', 'RED', 24000, 2000, 15000, 10000, 30000, 75000, 92500, 110000, 'PROPERTY'),
(1, 26, 'Gare Nord', 'NONE', 20000, -1, 0, -1, -1, -1, -1, -1, 'STATION'),
(1, 27, 'Foubourg St-Honoré', 'YELLOW', 26000, 2200, 15000, 11000, 33000, 80000, 97500, 115000, 'PROPERTY'),
(1, 28, 'Place de la Bourse', 'YELLOW', 26000, 2200, 15000, 11000, 33000, 80000, 97500, 115000, 'PROPERTY'),
(1, 29, 'Eau', 'NONE', 15000, -1, 0, -1, -1, -1, -1, -1, 'COMPANY'),
(1, 30, 'Rue La Fayette', 'YELLOW', 28000, 2400, 15000, 12000, 36000, 85000, 102500, 120000, 'PROPERTY'),
(0, 31, 'Aller en prison', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'GO_JAIL'),
(1, 32, 'Avenue de Breuteuil', 'GREEN', 30000, 2600, 20000, 13000, 39000, 90000, 110000, 127500, 'PROPERTY'),
(1, 33, 'Avenue Foch', 'GREEN', 30000, 2600, 20000, 13000, 39000, 90000, 110000, 127500, 'PROPERTY'),
(0, 34, 'Communauty card', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'CARDS'),
(1, 35, 'Boulevard des Capucines', 'GREEN', 32000, 2800, 20000, 15000, 45000, 100000, 120000, 140000, 'PROPERTY'),
(1, 36, 'Gare St-Lazare', 'NONE', 20000, -1, 0, -1, -1, -1, -1, -1, 'STATION'),
(0, 37, 'Luck card', 'NONE', -1, -1, 0, -1, -1, -1, -1, -1, 'CARDS'),
(1, 38, 'Avenue des Champs-Elysées', 'DARK_BLUE', 35000, 3500, 20000, 17500, 50000, 110000, 130000, 150000, 'PROPERTY'),
(1, 39, 'Taxe', 'NONE', -1, 10000, 0, -1, -1, -1, -1, -1, 'PUBLIC'),
(1, 40, 'Rue de la Paix', 'DARK_BLUE', 40000, 5000, 20000, 20000, 60000, 140000, 170000, 200000, 'PROPERTY');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `pseudo` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
