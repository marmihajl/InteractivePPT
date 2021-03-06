-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 06, 2017 at 09:57 AM
-- Server version: 5.7.17-0ubuntu0.16.04.1
-- PHP Version: 7.0.15-0ubuntu0.16.04.1

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
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_question_type` (IN `IdNum` INT UNSIGNED)  BEGIN
	DECLARE rows_num int;
    DECLARE msg varchar(255);
    DECLARE exit handler for sqlstate '45000'
  	BEGIN
	    GET DIAGNOSTICS CONDITION 1
    	msg = MESSAGE_TEXT;
    	SIGNAL sqlstate '45000' SET message_text = msg;
  	END;
	DECLARE exit handler for sqlexception
  	BEGIN
		ROLLBACK;
	END;
        
	DECLARE exit handler for sqlwarning
	BEGIN
	 	ROLLBACK;
	END;
    
    SET rows_num = (SELECT count(*) FROM Question_type);
    IF IdNum>rows_num THEN
    	SIGNAL sqlstate '45000' SET message_text = 'Question type with given Id does not exist!';
	ELSEIF IdNum IS NOT NULL THEN
		START TRANSACTION;
			SET @from_procedure_call = 1;
    		DELETE FROM Question_type WHERE idQuestion_type=IdNum;
    		UPDATE Question_type SET idQuestion_type=idQuestion_type-1 WHERE idQuestion_type>IdNum;
        	SET @from_procedure_call = NULL;
        COMMIT;
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_question_type` (IN `id` INT UNSIGNED, IN `name` VARCHAR(45) CHARSET utf8, IN `mode` TINYINT)  NO SQL
BEGIN
    DECLARE num_to_change INT;
    DECLARE msg varchar(255);
    
    DECLARE exit handler for sqlstate '45000'
  	BEGIN
	    GET DIAGNOSTICS CONDITION 1
    	msg = MESSAGE_TEXT;
    	SIGNAL sqlstate '45000' SET message_text = msg;
  	END;
    DECLARE exit handler for sqlexception
  	BEGIN
		ROLLBACK;
	END;
        
	DECLARE exit handler for sqlwarning
	BEGIN
	 	ROLLBACK;
	END;

	IF id IS NOT NULL AND id<>0 THEN

            SET num_to_change = (SELECT count(*) FROM Question_type);
            IF id >= num_to_change+1 THEN
            	INSERT INTO Question_type VALUES (id, name, mode);
            ELSE
            	START TRANSACTION;
					SET @from_procedure_call = 1;
		            WHILE num_to_change>=id DO
	    		        UPDATE Question_type SET idQuestion_type=idQuestion_type+1 WHERE idQuestion_type=num_to_change;
            	        SET num_to_change = num_to_change-1;
                	END WHILE;
        	    	INSERT INTO Question_type VALUES (id, name, mode);
            		SET @from_procedure_call = NULL;
				COMMIT;
            END IF;
    ELSE
    	INSERT INTO Question_type VALUES (0, name, mode);
	END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `swap_question_types` (IN `id1` INT, IN `id2` INT)  NO SQL
BEGIN
	DECLARE rows_num int;
    DECLARE msg varchar(255);

	DECLARE exit handler for sqlstate '45000'
  	BEGIN
	    GET DIAGNOSTICS CONDITION 1
    	msg = MESSAGE_TEXT;
    	SIGNAL sqlstate '45000' SET message_text = msg;
  	END;

	DECLARE exit handler for sqlexception
  	BEGIN
		ROLLBACK;
	END;
        
	DECLARE exit handler for sqlwarning
	BEGIN
	 	ROLLBACK;
	END;

    SET rows_num = (SELECT count(*) FROM Question_type);
    IF id1>rows_num OR id2>rows_num THEN
    	SIGNAL sqlstate '45000' SET message_text = 'Question type with given Id does not exist!';
	ELSEIF id1 IS NOT NULL AND id2 IS NOT NULL THEN
		START TRANSACTION;
    	SET @from_procedure_call = 1;
    	UPDATE Question_type SET idQuestion_type=-1 WHERE idQuestion_type=id1;
    	UPDATE Question_type SET idQuestion_type=id1 WHERE idQuestion_type=id2;
    	UPDATE Question_type SET idQuestion_type=id2 WHERE idQuestion_type=-1;
    	SET @from_procedure_call = NULL;
    	COMMIT;
	END IF;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `createOptionAndGetId` (`name` VARCHAR(100) CHARSET utf8) RETURNS INT(11) BEGIN
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
  `idOption` int(11) NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Answers`
--

INSERT INTO `Answers` (`idAnswer`, `idUser`, `idQuestion`, `idOption`, `datetime`) VALUES
(212, 2, 97, 121, '2017-02-28 22:57:09'),
(213, 2, 94, 35, '2017-03-01 12:00:19'),
(214, 2, 95, 35, '2017-03-01 12:00:19'),
(215, 2, 95, 36, '2017-03-01 12:00:19'),
(216, 2, 95, 37, '2017-03-01 12:00:19'),
(217, 2, 95, 119, '2017-03-01 12:00:19'),
(218, 2, 95, 120, '2017-03-01 12:00:19'),
(219, 2, 96, 36, '2017-03-01 12:00:27'),
(220, 2, 37, 52, '2017-03-01 12:18:00'),
(221, 2, 38, 54, '2017-03-01 12:18:00'),
(222, 2, 38, 56, '2017-03-01 12:18:00'),
(223, 2, 39, 123, '2017-03-01 12:18:00');

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
(87, 21),
(89, 21),
(9, 22),
(13, 22),
(87, 22),
(89, 22),
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
(93, 35),
(94, 35),
(95, 35),
(96, 35),
(5, 36),
(45, 36),
(93, 36),
(94, 36),
(95, 36),
(96, 36),
(45, 37),
(93, 37),
(95, 37),
(43, 40),
(55, 40),
(61, 40),
(92, 40),
(43, 41),
(55, 41),
(61, 41),
(92, 41),
(89, 42),
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
(60, 107),
(90, 110),
(90, 111),
(90, 112),
(90, 113),
(91, 114),
(91, 116),
(91, 117),
(91, 118),
(93, 119),
(95, 119),
(93, 120),
(95, 120),
(97, 121),
(97, 122);

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
(42, 'Možda'),
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
(107, 'ne vidim nista'),
(108, 'sda'),
(109, 'blah'),
(110, '0-5'),
(111, '5-10'),
(112, '10-15'),
(113, '15+'),
(114, 'ispitivanje potreba i stavova potencijalnih kupaca na prezentacijama proizvoda'),
(115, 'kuki'),
(116, 'provjeru stečenog znanja nakon nastave'),
(117, 'kolaboraciju na nastavnim aktivnostima'),
(118, 'brainstorming'),
(119, '4'),
(120, '5'),
(121, 'w'),
(122, 'z'),
(123, 'bok');

-- --------------------------------------------------------

--
-- Table structure for table `Presentation`
--

CREATE TABLE `Presentation` (
  `id` int(11) NOT NULL,
  `path` varchar(255) NOT NULL,
  `checksum` varchar(32) NOT NULL,
  `access_code` varchar(10) NOT NULL,
  `author` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Presentation`
--

INSERT INTO `Presentation` (`id`, `path`, `checksum`, `access_code`, `author`) VALUES
(3, 'ppt/2-nepoznato.ppt', '', 'd9b4vs69v2', 2),
(4, 'ppt/1-Petar Šestak-Programiranje u skriptnim programskim jezicima-1.pptx', 'f20a266070f9bc4f669cf32d3421ae94', 'rpk_anketa', 1),
(5, 'ppt/1-Proizvodnja vina.pptx', '8d3b30b7292e01f3d9b9a0e15c3c9aeb', '7dsR6n2n', 1),
(7, 'ppt/1-Varijable i pokazivači.pptx', '1cd8176f540c820e1d842573f4034894', 'm45k0fz42t', 1),
(10, 'ppt/2-test.pptx', '', 'j173hbvuos', 2),
(17, 'ppt/10-Poslovni plan za poduzeće CroIoT-final.pptx', 'e226a6cc8afc9a8d0e8246c715d57789', '4rl0dn7a68', 10),
(18, 'ppt/1-Youtube.pptx', '99def3c5cba7f9c405a7a90d3c462841', 'n0879n11j3', 1),
(19, 'ppt/1-blank_ppt.pptx', 'ef2f95333dc42a7f68412cf504690fb3', '69up787n13', 1),
(20, 'ppt/2-nova.pptx', '7d8588f883d92d03333318fcf5c730e7', '33g4y723t4', 2),
(22, 'ppt/1-CSharp and Software Design.pptx', '3f243e6c26dc4cf275751a6ef6ae3d6f', '5a99909p6k', 1),
(28, 'ppt/1-LN01-Introduction.ppt', '9cdf2b479616648fa834247d66bc530c', 'rg8a62oq1s', 1),
(30, 'ppt/2-bez_ankete.pptx', '', '123qwe456q', 2),
(31, 'ppt/2-interactive_presentation.pptx', '50d57e6be037a2d3a58de51d5bfeec7c', '4k2dh58k2d', 2);

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
(59, 'kak se zove siva panda?', 0, 1, 42),
(60, 'dal je ovo klasican checkbox group?', 1, 2, 43),
(61, 'dal je ovo klasican radiobutton group?', 1, 1, 43),
(62, 'dal je ovo klasicno pitanje s nadopunjavanjem?', 1, 3, 43),
(87, 'Jeste li se ikad sreli s ičim sličnim na nekoj prezentaciji?', 1, 1, 54),
(88, 'Ako da, gdje (na čijem izlaganju, koja aplikacija je korištena?)', 0, 3, 54),
(89, 'Mislite li da aplikacija može pospješiti interakciju između izlagatelja i publike?', 1, 1, 54),
(90, 'Koliko često sudjelujete u prezentacijama godišnje?', 1, 1, 54),
(91, 'Ovakav način prezentiranja mi se čini primijenjiv za:', 1, 2, 54),
(92, 'prvo', 1, 1, 55),
(93, 'drugo', 1, 2, 55),
(94, 'prv', 1, 1, 56),
(95, 'dr', 1, 2, 56),
(96, 'w', 0, 1, 57),
(97, 'qq', 0, 1, 58);

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
(107, 60),
(40, 61),
(41, 61),
(21, 87),
(22, 87),
(21, 89),
(22, 89),
(42, 89),
(110, 90),
(111, 90),
(112, 90),
(113, 90),
(114, 91),
(116, 91),
(117, 91),
(118, 91),
(40, 92),
(41, 92),
(35, 93),
(36, 93),
(37, 93),
(119, 93),
(120, 93),
(35, 94),
(36, 94),
(35, 95),
(36, 95),
(37, 95),
(119, 95),
(120, 95),
(35, 96),
(36, 96),
(121, 97),
(122, 97);

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
(3, 'text', 2),
(4, 'das', 2);

--
-- Triggers `Question_type`
--
DELIMITER $$
CREATE TRIGGER `question_type_delete` BEFORE DELETE ON `Question_type` FOR EACH ROW BEGIN
DECLARE msg varchar(255);
DECLARE last_consecutive_num int;
SET last_consecutive_num = (SELECT count(*) FROM Question_type);
IF OLD.idQuestion_type <> last_consecutive_num AND @from_procedure_call IS NULL THEN
	SET msg = 'Perform deletion of non-last question type with following command: CALL delete_question_type(IdNum);';
	SIGNAL sqlstate '45000' SET message_text = msg;
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `question_type_insert` BEFORE INSERT ON `Question_type` FOR EACH ROW BEGIN
DECLARE next_consecutive_num int;
IF NOT NEW.mode IN (1,2,3) THEN
	SIGNAL sqlstate '45000' SET message_text = 'Error: mode can only have value 1, 2 or 3.';
END IF;
SET next_consecutive_num = (SELECT count(*) FROM Question_type) +1;
IF NEW.idQuestion_type=0 THEN
	SET NEW.idQuestion_type = next_consecutive_num;
ELSEIF NEW.idQuestion_type<next_consecutive_num AND @from_procedure_call IS NULL THEN
	SIGNAL sqlstate '45000' SET message_text = 'Perform insertion of question type with following command: CALL insert_question_type(nullOrIdNum, \'qt name\', qtModeNum);';
ELSEIF NEW.idQuestion_type>next_consecutive_num THEN
    SIGNAL sqlstate '45000' SET message_text = 'Error: Ids of question types have to be in consecutive order!';
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `question_type_update` BEFORE UPDATE ON `Question_type` FOR EACH ROW BEGIN DECLARE msg varchar(255);
IF OLD.idQuestion_type<>NEW.idQuestion_type AND @from_procedure_call IS NULL THEN
	SET msg = 'Perform swap of question types with following command: CALL swap_question_types(IdNum1, IdNum2);';
	SIGNAL sqlstate '45000' SET message_text = msg;
ELSE
	IF NOT NEW.mode IN (1,2,3) THEN
		SIGNAL sqlstate '45000' SET message_text = 'Error: mode can only have value 1, 2 or 3.';
	END IF;
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
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Reply_request`
--

INSERT INTO `Reply_request` (`user`, `presentation`, `time`) VALUES
(1, 4, '2017-03-05 15:38:59'),
(1, 5, '2017-02-16 20:48:13'),
(2, 10, '2017-01-31 15:47:10'),
(10, 4, '2017-01-07 19:01:22'),
(12, 4, '2017-03-05 15:38:48');

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
(1, 4, 'no'),
(1, 5, 'no'),
(1, 7, 'no'),
(1, 10, 'no'),
(2, 3, 'yes'),
(2, 5, 'yes'),
(2, 10, 'no'),
(10, 4, 'no'),
(12, 4, 'no'),
(13, 10, 'yes');

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
(43, 'Anketa za ispitivanje modularnosti', 'Ova anketa je napravljena kako bi se ispitali aspekti modularnosti kod kreiranja i pregleda anketa', 'rg8a62oq1s'),
(44, 'SOLID Design Principles', 'Još jedna prezentacija o uzorcima dizajna', '5a99909p6k'),
(54, 'Mišljenje o aplikaciji', 'Molimo Vas da nam rješavanjem ove ankete izrazite mišljenje o aplikaciji.', 'rg8a62oq1s'),
(55, 'nova anketa', 'anketa', 'j173hbvuos'),
(56, 'ank', 'ank', '4k2dh58k2d'),
(57, 'qqq', 'rrr', '4k2dh58k2d'),
(58, '000', '999', '4k2dh58k2d');

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
(1, 'Petar Šestak', '1431307750213523', 1, ''),
(2, 'Marin Mihajlovic', '10210532062074946', 2, ''),
(8, 'Marinela Levak', '1336022606431703', 3, ''),
(10, 'Mario Šelek', '1256649427742897', 3, 'ed2L3DqVgfY:APA91bHfonVwB2Hnk7fe-MT4MaUHSiz5xXExFL3LaIDOaPt2fah91hIpD-EpuauXdQGWFrFCHR8ZSxY_xeBV1sYko1KGZ7Rh7tZ3nFi-P8igKlDatvL1I8REaBH3CKA1SUc_UEei4LQ7'),
(12, 'Račun Za Testiranje', '166569610511915', 3, ''),
(13, 'Marin Marin', '104852530032944', 3, '');

-- --------------------------------------------------------

--
-- Structure for view `getPresentationDetails`
--
DROP TABLE IF EXISTS `getPresentationDetails`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `getPresentationDetails`  AS  select `p`.`access_code` AS `access_code`,concat('{"path":"',`p`.`path`,'","id":',`p`.`id`,',"author_name":"',`u`.`name`,'","surveys":[',ifnull(group_concat(concat('{"id":',`s`.`idSurvey`,',"name":"',`s`.`name`,'"}') separator ','),''),']}') AS `presentation_details` from ((`Presentation` `p` left join `Users` `u` on((`p`.`author` = `u`.`idUser`))) left join `Survey` `s` on((`p`.`access_code` = `s`.`access_code`))) group by `p`.`id` order by `p`.`access_code` ;

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
  MODIFY `idAnswer` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=224;
--
-- AUTO_INCREMENT for table `Options`
--
ALTER TABLE `Options`
  MODIFY `idOptions` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;
--
-- AUTO_INCREMENT for table `Presentation`
--
ALTER TABLE `Presentation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `Questions`
--
ALTER TABLE `Questions`
  MODIFY `idQuestions` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;
--
-- AUTO_INCREMENT for table `Question_type`
--
ALTER TABLE `Question_type`
  MODIFY `idQuestion_type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `Role`
--
ALTER TABLE `Role`
  MODIFY `idRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `Survey`
--
ALTER TABLE `Survey`
  MODIFY `idSurvey` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;
--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
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
  ADD CONSTRAINT `Questions_ibfk_1` FOREIGN KEY (`Question_type_idQuestion_type`) REFERENCES `Question_type` (`idQuestion_type`) ON UPDATE CASCADE,
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
