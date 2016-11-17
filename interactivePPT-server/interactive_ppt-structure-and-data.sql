-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 15, 2016 at 10:05 PM
-- Server version: 5.7.16-0ubuntu0.16.04.1
-- PHP Version: 7.0.8-0ubuntu0.16.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `interactive_ppt`
--

-- --------------------------------------------------------

--
-- Table structure for table `Answers`
--

CREATE TABLE `Answers` (
  `idAnswers` int(11) NOT NULL,
  `answer_text` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `answer_num` int(11) DEFAULT NULL,
  `answer_bool` tinyint(1) DEFAULT NULL,
  `Users_idUser` int(11) NOT NULL,
  `Questions_idQuestions` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Answers`
--

INSERT INTO `Answers` (`idAnswers`, `answer_text`, `answer_num`, `answer_bool`, `Users_idUser`, `Questions_idQuestions`) VALUES
(1, 'Da', NULL, NULL, 1, 1),
(2, 'Da', NULL, NULL, 2, 1),
(3, 'Ne', NULL, NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Log`
--

CREATE TABLE `Log` (
  `Users_idUser` int(11) NOT NULL,
  `action` int(11) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Log`
--

INSERT INTO `Log` (`Users_idUser`, `action`, `datetime`) VALUES
(1, 1, '2016-11-10 03:50:34'),
(1, 1, '2016-11-10 03:51:23'),
(1, 1, '2016-11-10 03:51:36'),
(1, 1, '2016-11-10 03:51:50'),
(1, 1, '2016-11-10 03:52:05'),
(1, 1, '2016-11-10 03:52:19'),
(1, 1, '2016-11-10 03:52:33'),
(1, 1, '2016-11-10 03:53:07');

-- --------------------------------------------------------

--
-- Table structure for table `Options`
--

CREATE TABLE `Options` (
  `idOptions` int(11) NOT NULL,
  `choice_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Options`
--

INSERT INTO `Options` (`idOptions`, `choice_name`) VALUES
(1, 'Opcija 1'),
(2, 'Opcija 2'),
(3, 'Opcija 3');

-- --------------------------------------------------------

--
-- Table structure for table `Questions`
--

CREATE TABLE `Questions` (
  `idQuestions` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `answer_required` tinyint(1) DEFAULT NULL,
  `multiple_answers` tinyint(1) DEFAULT NULL,
  `Question_type_idQuestion_type` int(11) NOT NULL,
  `Survey_idSurvey` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Questions`
--

INSERT INTO `Questions` (`idQuestions`, `name`, `answer_required`, `multiple_answers`, `Question_type_idQuestion_type`, `Survey_idSurvey`) VALUES
(1, 'Pitanje 1', 0, 0, 1, 1),
(2, 'Pitanje 2', 0, 0, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Question_options`
--

CREATE TABLE `Question_options` (
  `idOptions` int(11) NOT NULL,
  `idQuestions` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Question_options`
--

INSERT INTO `Question_options` (`idOptions`, `idQuestions`) VALUES
(1, 1),
(2, 1),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Question_type`
--

CREATE TABLE `Question_type` (
  `idQuestion_type` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Question_type`
--

INSERT INTO `Question_type` (`idQuestion_type`, `name`) VALUES
(1, 'selection'),
(2, 'checkbox'),
(3, 'text');

-- --------------------------------------------------------

--
-- Table structure for table `Role`
--

CREATE TABLE `Role` (
  `idRole` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Role`
--

INSERT INTO `Role` (`idRole`, `name`, `description`) VALUES
(1, 'admin', 'admin aplikacije'),
(2, 'mod', 'moderator aplikacije'),
(3, 'user', 'user aplikacije');

-- --------------------------------------------------------

--
-- Table structure for table `Survey`
--

CREATE TABLE `Survey` (
  `idSurvey` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8 NOT NULL,
  `access_code` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `link_to_presentation` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `author` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Survey`
--

INSERT INTO `Survey` (`idSurvey`, `name`, `description`, `access_code`, `link_to_presentation`, `author`) VALUES
(1, 'Probna anketa', '', 'kod_za_pristup', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Survey_Selection`
--

CREATE TABLE `Survey_Selection` (
  `Survey_idSurvey` int(11) NOT NULL,
  `Users_idUser` int(11) NOT NULL,
  `selected` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Survey_Selection`
--

INSERT INTO `Survey_Selection` (`Survey_idSurvey`, `Users_idUser`, `selected`) VALUES
(1, 1, '2016-11-10 03:50:34'),
(1, 2, '2016-11-10 03:50:34'),
(1, 3, '2016-11-10 03:50:34');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `idUser` int(11) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `surname` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `facebook_id` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `Role_idRole` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`idUser`, `name`, `surname`, `facebook_id`, `address`, `Role_idRole`) VALUES
(1, 'Petar', 'Šestak', 'kontakt', 'http://www.facebook.com/zeko868', 1),
(2, 'Marin', 'Mihajlović', 'kontakt', 'adresa2', 2),
(3, 'Mario', 'Šelek', 'kontakt', 'adresa1', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Answers`
--
ALTER TABLE `Answers`
  ADD PRIMARY KEY (`idAnswers`),
  ADD KEY `fk_Answers_Users1_idx` (`Users_idUser`),
  ADD KEY `fk_Answers_Questions1_idx` (`Questions_idQuestions`);

--
-- Indexes for table `Log`
--
ALTER TABLE `Log`
  ADD KEY `fk_Log_Users1_idx` (`Users_idUser`);

--
-- Indexes for table `Options`
--
ALTER TABLE `Options`
  ADD PRIMARY KEY (`idOptions`);

--
-- Indexes for table `Questions`
--
ALTER TABLE `Questions`
  ADD PRIMARY KEY (`idQuestions`),
  ADD KEY `fk_Questions_Question_type1_idx` (`Question_type_idQuestion_type`),
  ADD KEY `fk_Questions_Survey1_idx` (`Survey_idSurvey`);

--
-- Indexes for table `Question_options`
--
ALTER TABLE `Question_options`
  ADD PRIMARY KEY (`idOptions`,`idQuestions`),
  ADD KEY `fk_Options_has_Questions_Questions1_idx` (`idQuestions`),
  ADD KEY `fk_Options_has_Questions_Options1_idx` (`idOptions`);

--
-- Indexes for table `Question_type`
--
ALTER TABLE `Question_type`
  ADD PRIMARY KEY (`idQuestion_type`);

--
-- Indexes for table `Role`
--
ALTER TABLE `Role`
  ADD PRIMARY KEY (`idRole`);

--
-- Indexes for table `Survey`
--
ALTER TABLE `Survey`
  ADD PRIMARY KEY (`idSurvey`),
  ADD KEY `fk_survey_author` (`author`);

--
-- Indexes for table `Survey_Selection`
--
ALTER TABLE `Survey_Selection`
  ADD PRIMARY KEY (`Survey_idSurvey`,`Users_idUser`),
  ADD KEY `fk_Survey_Selection_Users1_idx` (`Users_idUser`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`idUser`),
  ADD KEY `fk_Users_Role1_idx` (`Role_idRole`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Answers`
--
ALTER TABLE `Answers`
  MODIFY `idAnswers` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `Options`
--
ALTER TABLE `Options`
  MODIFY `idOptions` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `Questions`
--
ALTER TABLE `Questions`
  MODIFY `idQuestions` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `Question_type`
--
ALTER TABLE `Question_type`
  MODIFY `idQuestion_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `Role`
--
ALTER TABLE `Role`
  MODIFY `idRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `Survey`
--
ALTER TABLE `Survey`
  MODIFY `idSurvey` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Answers`
--
ALTER TABLE `Answers`
  ADD CONSTRAINT `fk_Answers_Questions1` FOREIGN KEY (`Questions_idQuestions`) REFERENCES `Questions` (`idQuestions`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Answers_Users1` FOREIGN KEY (`Users_idUser`) REFERENCES `Users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Log`
--
ALTER TABLE `Log`
  ADD CONSTRAINT `fk_Log_Users1` FOREIGN KEY (`Users_idUser`) REFERENCES `Users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Questions`
--
ALTER TABLE `Questions`
  ADD CONSTRAINT `fk_Questions_Question_type1` FOREIGN KEY (`Question_type_idQuestion_type`) REFERENCES `Question_type` (`idQuestion_type`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Questions_Survey1` FOREIGN KEY (`Survey_idSurvey`) REFERENCES `Survey` (`idSurvey`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Question_options`
--
ALTER TABLE `Question_options`
  ADD CONSTRAINT `fk_Options_has_Questions_Options1` FOREIGN KEY (`idOptions`) REFERENCES `Options` (`idOptions`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Options_has_Questions_Questions1` FOREIGN KEY (`idQuestions`) REFERENCES `Questions` (`idQuestions`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Survey`
--
ALTER TABLE `Survey`
  ADD CONSTRAINT `fk_survey_author` FOREIGN KEY (`author`) REFERENCES `Users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Survey_Selection`
--
ALTER TABLE `Survey_Selection`
  ADD CONSTRAINT `fk_Survey_Selection_Survey` FOREIGN KEY (`Survey_idSurvey`) REFERENCES `Survey` (`idSurvey`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Survey_Selection_Users1` FOREIGN KEY (`Users_idUser`) REFERENCES `Users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Users`
--
ALTER TABLE `Users`
  ADD CONSTRAINT `fk_Users_Role1` FOREIGN KEY (`Role_idRole`) REFERENCES `Role` (`idRole`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
