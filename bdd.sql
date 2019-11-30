-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  ven. 29 nov. 2019 à 16:52
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
('durand12', 1, 12000),
('durand12', 2, 2000),
('paul', 1, 4096),
('toto54', 2, 5000);

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
('durand12', 'Durand', 'Jacques', 'abcd'),
('paul', 'Martin', 'Paul', 'abcd'),
('toto54', 'Dupont', 'Jean', 'mdp');

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
(2, 'Coop');

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
