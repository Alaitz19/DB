-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 10.88.0.21:3306
-- Tiempo de generación: 13-05-2024 a las 09:50:13
-- Versión del servidor: 8.0.33
-- Versión de PHP: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `DBI51`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customer`
--

CREATE TABLE `customer` (
  `CustomerId` varchar(20) NOT NULL DEFAULT '',
  `custname` varchar(40) DEFAULT NULL,
  `custaddress` varchar(40) DEFAULT NULL,
  `custphone` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `customer`
--

INSERT INTO `customer` (`CustomerId`, `custname`, `custaddress`, `custphone`) VALUES
('10000001', 'Elizabeth Taylor-Gardner', 'avenida0', 943000001),
('10000002', 'Gisella Fabricci', 'avenida1', 943000002),
('10000003', 'RAMIRO LAVALLIE', 'avenida2', 943000003),
('10000004', 'Leire Alba-Aguirre', 'avenida3', 943000004),
('10000005', 'ETHAN VIOREL', 'avenida4', 943000005),
('10000006', 'Cristilaine Rithyele', 'avenida5', 943000006),
('10000007', 'CHAU COLVARD', 'avenida6', 943000007),
('10000008', 'Bizenta Juaristi', 'avenida7', 943000008),
('10000009', 'Agnetta Bensson', 'avenida8', 943000009),
('10000010', 'OSVALDO BRISTER', 'avenida9', 943000010),
('10000011', 'Mircea Dumitru', 'avenida10', 943000011),
('10000012', 'Aranzazu Bidaurreta', 'avenida11', 943000012),
('10000013', 'James Borg', 'avenida12', 943000013),
('10000014', 'Franklin Wong', 'avenida13', 943000014),
('10000015', 'John B Smith', 'avenida14', 943000015),
('10000016', 'Joyce English', 'avenida15', 943000016),
('10000017', 'Ramesh Narayan', 'avenida16', 943000017),
('10000018', 'Ahmad Jabbar', 'avenida17', 943000018),
('10000019', 'Alicia Zelaya', 'avenida18', 943000019),
('10000020', 'HERMAN SMITHWICK', 'avenida19', 943000020),
('10000021', 'Jennifer Wallace', 'avenida20', 943000021),
('10000022', 'Francisco Zumalakarregui', 'avenida21', 943000022),
('10000023', 'Leon Tolstoi', 'avenida22', 943000023),
('10000024', 'CRIST PARADOWSKI', 'avenida21', 943000024),
('10000025', 'Ivan Turgenev', 'avenida22', 943000025),
('10000026', 'Miguel de Cervantes', 'avenida22', 943000026),
('10000027', 'Maria Rodriguez', 'avenida23', 943000027),
('10000028', 'Pedro Martinez', 'avenida24', 943000028),
('10000029', 'Laura Fernandez', 'avenida25', 943000029),
('10000030', 'Roberto Garcia', 'avenida26', 943000030),
('72515633', 'Mario', 'street1', 632700765),
('72515634', 'Jonathan', NULL, NULL),
('72515635', 'Alain', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `department`
--

CREATE TABLE `department` (
  `Dname` varchar(15) NOT NULL,
  `Dnumber` int NOT NULL,
  `Mgr_ssn` char(9) NOT NULL,
  `Mgr_start_date` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `department`
--

INSERT INTO `department` (`Dname`, `Dnumber`, `Mgr_ssn`, `Mgr_start_date`) VALUES
('Headquarters', 1, '888665555', '1981-6-19'),
('Administration', 4, '987654321', '1995-1-1'),
('Research', 5, '333445555', '1988-5-22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dependent`
--

CREATE TABLE `dependent` (
  `Essn` char(9) NOT NULL,
  `Dependent_name` varchar(15) NOT NULL,
  `Sex` char(1) DEFAULT NULL,
  `Bdate` varchar(10) DEFAULT NULL,
  `Relationship` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `dependent`
--

INSERT INTO `dependent` (`Essn`, `Dependent_name`, `Sex`, `Bdate`, `Relationship`) VALUES
('123456789', 'Alice', 'F', '1988-12-30', 'Daughter'),
('123456789', 'Elizabeth', 'F', '1967-5-5', 'Spouse'),
('333445555', 'Alice', 'F', '1986-4-5', 'Daughter'),
('333445555', 'Joy', 'F', '1958-5-3', 'Spouse'),
('333445555', 'Theodore', 'M', '1983-10-25', 'Son'),
('987654321', 'Abner', 'M', '1942-2-28', 'Spouse');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dept_locations`
--

CREATE TABLE `dept_locations` (
  `Dnumber` int NOT NULL,
  `Dlocation` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `dept_locations`
--

INSERT INTO `dept_locations` (`Dnumber`, `Dlocation`) VALUES
(1, 'Houston'),
(4, 'Stafford'),
(5, 'Bellaire'),
(5, 'Houston');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dishes`
--

CREATE TABLE `dishes` (
  `dish` varchar(30) NOT NULL,
  `cuisine` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `difficulty` decimal(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `dishes`
--

INSERT INTO `dishes` (`dish`, `cuisine`, `category`, `difficulty`) VALUES
('cheesecake', NULL, NULL, NULL),
('chickensausage', NULL, NULL, NULL),
('eggsbenedicte', NULL, NULL, NULL),
('mushroomsoup', NULL, NULL, NULL),
('olives', NULL, NULL, NULL),
('pepperonipizza', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eats`
--

CREATE TABLE `eats` (
  `nameId` varchar(40) NOT NULL,
  `dish` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `eats`
--

INSERT INTO `eats` (`nameId`, `dish`) VALUES
('Benjamin', 'cheesecake'),
('Dan', 'cheesecake'),
('Elvis', 'cheesecake'),
('Hillary', 'cheesecake'),
('Kevin', 'cheesecake'),
('Dan', 'chickensausage'),
('Hillary', 'chickensausage'),
('Dan', 'eggsbenedicte'),
('Elvis', 'eggsbenedicte'),
('Hillary', 'eggsbenedicte'),
('Ian', 'eggsbenedicte'),
('Kevin', 'eggsbenedicte'),
('Ashley', 'mushroomsoup'),
('Dan', 'mushroomsoup'),
('Kevin', 'mushroomsoup'),
('Ian', 'olives'),
('Benjamin', 'pepperonipizza'),
('Dan', 'pepperonipizza'),
('Ian', 'pepperonipizza');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employee`
--

CREATE TABLE `employee` (
  `Fname` varchar(15) NOT NULL,
  `Minit` char(1) DEFAULT NULL,
  `Lname` varchar(15) NOT NULL,
  `Ssn` char(9) NOT NULL,
  `Bdate` varchar(10) DEFAULT NULL,
  `Address` varchar(30) DEFAULT NULL,
  `Sex` char(1) DEFAULT NULL,
  `Salary` decimal(10,2) DEFAULT NULL,
  `Super_ssn` char(9) DEFAULT NULL,
  `Dno` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `employee`
--

INSERT INTO `employee` (`Fname`, `Minit`, `Lname`, `Ssn`, `Bdate`, `Address`, `Sex`, `Salary`, `Super_ssn`, `Dno`) VALUES
('John', 'B', 'Smith', '123456789', '1965-1-9', '731 Foundren, Houston, TX', 'M', 30000.00, '333445555', 5),
('Franklin', 'T', 'Wong', '333445555', '1955-12-8', '638 Voss,Houston, TX', 'M', 40000.00, '888665555', 5),
('Joyce', 'A', 'English', '453453453', '1972-7-31', '5631 Rice, Houston, TX', 'F', 25000.00, '333445555', 5),
('Ramesh', 'K', 'Narayan', '666884444', '1962-9-15', '975 Fire Oak, Humble, TX', 'M', 38000.00, '333445555', 5),
('James', 'E', 'Borg', '888665555', '1937-11-10', '450 Stone, Houston, TX', 'M', 55000.00, NULL, 1),
('Jennifer', 'S', 'Wallace', '987654321', '1941-6-20', '291, Berry, Bellaire ,TX', 'F', 43000.00, '888665555', 4),
('Ahmad', 'V', 'Jabbar', '987987987', '1969-3-29', '980 Dallas, Houston, TX', 'M', 25000.00, '987654321', 4),
('Alicia', 'J', 'Zelaya', '999887777', '1968-1-19', '3321, Castle, Spring, TX', 'F', 25000.00, '987654321', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employee_customer`
--

CREATE TABLE `employee_customer` (
  `Emp_id` char(9) NOT NULL,
  `Cust_Id` varchar(20) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `excur_opt_customer`
--

CREATE TABLE `excur_opt_customer` (
  `TripTo` varchar(20) NOT NULL DEFAULT '',
  `DepartureDate` varchar(10) NOT NULL DEFAULT '0000-00-00',
  `CodeExc` int NOT NULL DEFAULT '0',
  `CustomerId` varchar(20) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `excur_opt_customer`
--

INSERT INTO `excur_opt_customer` (`TripTo`, `DepartureDate`, `CodeExc`, `CustomerId`) VALUES
('Sofia', '2018-04-01', 1, '10000001'),
('Madrid', '2018-05-01', 13, '10000002'),
('India', '2018-12-01', 12, '10000003'),
('Sofia', '2018-04-01', 1, '10000005'),
('Beijing', '2018-01-08', 9, '10000007'),
('Sofia', '2018-04-01', 1, '10000008'),
('Laayoune', '2018-09-10', 2, '10000011'),
('Pyongyang', '2018-01-06', 8, '10000012'),
('Biarritz', '2018-01-02', 5, '10000013'),
('Laayoune', '2018-09-10', 2, '10000014'),
('South Korea', '2018-04-01', 11, '10000014'),
('Laayoune', '2018-09-10', 2, '10000015'),
('Laayoune', '2018-09-10', 2, '10000017'),
('Biarritz', '2018-01-01', 4, '10000018'),
('Laayoune', '2018-11-02', 3, '10000021'),
('Volgograd', '2018-05-02', 14, '10000023'),
('Hong Kong', '2018-01-05', 7, '10000024'),
('Hamburg', '2018-01-03', 6, '10000025'),
('Mars', '2020-04-11', 10, '10000026');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `frequents`
--

CREATE TABLE `frequents` (
  `nameId` varchar(40) NOT NULL,
  `restaurname` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `frequents`
--

INSERT INTO `frequents` (`nameId`, `restaurname`) VALUES
('Dan', 'DearGourmet'),
('Elvis', 'DearGourmet'),
('Hillary', 'DearGourmet'),
('Ian', 'DearGourmet'),
('Benjamin', 'EatnGo'),
('Elvis', 'EatnGo'),
('Kevin', 'EatnGo'),
('Ashley', 'GoodFood'),
('Hillary', 'GoodFood'),
('Ian', 'GoodFood'),
('Linda', 'GoodFood'),
('Benjamin', 'KingMeal'),
('Hillary', 'KingMeal'),
('Kevin', 'KingMeal'),
('Tracy', 'KingMeal'),
('Ashley', 'LittleFat'),
('Linda', 'LittleFat'),
('Dan', 'NewYorkTaste'),
('Ian', 'NewYorkTaste');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `HotelId` varchar(20) NOT NULL DEFAULT '',
  `hotelname` varchar(40) DEFAULT NULL,
  `hotelcity` varchar(20) DEFAULT NULL,
  `hotelcapacity` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`HotelId`, `hotelname`, `hotelcity`, `hotelcapacity`) VALUES
('h01', 'Amara', 'Donostia', 340),
('h02', 'Zenit', 'Donostia', 200),
('h03', 'Silken', 'Barcelona', 23),
('h04', 'Sofitel', 'Biarritz', 1000),
('h05', 'Meininger', 'Frankfurt', 20),
('h06', 'Questenberk', 'Prague', 350),
('h07', 'Renaissance', 'Paris', 40),
('h08', 'Ciampino', 'Rome', 1),
('h09', 'Palaccio u Domu', 'Ajaccio', 75),
('h10', 'Salwan', 'Laayoune', 20),
('h11', 'The Oberoi Vanyavila', 'Rajasthan', 200),
('h12', 'Rome Marriot', 'Rome', 300),
('h13', 'Shangri-La', 'Shenzhen', 20),
('h14', 'Hotel 41', 'London', 20),
('h15', 'Le Regina', 'Warsaw', 202),
('h16', 'Sputnik', 'Minsk', 201),
('h17', 'Sheremetyevo Airport', 'Moscow', 204),
('h18', 'Yerevan Deluxe', 'Armenia', 270),
('h19', 'Holiday Inn', 'Madrid', 2700),
('h20', 'Urbany BCN', 'Barcelona', 2700),
('h21', 'Hotel Q!', 'Berlin', 700);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_trip`
--

CREATE TABLE `hotel_trip` (
  `TripTo` varchar(20) NOT NULL DEFAULT '',
  `DepartureDate` varchar(10) NOT NULL DEFAULT '0000-00-00',
  `HotelId` varchar(20) NOT NULL DEFAULT '',
  `price` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `hotel_trip`
--

INSERT INTO `hotel_trip` (`TripTo`, `DepartureDate`, `HotelId`, `price`) VALUES
('Antarctica', '2025-11-11', 'h01', NULL),
('Antarctica', '2025-11-11', 'h02', NULL),
('Antarctica', '2025-11-11', 'h07', NULL),
('Antarctica', '2025-11-11', 'h14', NULL),
('Barcelona', '2018-05-04', 'h03', NULL),
('Barcelona', '2018-05-04', 'h19', NULL),
('Barcelona', '2018-05-04', 'h20', NULL),
('Barcelona', '2018-05-04', 'h21', NULL),
('Beijing', '2018-01-08', 'h11', NULL),
('Beijing', '2018-01-08', 'h12', NULL),
('Beirut', '2018-05-03', 'h20', NULL),
('Biarritz', '2018-01-01', 'h02', NULL),
('Biarritz', '2018-01-01', 'h03', NULL),
('Biarritz', '2018-01-01', 'h04', NULL),
('Biarritz', '2018-01-02', 'h05', NULL),
('Biarritz', '2018-01-02', 'h06', NULL),
('Biarritz', '2018-01-02', 'h09', NULL),
('Bucharest', '2008-11-10', 'h02', NULL),
('Budapest', '2020-11-11', 'h03', NULL),
('Budapest', '2020-11-11', 'h09', NULL),
('Chisinau', '2018-11-11', 'h01', NULL),
('Chisinau', '2018-11-11', 'h05', NULL),
('Everest', '2022-03-03', 'h11', NULL),
('Everest', '2022-03-03', 'h18', NULL),
('Everest', '2022-03-03', 'h19', NULL),
('Hamburg', '2018-01-03', 'h07', NULL),
('Heraklion', '2021-11-11', 'h08', NULL),
('Hong Kong', '2018-01-05', 'h01', NULL),
('Hong Kong', '2018-01-05', 'h10', NULL),
('Hong Kong', '2018-01-05', 'h11', NULL),
('Hong Kong', '2018-01-05', 'h12', NULL),
('India', '2018-12-01', 'h09', NULL),
('India', '2018-12-01', 'h11', NULL),
('India', '2018-12-01', 'h18', NULL),
('India', '2018-12-01', 'h19', NULL),
('Jerusalem', '2018-06-01', 'h03', NULL),
('Jerusalem', '2018-06-01', 'h04', NULL),
('Laayoune', '2018-09-10', 'h09', NULL),
('Laayoune', '2018-09-10', 'h10', NULL),
('Laayoune', '2018-09-10', 'h11', NULL),
('Laayoune', '2018-09-10', 'h12', NULL),
('Laayoune', '2018-11-02', 'h03', NULL),
('Laayoune', '2018-11-02', 'h10', NULL),
('Madrid', '2018-05-01', 'h19', NULL),
('Mars', '2020-04-11', 'h03', NULL),
('Mars', '2020-04-11', 'h09', NULL),
('Netherlands', '2019-11-11', 'h09', NULL),
('Poland', '2018-03-01', 'h05', NULL),
('Poland', '2018-03-01', 'h06', NULL),
('Prague', '2018-01-11', 'h01', NULL),
('Prague', '2018-01-11', 'h02', NULL),
('Prague', '2018-01-11', 'h03', NULL),
('Pyongyang', '2018-01-06', 'h13', NULL),
('Riga', '2018-06-02', 'h03', NULL),
('Riga', '2018-06-02', 'h07', NULL),
('Sofia', '2018-04-01', 'h03', NULL),
('South Korea', '2018-04-01', 'h01', NULL),
('Volgograd', '2018-05-02', 'h05', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_trip_customer`
--

CREATE TABLE `hotel_trip_customer` (
  `TripTo` varchar(20) NOT NULL DEFAULT '',
  `DepartureDate` varchar(10) NOT NULL DEFAULT '0000-00-00',
  `HotelId` varchar(20) NOT NULL DEFAULT '',
  `CustomerId` varchar(20) NOT NULL DEFAULT '',
  `NumNights` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `hotel_trip_customer`
--

INSERT INTO `hotel_trip_customer` (`TripTo`, `DepartureDate`, `HotelId`, `CustomerId`, `NumNights`) VALUES
('Antarctica', '2025-11-11', 'h01', '10000022', 1),
('Antarctica', '2025-11-11', 'h02', '10000022', 1),
('Antarctica', '2025-11-11', 'h14', '10000022', 1),
('Barcelona', '2018-05-04', 'h20', '10000004', 3),
('Barcelona', '2018-05-04', 'h21', '10000004', 3),
('Beijing', '2018-01-08', 'h12', '10000007', 6),
('Beirut', '2018-05-03', 'h20', '10000015', 7),
('Biarritz', '2018-01-01', 'h02', '10000018', 4),
('Biarritz', '2018-01-02', 'h09', '10000013', 1),
('Hamburg', '2018-01-03', 'h07', '10000025', 5),
('Hong Kong', '2018-01-05', 'h12', '10000022', 5),
('Hong Kong', '2018-01-05', 'h12', '10000024', 5),
('Hong Kong', '2018-01-05', 'h12', '10000025', 5),
('Hong Kong', '2018-01-05', 'h12', '10000026', 5),
('India', '2018-12-01', 'h19', '10000003', 4),
('Jerusalem', '2018-06-01', 'h04', '10000009', 10),
('Laayoune', '2018-09-10', 'h10', '10000017', 1),
('Laayoune', '2018-09-10', 'h11', '10000014', 1),
('Laayoune', '2018-09-10', 'h11', '10000015', 1),
('Laayoune', '2018-09-10', 'h12', '10000011', 1),
('Laayoune', '2018-11-02', 'h10', '10000021', 2),
('Madrid', '2018-05-01', 'h19', '10000002', 1),
('Madrid', '2018-05-01', 'h19', '10000009', 1),
('Mars', '2020-04-11', 'h09', '10000025', 5),
('Mars', '2020-04-11', 'h09', '10000026', 5),
('Poland', '2018-03-01', 'h06', '10000004', 1),
('Prague', '2018-01-11', 'h01', '10000009', 1),
('Prague', '2018-01-11', 'h02', '10000009', 1),
('Pyongyang', '2018-01-06', 'h13', '10000012', 4),
('Riga', '2018-06-02', 'h07', '10000001', 1),
('Riga', '2018-06-02', 'h07', '10000016', 1),
('Riga', '2018-06-02', 'h07', '10000019', 1),
('Riga', '2018-06-02', 'h07', '10000020', 1),
('Sofia', '2018-04-01', 'h03', '10000001', 4),
('Sofia', '2018-04-01', 'h03', '10000005', 4),
('Sofia', '2018-04-01', 'h03', '10000006', 4),
('Sofia', '2018-04-01', 'h03', '10000008', 4),
('South Korea', '2018-04-01', 'h01', '10000010', 3),
('Volgograd', '2018-05-02', 'h05', '10000023', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `languages`
--

CREATE TABLE `languages` (
  `GuideId` varchar(20) NOT NULL DEFAULT '',
  `Lang` varchar(20) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `languages`
--

INSERT INTO `languages` (`GuideId`, `Lang`) VALUES
('72515633', 'Catalan'),
('72515633', 'English'),
('72515633', 'French'),
('72515633', 'Spanish'),
('72515634', 'English'),
('72515634', 'Estonian'),
('72515634', 'Euskera'),
('72515635', 'Norwegian'),
('72515636', 'Latvian'),
('72515636', 'Lithuanian'),
('72515637', 'Portuguese'),
('72515638', 'Italian'),
('72515638', 'Spanish'),
('72515639', 'English'),
('72515639', 'French'),
('72515640', 'Dutch'),
('72515640', 'Valencian'),
('72515641', 'English'),
('72515641', 'Euskera'),
('72515641', 'French'),
('72515642', 'Arabic'),
('72515642', 'French'),
('72515643', 'English'),
('72515644', 'Chinese'),
('72515644', 'German'),
('72515645', 'Japanese'),
('72515645', 'Korean'),
('72515646', 'Chinese'),
('72515646', 'Spanish'),
('72515647', 'Chinese'),
('72515647', 'Spanish'),
('72515648', 'Chinese'),
('72515648', 'Japanese'),
('72515648', 'Spanish'),
('72515649', 'English'),
('72515650', 'Japanese'),
('72515650', 'Spanish'),
('72515651', 'Spanish'),
('72515652', 'English'),
('72515653', 'French'),
('72515654', 'Chinese'),
('72515654', 'Japanese'),
('72515654', 'Spanish'),
('72515655', 'Spanish'),
('72515656', 'Chinese'),
('72515656', 'Japanese'),
('72515657', 'Spanish'),
('72515658', 'English'),
('72515658', 'Spanish'),
('72515659', 'English'),
('72515659', 'Spanish'),
('72515660', 'English'),
('72515660', 'Japanese'),
('72515660', 'Russian'),
('72515660', 'Spanish'),
('72515661', 'English'),
('72515661', 'Spanish');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menu`
--

CREATE TABLE `menu` (
  `mtype` int NOT NULL,
  `mid` int NOT NULL,
  `price` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menu_order`
--

CREATE TABLE `menu_order` (
  `numord` int NOT NULL,
  `menu_mtype` int NOT NULL,
  `menu_id` int NOT NULL,
  `customer_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `optional_excursion`
--

CREATE TABLE `optional_excursion` (
  `TripTo` varchar(20) NOT NULL DEFAULT '',
  `DepartureDate` varchar(10) NOT NULL DEFAULT '0000-00-00',
  `CodeExc` int NOT NULL DEFAULT '0',
  `ExcursionTo` varchar(20) DEFAULT NULL,
  `DepartTime` time DEFAULT NULL,
  `DepartPlace` varchar(20) DEFAULT NULL,
  `Price` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `optional_excursion`
--

INSERT INTO `optional_excursion` (`TripTo`, `DepartureDate`, `CodeExc`, `ExcursionTo`, `DepartTime`, `DepartPlace`, `Price`) VALUES
('Beijing', '2018-01-08', 9, 'touristattraction5', '10:12:10', 'departplace007', 750),
('Biarritz', '2018-01-01', 4, 'touristattraction3', '11:12:00', 'departplace004', 550),
('Biarritz', '2018-01-02', 5, 'touristattraction4', '08:12:00', 'departplace004', 222),
('Hamburg', '2018-01-03', 6, 'touristattraction4', '05:12:00', 'departplace006', 100),
('Hong Kong', '2018-01-05', 7, 'touristattraction5', '11:11:11', 'departplace007', 450),
('India', '2018-12-01', 12, 'touristattraction7', '00:12:00', 'departplace010', 777),
('Laayoune', '2018-09-10', 2, 'touristattraction1', '00:10:00', 'departplace002', 750),
('Laayoune', '2018-11-02', 3, 'touristattraction2', '00:12:10', 'departplace003', 775),
('Madrid', '2018-05-01', 13, 'touristattraction8', '10:08:11', 'departplace011', 800),
('Mars', '2020-04-11', 10, 'touristattraction6', '00:12:00', 'departplace008', 754),
('Pyongyang', '2018-01-06', 8, 'touristattraction5', '04:12:00', 'departplace007', 75),
('Sofia', '2018-04-01', 1, 'touristattraction1', '00:12:00', 'departplace001', 75),
('South Korea', '2018-04-01', 11, 'touristattraction7', '06:12:00', 'departplace009', 475),
('Volgograd', '2018-05-02', 14, 'touristattraction9', '00:12:00', 'departplace012', 755);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person`
--

CREATE TABLE `person` (
  `nameId` varchar(40) NOT NULL,
  `age` int DEFAULT NULL,
  `gender` char(6) DEFAULT NULL,
  `id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `person`
--

INSERT INTO `person` (`nameId`, `age`, `gender`, `id`) VALUES
('Abigail', 30, 'Female', 123456),
('Alexander', 35, 'Male', 789123),
('Anne', 17, 'female', 11),
('Ashley', 21, 'female', 6),
('Benjamin', 21, 'male', 2),
('Dan', 13, 'male', 4),
('Elvis', 45, 'male', 5),
('Grigory', 7, 'male', 5),
('Hillary', 30, 'female', 8),
('Ian', 18, 'male', 9),
('Kevin', 24, 'male', 7),
('Linda', 67, 'female', 12),
('Tracy', 22, 'female', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project`
--

CREATE TABLE `project` (
  `Pname` varchar(15) NOT NULL,
  `Pnumber` int NOT NULL,
  `Plocation` varchar(15) DEFAULT NULL,
  `Dnum` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `project`
--

INSERT INTO `project` (`Pname`, `Pnumber`, `Plocation`, `Dnum`) VALUES
('ProductX', 1, 'Bellaire', 5),
('ProductY', 2, 'Sugardland', 5),
('ProductZ', 3, 'Houston', 5),
('Computerization', 10, 'Stafford', 4),
('Reorganization', 20, 'Houston', 1),
('Newbenefits', 30, 'Stafford', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `restaurant`
--

CREATE TABLE `restaurant` (
  `restaurname` varchar(25) NOT NULL,
  `city` varchar(45) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `rating` decimal(3,1) DEFAULT NULL,
  `reportsresults2` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `restaurant`
--

INSERT INTO `restaurant` (`restaurname`, `city`, `capacity`, `rating`, `reportsresults2`) VALUES
('DearGourmet', 'New York', 60, NULL, NULL),
('Dish2Eat', 'Paris', 25, NULL, NULL),
('EatnGo', 'Paris', 25, NULL, NULL),
('GoodFood', 'Berlin', 50, NULL, NULL),
('KingMeal', 'Chicago', 70, NULL, NULL),
('LittleFat', 'Moscow', 35, NULL, NULL),
('NewYorkTaste', 'New York', 100, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sales`
--

CREATE TABLE `sales` (
  `restaurname` varchar(25) NOT NULL,
  `dateOfSale` date NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `sales`
--

INSERT INTO `sales` (`restaurname`, `dateOfSale`, `amount`) VALUES
('DearGourmet', '2018-01-13', 3000.00),
('DearGourmet', '2018-01-16', 1000.00),
('DearGourmet', '2018-12-21', 15000.00),
('EatnGo', '2018-03-12', 6000.00),
('EatnGo', '2018-08-15', 4000.00),
('KingMeal', '2018-03-03', 10000.00),
('KingMeal', '2018-04-11', 8000.00),
('KingMeal', '2018-05-09', 2000.00),
('LittleFat', '2018-06-15', 18000.00),
('LittleFat', '2018-07-05', 17000.00),
('LittleFat', '2018-09-09', 13000.00),
('NewYorkTaste', '2018-07-07', 7000.00),
('NewYorkTaste', '2018-10-11', 25000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `serves`
--

CREATE TABLE `serves` (
  `restaurname` varchar(25) NOT NULL,
  `dish` varchar(30) NOT NULL,
  `price` decimal(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `serves`
--

INSERT INTO `serves` (`restaurname`, `dish`, `price`) VALUES
('DearGourmet', 'cheesecake', 9.00),
('DearGourmet', 'chickensausage', 13.00),
('DearGourmet', 'pepperonipizza', 8.00),
('Dish2Eat', 'cheesecake', 10.00),
('Dish2Eat', 'eggsbenedicte', 11.00),
('EatnGo', 'cheesecake', 8.00),
('EatnGo', 'eggsbenedicte', 9.00),
('GoodFood', 'cheesecake', 10.00),
('GoodFood', 'mushroomsoup', 11.00),
('KingMeal', 'cheesecake', 9.00),
('KingMeal', 'chickensausage', 12.00),
('KingMeal', 'eggsbenedicte', 12.00),
('KingMeal', 'pepperonipizza', 12.00),
('LittleFat', 'cheesecake', 7.00),
('LittleFat', 'chickensausage', 10.00),
('LittleFat', 'mushroomsoup', 9.00),
('LittleFat', 'pepperonipizza', 10.00),
('NewYorkTaste', 'cheesecake', 7.00),
('NewYorkTaste', 'eggsbenedicte', 9.00),
('NewYorkTaste', 'pepperonipizza', 8.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tourguide`
--

CREATE TABLE `tourguide` (
  `GuideId` varchar(20) NOT NULL,
  `guidename` varchar(20) NOT NULL,
  `guidephone` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `tourguide`
--

INSERT INTO `tourguide` (`GuideId`, `guidename`, `guidephone`) VALUES
('50', 'Daniela', 6667),
('51', 'Leire', 66678),
('72515633', 'Mario', 632700765),
('72515634', 'Jonathan', 632700766),
('72515635', 'Alain', 632700767),
('72515636', 'Gorka', 632700768),
('72515637', 'Joseba', 632700769),
('72515638', 'Cristian', 632700770),
('72515639', 'Gonzalo', 632700771),
('72515640', 'Asier', 632700772),
('72515641', 'Jose', 632700773),
('72515642', 'Ivan', 632700774),
('72515643', 'Julen', 632700775),
('72515644', 'Ander', 632700776),
('72515645', 'Miguel', 632700777),
('72515646', 'Manuel', 632700778),
('72515647', 'Kevin', 632700779),
('72515648', 'IÃ±aki', 632700780),
('72515649', 'Gilen', 632700781),
('72515650', 'Ainoha', 632700782),
('72515651', 'Unai', 632700783),
('72515652', 'Javier', 632700784),
('72515653', 'Maria', 632700785),
('72515654', 'Ksenia', 632700786),
('72515655', 'Mikel', 632700787),
('72515656', 'Colin', 632700788),
('72515657', 'Patxi', 632700789),
('72515658', 'Jon', 632700790),
('72515659', 'Cristian', 632700791),
('72515660', 'Gonzalo', 632700792),
('72515661', 'Alain', 632700793);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trip`
--

CREATE TABLE `trip` (
  `TripTo` varchar(20) NOT NULL DEFAULT '',
  `DepartureDate` varchar(10) NOT NULL DEFAULT '0000-00-00',
  `Numdays` int DEFAULT NULL,
  `CityDeparture` varchar(20) DEFAULT NULL,
  `GuideId` varchar(20) DEFAULT NULL,
  `Ppday` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `trip`
--

INSERT INTO `trip` (`TripTo`, `DepartureDate`, `Numdays`, `CityDeparture`, `GuideId`, `Ppday`) VALUES
('Antarctica', '2025-11-11', 800, 'Irun', '72515650', 405),
('Barcelona', '2018-05-04', 5, 'Essen', '72515643', 741),
('Beijing', '2018-01-08', 35, 'Limoges', '72515636', 210),
('Beirut', '2018-05-03', 602, 'Donostia', '72515641', 325),
('Biarritz', '2018-01-01', 40, 'Paris', '72515635', 400),
('Biarritz', '2018-01-02', 50, 'Oslo', '72515635', 450),
('Bucharest', '2008-11-10', 55, 'Irun', '72515633', 30),
('Budapest', '2020-11-11', 50, 'Irun', '72515639', 400),
('Chisinau', '2018-11-11', 5, 'Irun', '72515633', 40),
('Everest', '2022-03-03', 42, 'Milan', '72515650', 40),
('Hamburg', '2018-01-03', 20, 'Pau', '72515635', 300),
('Heraklion', '2021-11-11', 5, 'Irun', '72515646', 1500),
('Hong Kong', '2018-01-05', 15, 'Donostia', '72515635', 250),
('India', '2018-12-01', 54, 'Limoges', '72515639', 652),
('Jerusalem', '2018-06-01', 5, 'Paris', '72515644', 111),
('Laayoune', '2018-09-10', 365, 'Donostia', '72515633', 120),
('Laayoune', '2018-11-02', 365, 'Donostia', '72515634', 119),
('Madrid', '2018-05-01', 51, 'Stuttgart', '72515640', 240),
('Mars', '2020-04-11', 78, 'Essen', '72515637', 800),
('Netherlands', '2019-11-11', 5, 'Irun', '72515647', 452),
('Poland', '2018-03-01', 2, 'Irun', '72515633', 10),
('Prague', '2018-01-11', 12, 'Irun', '72515651', 65),
('Pyongyang', '2018-01-06', 25, 'Donostia', '72515636', 170),
('Riga', '2018-06-02', 3, 'Florence', '72515645', 512),
('Sofia', '2018-04-01', 5, 'Irun', '72515633', 75),
('South Korea', '2018-04-01', 7, 'Donostia', '72515638', 40),
('Volgograd', '2018-05-02', 500, 'Irun', '72515641', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `works_on`
--

CREATE TABLE `works_on` (
  `Essn` char(9) NOT NULL,
  `Pno` int NOT NULL,
  `Hours` decimal(3,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `works_on`
--

INSERT INTO `works_on` (`Essn`, `Pno`, `Hours`) VALUES
('123456789', 1, 32.0),
('123456789', 2, 7.0),
('333445555', 2, 20.0),
('333445555', 3, 10.0),
('333445555', 10, 10.0),
('333445555', 20, 10.0),
('453453453', 1, 20.0),
('453453453', 2, 20.0),
('666884444', 3, 40.0),
('888665555', 20, 0.0),
('987654321', 20, 15.0),
('987654321', 30, 20.0),
('987987987', 10, 35.0),
('987987987', 30, 5.0),
('999887777', 10, 10.0),
('999887777', 30, 30.0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CustomerId`);

--
-- Indices de la tabla `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`Dnumber`),
  ADD UNIQUE KEY `Dname_UNIQUE` (`Dname`),
  ADD KEY `Mgr_ssn` (`Mgr_ssn`);

--
-- Indices de la tabla `dependent`
--
ALTER TABLE `dependent`
  ADD PRIMARY KEY (`Essn`,`Dependent_name`),
  ADD KEY `essdep` (`Essn`);

--
-- Indices de la tabla `dept_locations`
--
ALTER TABLE `dept_locations`
  ADD PRIMARY KEY (`Dnumber`,`Dlocation`),
  ADD KEY `dnum` (`Dnumber`);

--
-- Indices de la tabla `dishes`
--
ALTER TABLE `dishes`
  ADD PRIMARY KEY (`dish`);

--
-- Indices de la tabla `eats`
--
ALTER TABLE `eats`
  ADD PRIMARY KEY (`nameId`,`dish`),
  ADD KEY `idx_eats_nameId` (`nameId`),
  ADD KEY `eats_dish` (`dish`);

--
-- Indices de la tabla `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`Ssn`),
  ADD KEY `superssn` (`Super_ssn`),
  ADD KEY `dno` (`Dno`);

--
-- Indices de la tabla `employee_customer`
--
ALTER TABLE `employee_customer`
  ADD PRIMARY KEY (`Emp_id`,`Cust_Id`),
  ADD UNIQUE KEY `Cust_Id` (`Cust_Id`),
  ADD KEY `eid` (`Emp_id`),
  ADD KEY `cid` (`Cust_Id`);

--
-- Indices de la tabla `excur_opt_customer`
--
ALTER TABLE `excur_opt_customer`
  ADD PRIMARY KEY (`TripTo`,`DepartureDate`,`CodeExc`,`CustomerId`),
  ADD KEY `ttddce_eoc` (`TripTo`,`DepartureDate`,`CodeExc`),
  ADD KEY `eoc_cid` (`CustomerId`);

--
-- Indices de la tabla `frequents`
--
ALTER TABLE `frequents`
  ADD PRIMARY KEY (`nameId`,`restaurname`),
  ADD KEY `freq_nameId` (`nameId`),
  ADD KEY `freq_restaurname` (`restaurname`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`HotelId`);

--
-- Indices de la tabla `hotel_trip`
--
ALTER TABLE `hotel_trip`
  ADD PRIMARY KEY (`TripTo`,`DepartureDate`,`HotelId`),
  ADD KEY `ttdd` (`TripTo`,`DepartureDate`),
  ADD KEY `h` (`HotelId`);

--
-- Indices de la tabla `hotel_trip_customer`
--
ALTER TABLE `hotel_trip_customer`
  ADD PRIMARY KEY (`TripTo`,`DepartureDate`,`HotelId`,`CustomerId`),
  ADD KEY `ttddh` (`TripTo`,`DepartureDate`,`HotelId`),
  ADD KEY `cid` (`CustomerId`);

--
-- Indices de la tabla `languages`
--
ALTER TABLE `languages`
  ADD PRIMARY KEY (`GuideId`,`Lang`),
  ADD KEY `lgid` (`GuideId`);

--
-- Indices de la tabla `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`mtype`,`mid`);

--
-- Indices de la tabla `menu_order`
--
ALTER TABLE `menu_order`
  ADD PRIMARY KEY (`numord`),
  ADD KEY `idx_menu_mtype` (`menu_mtype`,`menu_id`),
  ADD KEY `idx_customer` (`customer_id`);

--
-- Indices de la tabla `optional_excursion`
--
ALTER TABLE `optional_excursion`
  ADD PRIMARY KEY (`TripTo`,`DepartureDate`,`CodeExc`),
  ADD KEY `ttdd_oe` (`TripTo`,`DepartureDate`);

--
-- Indices de la tabla `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`nameId`),
  ADD KEY `idx_id` (`id`);

--
-- Indices de la tabla `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`Pnumber`),
  ADD UNIQUE KEY `Pname_UNIQUE` (`Pname`),
  ADD KEY `Dnum` (`Dnum`);

--
-- Indices de la tabla `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`restaurname`),
  ADD KEY `idx_reportsresuls` (`reportsresults2`);

--
-- Indices de la tabla `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`restaurname`,`dateOfSale`),
  ADD UNIQUE KEY `dateOfSale` (`dateOfSale`),
  ADD KEY `idx_restaurnamesales` (`restaurname`);

--
-- Indices de la tabla `serves`
--
ALTER TABLE `serves`
  ADD PRIMARY KEY (`restaurname`,`dish`),
  ADD KEY `idx_restaurname` (`restaurname`),
  ADD KEY `fk_serves_dish_idx` (`dish`);

--
-- Indices de la tabla `tourguide`
--
ALTER TABLE `tourguide`
  ADD PRIMARY KEY (`GuideId`);

--
-- Indices de la tabla `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`TripTo`,`DepartureDate`),
  ADD KEY `gid` (`GuideId`);

--
-- Indices de la tabla `works_on`
--
ALTER TABLE `works_on`
  ADD PRIMARY KEY (`Essn`,`Pno`),
  ADD KEY `essn` (`Essn`),
  ADD KEY `pno` (`Pno`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `menu_order`
--
ALTER TABLE `menu_order`
  MODIFY `numord` int NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dependent`
--
ALTER TABLE `dependent`
  ADD CONSTRAINT `EssnDep_Ssn` FOREIGN KEY (`Essn`) REFERENCES `employee` (`Ssn`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `dept_locations`
--
ALTER TABLE `dept_locations`
  ADD CONSTRAINT `dnumber` FOREIGN KEY (`Dnumber`) REFERENCES `department` (`Dnumber`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `eats`
--
ALTER TABLE `eats`
  ADD CONSTRAINT `fk_eats_dish` FOREIGN KEY (`dish`) REFERENCES `dishes` (`dish`),
  ADD CONSTRAINT `fk_eats_nameId` FOREIGN KEY (`nameId`) REFERENCES `person` (`nameId`);

--
-- Filtros para la tabla `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `depno` FOREIGN KEY (`Dno`) REFERENCES `department` (`Dnumber`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `superssn` FOREIGN KEY (`Super_ssn`) REFERENCES `employee` (`Ssn`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `employee_customer`
--
ALTER TABLE `employee_customer`
  ADD CONSTRAINT `Cust_Cust` FOREIGN KEY (`Cust_Id`) REFERENCES `customer` (`CustomerId`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `excur_opt_customer`
--
ALTER TABLE `excur_opt_customer`
  ADD CONSTRAINT `eoc_cus` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `eoc_oe` FOREIGN KEY (`TripTo`,`DepartureDate`,`CodeExc`) REFERENCES `optional_excursion` (`TripTo`, `DepartureDate`, `CodeExc`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `frequents`
--
ALTER TABLE `frequents`
  ADD CONSTRAINT `fk_freq_nameid` FOREIGN KEY (`nameId`) REFERENCES `person` (`nameId`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_freq_restaurname` FOREIGN KEY (`restaurname`) REFERENCES `restaurant` (`restaurname`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `hotel_trip`
--
ALTER TABLE `hotel_trip`
  ADD CONSTRAINT `ht_h` FOREIGN KEY (`HotelId`) REFERENCES `hotel` (`HotelId`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `ht_t` FOREIGN KEY (`TripTo`,`DepartureDate`) REFERENCES `trip` (`TripTo`, `DepartureDate`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `hotel_trip_customer`
--
ALTER TABLE `hotel_trip_customer`
  ADD CONSTRAINT `htc_cus` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`CustomerId`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `htc_ht` FOREIGN KEY (`TripTo`,`DepartureDate`,`HotelId`) REFERENCES `hotel_trip` (`TripTo`, `DepartureDate`, `HotelId`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `languages`
--
ALTER TABLE `languages`
  ADD CONSTRAINT `lg_tg` FOREIGN KEY (`GuideId`) REFERENCES `tourguide` (`GuideId`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `menu_order`
--
ALTER TABLE `menu_order`
  ADD CONSTRAINT `menu_order_ibfk_1` FOREIGN KEY (`menu_mtype`,`menu_id`) REFERENCES `menu` (`mtype`, `mid`) ON UPDATE CASCADE,
  ADD CONSTRAINT `menu_order_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `person` (`id`);

--
-- Filtros para la tabla `optional_excursion`
--
ALTER TABLE `optional_excursion`
  ADD CONSTRAINT `oe_t` FOREIGN KEY (`TripTo`,`DepartureDate`) REFERENCES `trip` (`TripTo`, `DepartureDate`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `depnum` FOREIGN KEY (`Dnum`) REFERENCES `department` (`Dnumber`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `restaurant`
--
ALTER TABLE `restaurant`
  ADD CONSTRAINT `fk_reportsresuls` FOREIGN KEY (`reportsresults2`) REFERENCES `restaurant` (`restaurname`);

--
-- Filtros para la tabla `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `fk_restaurname_sales` FOREIGN KEY (`restaurname`) REFERENCES `restaurant` (`restaurname`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `serves`
--
ALTER TABLE `serves`
  ADD CONSTRAINT `fk_restaurname_serves` FOREIGN KEY (`restaurname`) REFERENCES `restaurant` (`restaurname`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_serves_dish` FOREIGN KEY (`dish`) REFERENCES `dishes` (`dish`);

--
-- Filtros para la tabla `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `ht_tg` FOREIGN KEY (`GuideId`) REFERENCES `tourguide` (`GuideId`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Filtros para la tabla `works_on`
--
ALTER TABLE `works_on`
  ADD CONSTRAINT `Essn_Ssn` FOREIGN KEY (`Essn`) REFERENCES `employee` (`Ssn`),
  ADD CONSTRAINT `pnum` FOREIGN KEY (`Pno`) REFERENCES `project` (`Pnumber`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
