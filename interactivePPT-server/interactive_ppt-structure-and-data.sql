-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 31, 2017 at 07:50 PM
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

DELIMITER $$
--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `createOptionAndGetId` (`name` VARCHAR(100)) RETURNS INT(11) BEGIN
DECLARE returnValue int(11);
SET returnValue := (SELECT idOptions FROM Options WHERE choice_name = name LIMIT 1);
IF returnValue IS NULL THEN
    INSERT IGNORE INTO Options VALUES (default, name);
    SET returnValue := LAST_INSERT_ID();
END IF;
RETURN returnValue;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `Answers`
--

CREATE TABLE `Answers` (
  `idAnswer` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  `idOption` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Answers`
--

INSERT INTO `Answers` (`idAnswer`, `idUser`, `idQuestion`, `idOption`) VALUES
(9, 1, 15, 2),
(10, 1, 15, 3),
(11, 1, 14, 1),
(12, 1, 47, 63),
(13, 1, 14, 1),
(14, 1, 47, 64),
(15, 1, 14, 1),
(16, 1, 15, 2),
(17, 1, 15, 3),
(18, 1, 47, 65),
(19, 1, 14, 1),
(20, 1, 15, 2),
(21, 1, 15, 3),
(22, 1, 47, 65),
(23, 1, 14, 1),
(24, 1, 15, 2),
(25, 1, 15, 3),
(26, 1, 47, 65),
(27, 1, 15, 2),
(28, 1, 15, 3),
(29, 1, 14, 1),
(30, 1, 47, 66),
(31, 1, 14, 1),
(32, 1, 15, 2),
(33, 1, 15, 3),
(34, 1, 47, 67),
(35, 1, 14, 1),
(36, 1, 15, 2),
(37, 1, 15, 3),
(38, 1, 47, 67),
(39, 1, 14, 3),
(40, 1, 15, 2),
(41, 1, 15, 3),
(42, 1, 47, 67),
(43, 1, 14, 2),
(44, 1, 15, 2),
(45, 1, 15, 3),
(46, 1, 47, 68),
(47, 1, 14, 2),
(48, 1, 15, 2),
(49, 1, 47, 68),
(50, 1, 15, 1),
(51, 1, 10, 23),
(52, 1, 10, 28),
(53, 1, 11, 30),
(54, 1, 13, 22),
(55, 1, 6, 12),
(56, 1, 7, 15),
(57, 1, 8, 17),
(58, 1, 9, 21),
(59, 1, 10, 23),
(60, 1, 10, 28),
(61, 1, 11, 30),
(62, 1, 13, 22),
(63, 1, 6, 12),
(64, 1, 7, 15),
(65, 1, 8, 17),
(66, 1, 12, 69),
(69, 1, 9, 21),
(70, 1, 10, 24),
(71, 1, 10, 27),
(72, 1, 11, 31),
(73, 1, 6, 11),
(74, 1, 7, 15),
(75, 1, 8, 18),
(76, 1, 9, 21),
(176, 1, 52, 78),
(177, 1, 52, 79),
(178, 1, 52, 80),
(179, 1, 52, 82),
(180, 1, 53, 84),
(181, 1, 54, 90),
(182, 1, 1, 2),
(183, 1, 3, 5),
(184, 1, 4, 7),
(185, 1, 5, 8),
(186, 2, 14, 1),
(187, 2, 15, 2),
(188, 2, 15, 3),
(189, 2, 47, 91),
(190, 2, 31, 92),
(191, 2, 55, 40),
(192, 2, 56, 93),
(193, 2, 56, 94),
(194, 2, 56, 95),
(195, 1, 60, 40),
(196, 1, 61, 40),
(197, 1, 62, 40);

-- --------------------------------------------------------

--
-- Table structure for table `Default_question_options`
--

CREATE TABLE `Default_question_options` (
  `idQuestion` int(11) NOT NULL,
  `idOption` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Default_question_options`
--

INSERT INTO `Default_question_options` (`idQuestion`, `idOption`) VALUES
(1, 1),
(14, 1),
(15, 1),
(1, 2),
(14, 2),
(15, 2),
(1, 3),
(14, 3),
(15, 3),
(3, 4),
(5, 4),
(3, 5),
(4, 6),
(4, 7),
(2, 8),
(5, 8),
(2, 9),
(5, 9),
(6, 10),
(6, 11),
(6, 12),
(6, 13),
(6, 14),
(7, 15),
(7, 16),
(8, 17),
(8, 18),
(8, 19),
(8, 20),
(9, 21),
(13, 21),
(9, 22),
(13, 22),
(10, 23),
(10, 24),
(10, 25),
(10, 26),
(10, 27),
(10, 28),
(11, 29),
(11, 30),
(11, 31),
(11, 32),
(11, 33),
(43, 34),
(5, 35),
(45, 35),
(5, 36),
(45, 36),
(45, 37),
(43, 40),
(55, 40),
(60, 40),
(61, 40),
(43, 41),
(55, 41),
(60, 41),
(61, 41),
(37, 52),
(37, 53),
(38, 54),
(38, 55),
(38, 56),
(38, 57),
(42, 60),
(42, 61),
(49, 72),
(49, 73),
(50, 74),
(50, 75),
(51, 76),
(51, 77),
(52, 78),
(52, 79),
(52, 80),
(52, 81),
(52, 82),
(53, 83),
(53, 84),
(57, 96),
(57, 97),
(58, 98),
(58, 99),
(58, 100),
(58, 101),
(59, 102),
(59, 103),
(59, 104),
(59, 105),
(60, 107);

-- --------------------------------------------------------

--
-- Stand-in structure for view `getPresentationDetails`
--
CREATE TABLE `getPresentationDetails` (
`access_code` varchar(10)
,`presentation_details` text
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `getQuestionDetails`
--
CREATE TABLE `getQuestionDetails` (
`idSurvey` int(11)
,`question_details` text
);

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
  `choice_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Options`
--

INSERT INTO `Options` (`idOptions`, `choice_name`) VALUES
(1, 'Opcija 1'),
(2, 'Opcija 2'),
(3, 'Opcija 3'),
(4, 'vrganj'),
(5, 'zelena pupavka'),
(6, 'vrganji s jajci'),
(7, 'zagorska juha'),
(8, 'sumska pecurka'),
(9, 'lisicka'),
(10, '16 - 17'),
(11, '17 - 19'),
(12, '20 - 21'),
(13, '22 - 23'),
(14, 'vise od 23'),
(15, 'M'),
(16, 'Z'),
(17, 'student'),
(18, 'zaposlen'),
(19, 'nezaposlen'),
(20, 'umirovljen'),
(21, 'Da'),
(22, 'Ne'),
(23, 'RPG'),
(24, 'MMORPG'),
(25, 'RTS'),
(26, 'simulacija'),
(27, 'MOBA'),
(28, 'FPS'),
(29, '< 2 sata'),
(30, '2 - 4'),
(31, '4 - 6'),
(32, '6 - 10'),
(33, '10 - vise'),
(34, 'mozda'),
(35, '1'),
(36, '2'),
(37, '3'),
(38, 'a'),
(39, 'b'),
(40, 'da'),
(41, 'ne'),
(50, 'nedodijeljeni'),
(51, 'sfsdfsdf'),
(52, 'muško'),
(53, 'žensko'),
(54, 'direktan pristup'),
(55, 'putem web-aplikacija'),
(56, 'putem e-maila'),
(57, 'putem telefona'),
(60, 'true'),
(61, 'false'),
(62, ''),
(63, 'dasda'),
(64, 'dsadafgfdgd'),
(65, 'njkn'),
(66, 'dsfsdfs'),
(67, 'dsada'),
(68, 'asdasd'),
(69, 'jer su fancys'),
(72, 'opcijaaaaaaaaaaaaa 1'),
(73, 'opcijbbbbbbbbbbbbb 2'),
(74, 'x'),
(75, 'y'),
(76, 'no, John Cena'),
(77, 'yees ^_^'),
(78, 'goto'),
(79, 'return'),
(80, 'break'),
(81, 'case'),
(82, 'continue'),
(83, 'točno'),
(84, 'netočno'),
(87, 'opšn'),
(90, 'iomanip'),
(91, 'obavezno'),
(92, 'odgovor'),
(93, 'prvi1'),
(94, 'prvi2'),
(95, 'prvi3'),
(96, 'opcija 1'),
(97, 'opcija 2'),
(98, 'bambus'),
(99, 'bambuča'),
(100, 'mrkva'),
(101, 'jabuke'),
(102, 'Cheng Shi'),
(103, 'Miao Miao'),
(104, 'Bao Bao'),
(105, 'Lei Ming'),
(106, 'krastavac'),
(107, 'ne vidim nista');

-- --------------------------------------------------------

--
-- Table structure for table `Presentation`
--

CREATE TABLE `Presentation` (
  `id` int(11) NOT NULL,
  `path` varchar(255) NOT NULL,
  `access_code` varchar(10) NOT NULL,
  `author` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Presentation`
--

INSERT INTO `Presentation` (`id`, `path`, `access_code`, `author`) VALUES
(3, 'ppt/nepoznato.ppt', 'd9b4vs69v2', 2),
(4, 'ppt/Petar Šestak-Programiranje u skriptnim programskim jezicima-1.pptx', 'rpk_anketa', 1),
(5, 'ppt/Proizvodnja vina.pptx', '7dsR6n2n', 1),
(7, 'ppt/Varijable i pokazivači.pptx', 'm45k0fz42t', 1),
(10, 'ppt/test.pptx', 'j173hbvuos', 2),
(15, 'ppt/nova.pptx', '14imt3221c', 2),
(17, 'ppt/Poslovni plan za poduzeće CroIoT-final.pptx', '4rl0dn7a68', 10),
(18, 'ppt/Youtube.pptx', 'n0879n11j3', 1),
(19, 'ppt/blank_ppt.pptx', '69up787n13', 1),
(20, 'ppt/nova.pptx', '33g4y723t4', 2),
(21, 'ppt/LN01-Introduction.ppt', 'knv58t744c', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Questions`
--

CREATE TABLE `Questions` (
  `idQuestions` int(11) NOT NULL,
  `name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `answer_required` tinyint(1) DEFAULT NULL,
  `Question_type_idQuestion_type` int(11) NOT NULL,
  `Survey_idSurvey` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Questions`
--

INSERT INTO `Questions` (`idQuestions`, `name`, `answer_required`, `Question_type_idQuestion_type`, `Survey_idSurvey`) VALUES
(1, 'Pitanje 1', 0, 1, 1),
(2, 'Pitanje 2', 0, 2, 1),
(3, 'prepoznajete li gljivu sa slike', 0, 1, 2),
(4, 'vase omiljeno jelo od gljiva', 0, 1, 2),
(5, 'od gljiva sakupljam sljedece', 0, 2, 2),
(6, 'Koliko imate godina:', 0, 1, 3),
(7, 'Oznacite spol:', 0, 1, 3),
(8, 'Status zaposlenja:', 0, 1, 3),
(9, 'Igrate li racunalne igre:', 1, 1, 3),
(10, 'Koji tip igara najcesce igrate:', 0, 2, 3),
(11, 'Koliko vremena tjedno provodite igrajuci:', 0, 1, 3),
(12, 'Zasto igrate racunalne igre', 0, 3, 3),
(13, 'Smatrate li da ste ovisni o racunalnim igrama:', 0, 1, 3),
(14, 'Prvo pitanje', 0, 1, 4),
(15, 'Drugo pitanje', 0, 2, 4),
(28, 'tekstualno', 0, 3, 25),
(29, 'aaa', 0, 1, 26),
(30, 'qqq', 0, 2, 26),
(31, 'tekstualno pitanje', 0, 3, 26),
(32, 'single', 0, 1, 27),
(33, 'textedit', 0, 3, 27),
(34, 'multiple', 0, 2, 27),
(37, 'Vaš spol:', 0, 1, 29),
(38, 'Način na koji vršite anketiranje:', 0, 2, 29),
(39, 'Pozdravna poruka kojom najčešće počinju vaše ankete:', 0, 3, 29),
(42, 'pitanje s 2 odgovora', 0, 1, 32),
(43, 'radio box', 0, 1, 33),
(44, 'text edit', 0, 3, 33),
(45, 'radio box 2', 0, 1, 33),
(46, 'kak se zoveš?', 0, 3, 34),
(47, 'textedit pitanje', 1, 3, 4),
(49, 'pitaaaaaaanjeee 1', 0, 1, 36),
(50, 'bljablja', 1, 1, 37),
(51, 'can you see me?', 1, 1, 38),
(52, 'Označite naredbe iz skupine skokova:', 0, 2, 39),
(53, 'Rekurzivni algoritmi su manje realne vremenske složenosti u odnosu na iterativne', 0, 1, 39),
(54, 'Navedite naziv neke biblioteke u C++', 0, 3, 39),
(55, 'prvo', 1, 1, 40),
(56, 'drugo', 0, 3, 40),
(57, 'pitanje 1', 0, 1, 41),
(58, 'koje jelo vole pande?', 1, 2, 42),
(59, 'kak je zove siva panda?', 0, 1, 42),
(60, 'dal je ovo klasican checkbox group?', 1, 2, 43),
(61, 'dal je ovo klasican radiobutton group?', 1, 1, 43),
(62, 'dal je ovo klasicno pitanje s nadopunjavanjem?', 1, 3, 43);

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
(3, 1),
(8, 2),
(9, 2),
(106, 2),
(4, 3),
(5, 3),
(6, 4),
(7, 4),
(4, 5),
(8, 5),
(9, 5),
(35, 5),
(36, 5),
(10, 6),
(11, 6),
(12, 6),
(13, 6),
(14, 6),
(15, 7),
(16, 7),
(17, 8),
(18, 8),
(19, 8),
(20, 8),
(21, 9),
(22, 9),
(23, 10),
(24, 10),
(25, 10),
(26, 10),
(27, 10),
(28, 10),
(29, 11),
(30, 11),
(31, 11),
(32, 11),
(33, 11),
(21, 13),
(22, 13),
(1, 14),
(2, 14),
(3, 14),
(1, 15),
(2, 15),
(3, 15),
(52, 37),
(53, 37),
(54, 38),
(55, 38),
(56, 38),
(57, 38),
(60, 42),
(61, 42),
(34, 43),
(40, 43),
(41, 43),
(62, 44),
(35, 45),
(36, 45),
(37, 45),
(72, 49),
(73, 49),
(74, 50),
(75, 50),
(76, 51),
(77, 51),
(78, 52),
(79, 52),
(80, 52),
(81, 52),
(82, 52),
(83, 53),
(84, 53),
(40, 55),
(41, 55),
(96, 57),
(97, 57),
(98, 58),
(99, 58),
(100, 58),
(101, 58),
(102, 59),
(103, 59),
(104, 59),
(105, 59),
(40, 60),
(41, 60),
(107, 60),
(40, 61),
(41, 61);

-- --------------------------------------------------------

--
-- Table structure for table `Question_type`
--

CREATE TABLE `Question_type` (
  `idQuestion_type` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `mode` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Question_type`
--

INSERT INTO `Question_type` (`idQuestion_type`, `name`, `mode`) VALUES
(1, 'selection', 1),
(2, 'checkbox', 1),
(3, 'text', 2);

--
-- Triggers `Question_type`
--
DELIMITER $$
CREATE TRIGGER `question_type_mode_range` BEFORE UPDATE ON `Question_type` FOR EACH ROW BEGIN DECLARE msg varchar(255);
     IF (NOT NEW.mode IN (1,2,3))
     THEN
        SET msg = concat('Logička pogreška: mode može imati vrijednost 1, 2 ili 3. Vi ste pokušali staviti ', cast(new.mode as char));
        SIGNAL sqlstate '45000' SET message_text = msg;
     END IF; 
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `question_type_mode_range_insert` BEFORE INSERT ON `Question_type` FOR EACH ROW BEGIN DECLARE msg varchar(255);
     IF (NOT NEW.mode IN (1,2,3))
     THEN
        SET msg = concat('Logička pogreška: mode može imati vrijednost 1, 2 ili 3. Vi ste pokušali staviti ', cast(new.mode as char));
        SIGNAL sqlstate '45000' SET message_text = msg;
     END IF; 
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `Reply_request`
--

CREATE TABLE `Reply_request` (
  `user` int(11) NOT NULL,
  `presentation` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Reply_request`
--

INSERT INTO `Reply_request` (`user`, `presentation`, `time`) VALUES
(1, 3, '2017-01-05 18:02:57'),
(1, 4, '2017-01-09 13:20:17'),
(1, 5, '2017-01-09 13:00:54'),
(2, 10, '2017-01-31 15:47:10'),
(10, 4, '2017-01-07 19:01:22');

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
-- Table structure for table `Subscription`
--

CREATE TABLE `Subscription` (
  `idUser` int(11) NOT NULL,
  `idPresentation` int(11) NOT NULL,
  `active` varchar(3) NOT NULL DEFAULT 'yes'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Subscription`
--

INSERT INTO `Subscription` (`idUser`, `idPresentation`, `active`) VALUES
(1, 3, 'yes'),
(1, 4, 'yes'),
(1, 5, 'no'),
(1, 7, 'no'),
(1, 10, 'no'),
(2, 3, 'yes'),
(2, 10, 'yes'),
(2, 15, 'yes'),
(10, 4, 'no');

-- --------------------------------------------------------

--
-- Table structure for table `Survey`
--

CREATE TABLE `Survey` (
  `idSurvey` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8 NOT NULL,
  `access_code` varchar(10) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Survey`
--

INSERT INTO `Survey` (`idSurvey`, `name`, `description`, `access_code`) VALUES
(1, 'Probna anketa', '', 'rpk_anketa'),
(2, 'HerbariumApp', 'Anketa o poznavanju biljnih vrsta', 'rpk_anketa'),
(3, 'Upitnik o ovisnosti o racunalnim igrama', 'Molimo Vas da odgovorite na ovu anketu u kojoj se ispituje ovisnost ljudi o racunalnim igrama:', 'rpk_anketa'),
(4, 'Moja prva anketa', 'Ovo je moja prva anketa potrebna za testiranje', 'd9b4vs69v2'),
(5, '30112016', 'dada', '7dsR6n2n'),
(25, 'Testna anketa', 'Ovo je anketa koja se kreira kao test za dodavanje ankete', 'd9b4vs69v2'),
(26, 'treca anketa', 'Ovo je moja treca anketa', 'd9b4vs69v2'),
(27, '03122016', 'wsfsdgsdgds', '7dsR6n2n'),
(29, 'Anketa o anketama', 'Ovo je anketa napravljena za potrebu projekta na kolegiju Računalom posredovana komunikacija', '7dsR6n2n'),
(32, 'anketa s jednim pitanjem', 'dasdasd', '7dsR6n2n'),
(33, 'Test', 'Anketa za test radio boxa i text edita', 'd9b4vs69v2'),
(34, 'anketa s text edit pitanjem', 'fsdfdafsf', 'rpk_anketa'),
(36, 'neka anketa s jako dugackim naslovom tstirati dal šljaka app u landscape orijentaciji', 'neka anketa s jaaako dugackim nasloovoooom koji je tak dugi da bi trebal testirati dal šljaka app u landscape orijentaciji (nešto nešto nešto nešto nešto nešto)', 'rpk_anketa'),
(37, '27122016', 'testna anketica', 'rpk_anketa'),
(38, 'dfsdfdsfs', '\\\\xF0\\\\x9F\\\\x90\\\\xBC', 'rpk_anketa'),
(39, 'Provjera znanja iz Programiranja', 'Ovo je kratka provjera znanja iz programiranja - i provjera ispravnosti aplikacije', 'm45k0fz42t'),
(40, 'tekst test', 'ovo je tekst test', '33g4y723t4'),
(41, 'anketa naknadno dodana', 'yeeeee', 'rpk_anketa'),
(42, 'pandologija', 'anketa o pandama', 'm45k0fz42t'),
(43, 'Anketa za ispitivanje modularnosti', 'Ova anketa je napravljena kako bi se ispitali aspekti modularnosti kod kreiranja i pregleda anketa', 'knv58t744c');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `idUser` int(11) NOT NULL,
  `name` varchar(90) CHARACTER SET utf8 NOT NULL,
  `app_uid` varchar(45) CHARACTER SET utf8 NOT NULL,
  `Role_idRole` int(11) NOT NULL,
  `token` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`idUser`, `name`, `app_uid`, `Role_idRole`, `token`) VALUES
(1, 'Petar Šestak', '1431307750213523', 1, 'favpD4g2lYs:APA91bEMX8jcFgyUWeLNVh-mXBNHuxAVQ88ZKiwQh7sQKifUK2Vf7qs9x7XvU7EL_l2WMsddv_VnNmTJw4OCMw4KYepGHnYodOsGUWjmbIv92naV4LtGQrIdO_l6mxqGmge6aRQ8khiU'),
(2, 'Marin Mihajlovic', '10210532062074946', 2, 'cqoFmicQg9g:APA91bEHnRCf4LqNhRg3VEAHuec11DU8LKt-nStsotdh8IDp9x2LKWPMJL3a6WoKdehvvUhZouzRKAg9HM6wEwQWRQBctBTf4UkCtaadZHBNdUz4eq07spo5DdhSl_YtNmDk88N6rAih'),
(8, 'Marinela Levak', '1336022606431703', 3, ''),
(10, 'Mario Šelek', '1256649427742897', 3, 'ed2L3DqVgfY:APA91bHfonVwB2Hnk7fe-MT4MaUHSiz5xXExFL3LaIDOaPt2fah91hIpD-EpuauXdQGWFrFCHR8ZSxY_xeBV1sYko1KGZ7Rh7tZ3nFi-P8igKlDatvL1I8REaBH3CKA1SUc_UEei4LQ7');

-- --------------------------------------------------------

--
-- Structure for view `getPresentationDetails`
--
DROP TABLE IF EXISTS `getPresentationDetails`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `getPresentationDetails`  AS  select `p`.`access_code` AS `access_code`,concat('{"path":"',`p`.`path`,'","author_name":"',`u`.`name`,'","surveys":[',ifnull(group_concat(concat('{"id":',`s`.`idSurvey`,',"name":"',`s`.`name`,'"}') separator ','),''),']}') AS `presentation_details` from ((`Presentation` `p` left join `Users` `u` on((`p`.`author` = `u`.`idUser`))) left join `Survey` `s` on((`p`.`access_code` = `s`.`access_code`))) group by `p`.`id` order by `p`.`access_code` ;

-- --------------------------------------------------------

--
-- Structure for view `getQuestionDetails`
--
DROP TABLE IF EXISTS `getQuestionDetails`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `getQuestionDetails`  AS  select `result`.`idSurvey` AS `idSurvey`,`result`.`question_details` AS `question_details` from (select `s`.`idSurvey` AS `idSurvey`,concat('{"id":',`q`.`idQuestions`,',"name":"',`q`.`name`,'","type":',`q`.`Question_type_idQuestion_type`,', "required_answer":',`q`.`answer_required`,concat(',"options":[',group_concat(concat('{"id":',`o`.`idOptions`,',"name":"',`o`.`choice_name`,'"}') order by `o`.`idOptions` ASC separator ','),']'),'}') AS `question_details` from ((((`Questions` `q` join `Default_question_options` `dqo` on((`q`.`idQuestions` = `dqo`.`idQuestion`))) join `Options` `o` on((`dqo`.`idOption` = `o`.`idOptions`))) join `Survey` `s` on((`s`.`idSurvey` = `q`.`Survey_idSurvey`))) join `Question_type` `qt` on((`q`.`Question_type_idQuestion_type` = `qt`.`idQuestion_type`))) where (`qt`.`mode` = 1) group by `q`.`idQuestions` union select `s`.`idSurvey` AS `idSurvey`,concat('{"id":',`q`.`idQuestions`,',"name":"',`q`.`name`,'", "type":',`q`.`Question_type_idQuestion_type`,', "required_answer":',`q`.`answer_required`,concat(',"options":[',group_concat(concat('{"id":',(case when exists(select 1 from `Default_question_options` `dqo` where ((`dqo`.`idQuestion` = `q`.`idQuestions`) and (`dqo`.`idOption` = `o`.`idOptions`))) then `o`.`idOptions` else 0 end),',"name":"',`o`.`choice_name`,'"}') order by `o`.`idOptions` ASC separator ','),']'),'}') AS `question_details` from ((((`Questions` `q` join `Question_options` `qo` on((`q`.`idQuestions` = `qo`.`idQuestions`))) join `Options` `o` on((`qo`.`idOptions` = `o`.`idOptions`))) join `Survey` `s` on((`s`.`idSurvey` = `q`.`Survey_idSurvey`))) join `Question_type` `qt` on((`q`.`Question_type_idQuestion_type` = `qt`.`idQuestion_type`))) where (`qt`.`mode` = 3) group by `q`.`idQuestions` union select `s`.`idSurvey` AS `idSurvey`,concat('{"id":',`q`.`idQuestions`,',"name":"',`q`.`name`,'", "type":',`q`.`Question_type_idQuestion_type`,', "required_answer":',`q`.`answer_required`,',"options":[]}') AS `question_details` from ((`Questions` `q` join `Survey` `s` on((`s`.`idSurvey` = `q`.`Survey_idSurvey`))) join `Question_type` `qt` on((`q`.`Question_type_idQuestion_type` = `qt`.`idQuestion_type`))) where (`qt`.`mode` = 2) group by `q`.`idQuestions`) `result` order by `result`.`idSurvey`,`result`.`question_details` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Answers`
--
ALTER TABLE `Answers`
  ADD PRIMARY KEY (`idAnswer`),
  ADD KEY `fk_Answers_Users1_idx` (`idUser`),
  ADD KEY `fk_Answers_Questions1_idx` (`idOption`),
  ADD KEY `idQuestion` (`idQuestion`);

--
-- Indexes for table `Default_question_options`
--
ALTER TABLE `Default_question_options`
  ADD PRIMARY KEY (`idQuestion`,`idOption`),
  ADD KEY `fk_dqo_option` (`idOption`);

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
ALTER TABLE `Options` ADD FULLTEXT KEY `choice_name` (`choice_name`);

--
-- Indexes for table `Presentation`
--
ALTER TABLE `Presentation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `access_code` (`access_code`),
  ADD KEY `fk_Ppt_User` (`author`);

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
-- Indexes for table `Reply_request`
--
ALTER TABLE `Reply_request`
  ADD PRIMARY KEY (`user`,`presentation`),
  ADD KEY `presentation` (`presentation`);

--
-- Indexes for table `Role`
--
ALTER TABLE `Role`
  ADD PRIMARY KEY (`idRole`);

--
-- Indexes for table `Subscription`
--
ALTER TABLE `Subscription`
  ADD PRIMARY KEY (`idUser`,`idPresentation`),
  ADD KEY `fk_Subscription_Ppt` (`idPresentation`);

--
-- Indexes for table `Survey`
--
ALTER TABLE `Survey`
  ADD PRIMARY KEY (`idSurvey`),
  ADD KEY `fk_survey_ppt` (`access_code`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `app_uid_2` (`app_uid`),
  ADD KEY `fk_Users_Role1_idx` (`Role_idRole`),
  ADD KEY `app_uid` (`app_uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Answers`
--
ALTER TABLE `Answers`
  MODIFY `idAnswer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=198;
--
-- AUTO_INCREMENT for table `Options`
--
ALTER TABLE `Options`
  MODIFY `idOptions` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;
--
-- AUTO_INCREMENT for table `Presentation`
--
ALTER TABLE `Presentation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `Questions`
--
ALTER TABLE `Questions`
  MODIFY `idQuestions` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
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
  MODIFY `idSurvey` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Answers`
--
ALTER TABLE `Answers`
  ADD CONSTRAINT `Answers_ibfk_1` FOREIGN KEY (`idQuestion`) REFERENCES `Questions` (`idQuestions`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Answers_Options1` FOREIGN KEY (`idOption`) REFERENCES `Options` (`idOptions`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Answers_Users1` FOREIGN KEY (`idUser`) REFERENCES `Users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Default_question_options`
--
ALTER TABLE `Default_question_options`
  ADD CONSTRAINT `Default_question_options_ibfk_1` FOREIGN KEY (`idQuestion`) REFERENCES `Questions` (`idQuestions`),
  ADD CONSTRAINT `Default_question_options_ibfk_2` FOREIGN KEY (`idOption`) REFERENCES `Options` (`idOptions`);

--
-- Constraints for table `Log`
--
ALTER TABLE `Log`
  ADD CONSTRAINT `fk_Log_Users1` FOREIGN KEY (`Users_idUser`) REFERENCES `Users` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Presentation`
--
ALTER TABLE `Presentation`
  ADD CONSTRAINT `Presentation_ibfk_1` FOREIGN KEY (`author`) REFERENCES `Users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

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
-- Constraints for table `Reply_request`
--
ALTER TABLE `Reply_request`
  ADD CONSTRAINT `Reply_request_ibfk_1` FOREIGN KEY (`user`) REFERENCES `Users` (`idUser`),
  ADD CONSTRAINT `Reply_request_ibfk_2` FOREIGN KEY (`presentation`) REFERENCES `Presentation` (`id`);

--
-- Constraints for table `Subscription`
--
ALTER TABLE `Subscription`
  ADD CONSTRAINT `fk_Subscription_Ppt` FOREIGN KEY (`idPresentation`) REFERENCES `Presentation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Subscription_User` FOREIGN KEY (`idUser`) REFERENCES `Users` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Survey`
--
ALTER TABLE `Survey`
  ADD CONSTRAINT `Survey_ibfk_1` FOREIGN KEY (`access_code`) REFERENCES `Presentation` (`access_code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Users`
--
ALTER TABLE `Users`
  ADD CONSTRAINT `fk_Users_Role1` FOREIGN KEY (`Role_idRole`) REFERENCES `Role` (`idRole`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
