-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  sam. 14 déc. 2019 à 22:22
-- Version du serveur :  10.1.32-MariaDB
-- Version de PHP :  7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `jeu2048`
--

-- --------------------------------------------------------

--
-- Structure de la table `jouer`
--

CREATE TABLE `jouer` (
  `pseudo` varchar(50) COLLATE utf8_bin NOT NULL,
  `idPartie` int(11) NOT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `jouer`
--

INSERT INTO `jouer` (`pseudo`, `idPartie`, `score`) VALUES
('TOTO1', 12, 436),
('TOTO10', 21, 49),
('TOTO11', 22, 34),
('TOTO2', 13, 55),
('TOTO3', 14, 14),
('TOTO4', 15, 67),
('TOTO5', 16, 49),
('TOTO6', 17, 47),
('TOTO7', 18, 85),
('TOTO8', 19, 88),
('TOTO9', 20, 82),
('fulash', 31, 4),
('fulash', 35, 12),
('fulash', 36, 12),
('fulash', 64, 0),
('fulash', 66, 0),
('luc54', 3, 664),
('luc54', 4, 480),
('luc54', 5, 44),
('luc54', 6, 744),
('luc54', 7, 0),
('luc54', 8, 44),
('luc54', 9, 0),
('luc54', 10, 12),
('luc54', 11, 184),
('luc54', 23, 2388),
('luc54', 24, 2388),
('luc54', 25, 340),
('luc54', 26, 340),
('luc54', 27, 340),
('luc54', 28, 340),
('luc54', 29, 8),
('luc54', 30, 8),
('luc54', 32, 8),
('luc54', 33, 16),
('luc54', 34, 16),
('luc54', 37, 332),
('luc54', 38, 52),
('luc54', 39, 52),
('luc54', 40, 52),
('luc54', 41, 52),
('luc54', 42, 52),
('luc54', 43, 0),
('luc54', 44, 8),
('luc54', 45, 4),
('luc54', 46, 0),
('luc54', 47, 0),
('luc54', 48, 0),
('luc54', 49, 0),
('luc54', 50, 0),
('luc54', 51, 0),
('luc54', 52, 0),
('luc54', 53, 0),
('luc54', 54, 0),
('luc54', 55, 0),
('luc54', 56, 0),
('luc54', 57, 0),
('luc54', 58, 1232),
('luc54', 59, 0),
('luc54', 60, 0),
('luc54', 61, 5620),
('luc54', 62, 148),
('luc54', 63, 6012),
('luc54', 65, 128);

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

CREATE TABLE `joueur` (
  `pseudo` varchar(50) COLLATE utf8_bin NOT NULL,
  `nom` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `prenom` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mdp` varchar(256) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `joueur`
--

INSERT INTO `joueur` (`pseudo`, `nom`, `prenom`, `mdp`) VALUES
('TOTO1', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO10', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO11', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO2', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO3', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO4', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO5', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO6', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO7', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO8', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('TOTO9', '', '', 'EF797C8118F02DFB649607DD5D3F8C7623048C9C063D532CC95C5ED7A898A64F'),
('fulash', '', '', 'F3029A66C61B61B41B428963A2FC134154A5383096C776F3B4064733C5463D90'),
('luc54', '', '', 'AA3D2FE4F6D301DBD6B8FB2D2FDDFB7AEEBF3BEC53FFFF4B39A0967AFA88C609');

-- --------------------------------------------------------

--
-- Structure de la table `partie`
--

CREATE TABLE `partie` (
  `idPartie` int(11) NOT NULL,
  `typePartie` varchar(20) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `partie`
--

INSERT INTO `partie` (`idPartie`, `typePartie`) VALUES
(1, 'Coop'),
(2, 'Coop'),
(3, 'Solo'),
(4, 'Solo'),
(5, 'Solo'),
(6, 'Solo'),
(7, 'Solo'),
(8, 'Solo'),
(9, 'Solo'),
(10, 'Solo'),
(11, 'Solo'),
(12, 'Solo'),
(13, 'Solo'),
(14, 'Solo'),
(15, 'Solo'),
(16, 'Solo'),
(17, 'Solo'),
(18, 'Solo'),
(19, 'Solo'),
(20, 'Solo'),
(21, 'Solo'),
(22, 'Solo'),
(23, 'Solo'),
(24, 'Solo'),
(25, 'Solo'),
(26, 'Solo'),
(27, 'Solo'),
(28, 'Solo'),
(29, 'Solo'),
(30, 'Solo'),
(31, 'Solo'),
(32, 'Solo'),
(33, 'Solo'),
(34, 'Solo'),
(35, 'Solo'),
(36, 'Solo'),
(37, 'Solo'),
(38, 'Solo'),
(39, 'Solo'),
(40, 'Solo'),
(41, 'Solo'),
(42, 'Solo'),
(43, 'Solo'),
(44, 'Solo'),
(45, 'Solo'),
(46, 'Solo'),
(47, 'Solo'),
(48, 'Solo'),
(49, 'Solo'),
(50, 'Solo'),
(51, 'Solo'),
(52, 'Solo'),
(53, 'Solo'),
(54, 'Solo'),
(55, 'Solo'),
(56, 'Solo'),
(57, 'Solo'),
(58, 'Solo'),
(59, 'Solo'),
(60, 'Solo'),
(61, 'Solo'),
(62, 'Solo'),
(63, 'Solo'),
(64, 'Solo'),
(65, 'Solo'),
(66, 'Solo');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `jouer`
--
ALTER TABLE `jouer`
  ADD PRIMARY KEY (`pseudo`,`idPartie`),
  ADD KEY `idPartie` (`idPartie`);

--
-- Index pour la table `joueur`
--
ALTER TABLE `joueur`
  ADD PRIMARY KEY (`pseudo`);

--
-- Index pour la table `partie`
--
ALTER TABLE `partie`
  ADD PRIMARY KEY (`idPartie`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `jouer`
--
ALTER TABLE `jouer`
  ADD CONSTRAINT `jouer_ibfk_1` FOREIGN KEY (`pseudo`) REFERENCES `joueur` (`pseudo`),
  ADD CONSTRAINT `jouer_ibfk_2` FOREIGN KEY (`idPartie`) REFERENCES `partie` (`idPartie`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
