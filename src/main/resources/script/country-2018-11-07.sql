/*
Navicat MySQL Data Transfer

Source Server         : localhostMySQL
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : his_new

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-07 15:58:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  `capital` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `iso2` varchar(255) DEFAULT NULL,
  `iso3` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_code` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES ('1', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kabul', '4', 'AFN', 'AF', 'AFG', 'Afghanistan', '93', '');
INSERT INTO `country` VALUES ('2', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Mariehamn', '248', 'EUR', 'AX', 'ALA', 'Aland Islands', '+358-18', '');
INSERT INTO `country` VALUES ('3', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tirana', '8', 'ALL', 'AL', 'ALB', 'Albania', '355', '');
INSERT INTO `country` VALUES ('4', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Algiers', '12', 'DZD', 'DZ', 'DZA', 'Algeria', '213', '');
INSERT INTO `country` VALUES ('5', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Pago Pago', '16', 'USD', 'AS', 'ASM', 'American Samoa', '+1-684', '');
INSERT INTO `country` VALUES ('6', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Andorra la Vella', '20', 'EUR', 'AD', 'AND', 'Andorra', '376', '');
INSERT INTO `country` VALUES ('7', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Luanda', '24', 'AOA', 'AO', 'AGO', 'Angola', '244', '');
INSERT INTO `country` VALUES ('8', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'The Valley', '660', 'XCD', 'AI', 'AIA', 'Anguilla', '+1-264', '');
INSERT INTO `country` VALUES ('9', '2018-07-20 14:41:03', '2018-07-20 14:41:03', '', '10', '', 'AQ', 'ATA', 'Antarctica', '', '');
INSERT INTO `country` VALUES ('10', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'St. John\'s', '28', 'XCD', 'AG', 'ATG', 'Antigua And Barbuda', '+1-268', '');
INSERT INTO `country` VALUES ('11', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Buenos Aires', '32', 'ARS', 'AR', 'ARG', 'Argentina', '54', '');
INSERT INTO `country` VALUES ('12', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Yerevan', '51', 'AMD', 'AM', 'ARM', 'Armenia', '374', '');
INSERT INTO `country` VALUES ('13', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Oranjestad', '533', 'AWG', 'AW', 'ABW', 'Aruba', '297', '');
INSERT INTO `country` VALUES ('14', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Canberra', '36', 'AUD', 'AU', 'AUS', 'Australia', '61', '');
INSERT INTO `country` VALUES ('15', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Vienna', '40', 'EUR', 'AT', 'AUT', 'Austria', '43', '');
INSERT INTO `country` VALUES ('16', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Baku', '31', 'AZN', 'AZ', 'AZE', 'Azerbaijan', '994', '');
INSERT INTO `country` VALUES ('17', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nassau', '44', 'BSD', 'BS', 'BHS', 'Bahamas The', '+1-242', '');
INSERT INTO `country` VALUES ('18', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Manama', '48', 'BHD', 'BH', 'BHR', 'Bahrain', '973', '');
INSERT INTO `country` VALUES ('19', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Dhaka', '50', 'BDT', 'BD', 'BGD', 'Bangladesh', '880', '');
INSERT INTO `country` VALUES ('20', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bridgetown', '52', 'BBD', 'BB', 'BRB', 'Barbados', '+1-246', '');
INSERT INTO `country` VALUES ('21', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Minsk', '112', 'BYR', 'BY', 'BLR', 'Belarus', '375', '');
INSERT INTO `country` VALUES ('22', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Brussels', '56', 'EUR', 'BE', 'BEL', 'Belgium', '32', '');
INSERT INTO `country` VALUES ('23', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Belmopan', '84', 'BZD', 'BZ', 'BLZ', 'Belize', '501', '');
INSERT INTO `country` VALUES ('24', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Porto-Novo', '204', 'XOF', 'BJ', 'BEN', 'Benin', '229', '');
INSERT INTO `country` VALUES ('25', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Hamilton', '60', 'BMD', 'BM', 'BMU', 'Bermuda', '+1-441', '');
INSERT INTO `country` VALUES ('26', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Thimphu', '64', 'BTN', 'BT', 'BTN', 'Bhutan', '975', '');
INSERT INTO `country` VALUES ('27', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Sucre', '68', 'BOB', 'BO', 'BOL', 'Bolivia', '591', '');
INSERT INTO `country` VALUES ('28', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Sarajevo', '70', 'BAM', 'BA', 'BIH', 'Bosnia and Herzegovina', '387', '');
INSERT INTO `country` VALUES ('29', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Gaborone', '72', 'BWP', 'BW', 'BWA', 'Botswana', '267', '');
INSERT INTO `country` VALUES ('30', '2018-07-20 14:41:03', '2018-07-20 14:41:03', '', '74', 'NOK', 'BV', 'BVT', 'Bouvet Island', '', '');
INSERT INTO `country` VALUES ('31', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Brasilia', '76', 'BRL', 'BR', 'BRA', 'Brazil', '55', '');
INSERT INTO `country` VALUES ('32', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Diego Garcia', '86', 'USD', 'IO', 'IOT', 'British Indian Ocean Territory', '246', '');
INSERT INTO `country` VALUES ('33', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bandar Seri Begawan', '96', 'BND', 'BN', 'BRN', 'Brunei', '673', '');
INSERT INTO `country` VALUES ('34', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Sofia', '100', 'BGN', 'BG', 'BGR', 'Bulgaria', '359', '');
INSERT INTO `country` VALUES ('35', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Ouagadougou', '854', 'XOF', 'BF', 'BFA', 'Burkina Faso', '226', '');
INSERT INTO `country` VALUES ('36', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bujumbura', '108', 'BIF', 'BI', 'BDI', 'Burundi', '257', '');
INSERT INTO `country` VALUES ('37', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Phnom Penh', '116', 'KHR', 'KH', 'KHM', 'Cambodia', '855', '');
INSERT INTO `country` VALUES ('38', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Yaounde', '120', 'XAF', 'CM', 'CMR', 'Cameroon', '237', '');
INSERT INTO `country` VALUES ('39', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Ottawa', '124', 'CAD', 'CA', 'CAN', 'Canada', '1', '');
INSERT INTO `country` VALUES ('40', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Praia', '132', 'CVE', 'CV', 'CPV', 'Cape Verde', '238', '');
INSERT INTO `country` VALUES ('41', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'George Town', '136', 'KYD', 'KY', 'CYM', 'Cayman Islands', '+1-345', '');
INSERT INTO `country` VALUES ('42', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bangui', '140', 'XAF', 'CF', 'CAF', 'Central African Republic', '236', '');
INSERT INTO `country` VALUES ('43', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'N\'Djamena', '148', 'XAF', 'TD', 'TCD', 'Chad', '235', '');
INSERT INTO `country` VALUES ('44', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Santiago', '152', 'CLP', 'CL', 'CHL', 'Chile', '56', '');
INSERT INTO `country` VALUES ('45', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Beijing', '156', 'CNY', 'CN', 'CHN', 'China', '86', '');
INSERT INTO `country` VALUES ('46', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Flying Fish Cove', '162', 'AUD', 'CX', 'CXR', 'Christmas Island', '61', '');
INSERT INTO `country` VALUES ('47', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'West Island', '166', 'AUD', 'CC', 'CCK', 'Cocos (Keeling) Islands', '61', '');
INSERT INTO `country` VALUES ('48', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bogota', '170', 'COP', 'CO', 'COL', 'Colombia', '57', '');
INSERT INTO `country` VALUES ('49', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Moroni', '174', 'KMF', 'KM', 'COM', 'Comoros', '269', '');
INSERT INTO `country` VALUES ('50', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Brazzaville', '178', 'XAF', 'CG', 'COG', 'Congo', '242', '');
INSERT INTO `country` VALUES ('51', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kinshasa', '180', 'CDF', 'CD', 'COD', 'Congo The Democratic Republic Of The', '243', '');
INSERT INTO `country` VALUES ('52', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Avarua', '184', 'NZD', 'CK', 'COK', 'Cook Islands', '682', '');
INSERT INTO `country` VALUES ('53', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'San Jose', '188', 'CRC', 'CR', 'CRI', 'Costa Rica', '506', '');
INSERT INTO `country` VALUES ('54', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Yamoussoukro', '384', 'XOF', 'CI', 'CIV', 'Cote D\'Ivoire (Ivory Coast)', '225', '');
INSERT INTO `country` VALUES ('55', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Zagreb', '191', 'HRK', 'HR', 'HRV', 'Croatia (Hrvatska)', '385', '');
INSERT INTO `country` VALUES ('56', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Havana', '192', 'CUP', 'CU', 'CUB', 'Cuba', '53', '');
INSERT INTO `country` VALUES ('57', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nicosia', '196', 'EUR', 'CY', 'CYP', 'Cyprus', '357', '');
INSERT INTO `country` VALUES ('58', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Prague', '203', 'CZK', 'CZ', 'CZE', 'Czech Republic', '420', '');
INSERT INTO `country` VALUES ('59', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Copenhagen', '208', 'DKK', 'DK', 'DNK', 'Denmark', '45', '');
INSERT INTO `country` VALUES ('60', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Djibouti', '262', 'DJF', 'DJ', 'DJI', 'Djibouti', '253', '');
INSERT INTO `country` VALUES ('61', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Roseau', '212', 'XCD', 'DM', 'DMA', 'Dominica', '+1-767', '');
INSERT INTO `country` VALUES ('62', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Santo Domingo', '214', 'DOP', 'DO', 'DOM', 'Dominican Republic', '+1-809 and 1-829', '');
INSERT INTO `country` VALUES ('63', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Dili', '626', 'USD', 'TL', 'TLS', 'East Timor', '670', '');
INSERT INTO `country` VALUES ('64', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Quito', '218', 'USD', 'EC', 'ECU', 'Ecuador', '593', '');
INSERT INTO `country` VALUES ('65', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Cairo', '818', 'EGP', 'EG', 'EGY', 'Egypt', '20', '');
INSERT INTO `country` VALUES ('66', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'San Salvador', '222', 'USD', 'SV', 'SLV', 'El Salvador', '503', '');
INSERT INTO `country` VALUES ('67', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Malabo', '226', 'XAF', 'GQ', 'GNQ', 'Equatorial Guinea', '240', '');
INSERT INTO `country` VALUES ('68', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Asmara', '232', 'ERN', 'ER', 'ERI', 'Eritrea', '291', '');
INSERT INTO `country` VALUES ('69', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tallinn', '233', 'EUR', 'EE', 'EST', 'Estonia', '372', '');
INSERT INTO `country` VALUES ('70', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Addis Ababa', '231', 'ETB', 'ET', 'ETH', 'Ethiopia', '251', '');
INSERT INTO `country` VALUES ('71', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Stanley', '238', 'FKP', 'FK', 'FLK', 'Falkland Islands', '500', '');
INSERT INTO `country` VALUES ('72', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Torshavn', '234', 'DKK', 'FO', 'FRO', 'Faroe Islands', '298', '');
INSERT INTO `country` VALUES ('73', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Suva', '242', 'FJD', 'FJ', 'FJI', 'Fiji Islands', '679', '');
INSERT INTO `country` VALUES ('74', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Helsinki', '246', 'EUR', 'FI', 'FIN', 'Finland', '358', '');
INSERT INTO `country` VALUES ('75', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Paris', '250', 'EUR', 'FR', 'FRA', 'France', '33', '');
INSERT INTO `country` VALUES ('76', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Cayenne', '254', 'EUR', 'GF', 'GUF', 'French Guiana', '594', '');
INSERT INTO `country` VALUES ('77', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Papeete', '258', 'XPF', 'PF', 'PYF', 'French Polynesia', '689', '');
INSERT INTO `country` VALUES ('78', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Port-aux-Francais', '260', 'EUR', 'TF', 'ATF', 'French Southern Territories', '', '');
INSERT INTO `country` VALUES ('79', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Libreville', '266', 'XAF', 'GA', 'GAB', 'Gabon', '241', '');
INSERT INTO `country` VALUES ('80', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Banjul', '270', 'GMD', 'GM', 'GMB', 'Gambia The', '220', '');
INSERT INTO `country` VALUES ('81', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tbilisi', '268', 'GEL', 'GE', 'GEO', 'Georgia', '995', '');
INSERT INTO `country` VALUES ('82', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Berlin', '276', 'EUR', 'DE', 'DEU', 'Germany', '49', '');
INSERT INTO `country` VALUES ('83', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Accra', '288', 'GHS', 'GH', 'GHA', 'Ghana', '233', '');
INSERT INTO `country` VALUES ('84', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Gibraltar', '292', 'GIP', 'GI', 'GIB', 'Gibraltar', '350', '');
INSERT INTO `country` VALUES ('85', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Athens', '300', 'EUR', 'GR', 'GRC', 'Greece', '30', '');
INSERT INTO `country` VALUES ('86', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nuuk', '304', 'DKK', 'GL', 'GRL', 'Greenland', '299', '');
INSERT INTO `country` VALUES ('87', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'St. George\'s', '308', 'XCD', 'GD', 'GRD', 'Grenada', '+1-473', '');
INSERT INTO `country` VALUES ('88', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Basse-Terre', '312', 'EUR', 'GP', 'GLP', 'Guadeloupe', '590', '');
INSERT INTO `country` VALUES ('89', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Hagatna', '316', 'USD', 'GU', 'GUM', 'Guam', '+1-671', '');
INSERT INTO `country` VALUES ('90', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Guatemala City', '320', 'GTQ', 'GT', 'GTM', 'Guatemala', '502', '');
INSERT INTO `country` VALUES ('91', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'St Peter Port', '831', 'GBP', 'GG', 'GGY', 'Guernsey and Alderney', '+44-1481', '');
INSERT INTO `country` VALUES ('92', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Conakry', '324', 'GNF', 'GN', 'GIN', 'Guinea', '224', '');
INSERT INTO `country` VALUES ('93', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bissau', '624', 'XOF', 'GW', 'GNB', 'Guinea-Bissau', '245', '');
INSERT INTO `country` VALUES ('94', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Georgetown', '328', 'GYD', 'GY', 'GUY', 'Guyana', '592', '');
INSERT INTO `country` VALUES ('95', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Port-au-Prince', '332', 'HTG', 'HT', 'HTI', 'Haiti', '509', '');
INSERT INTO `country` VALUES ('96', '2018-07-20 14:41:03', '2018-07-20 14:41:03', '', '334', 'AUD', 'HM', 'HMD', 'Heard and McDonald Islands', ' ', '');
INSERT INTO `country` VALUES ('97', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tegucigalpa', '340', 'HNL', 'HN', 'HND', 'Honduras', '504', '');
INSERT INTO `country` VALUES ('98', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Hong Kong', '344', 'HKD', 'HK', 'HKG', 'Hong Kong S.A.R.', '852', '');
INSERT INTO `country` VALUES ('99', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Budapest', '348', 'HUF', 'HU', 'HUN', 'Hungary', '36', '');
INSERT INTO `country` VALUES ('100', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Reykjavik', '352', 'ISK', 'IS', 'ISL', 'Iceland', '354', '');
INSERT INTO `country` VALUES ('101', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'New Delhi', '356', 'INR', 'IN', 'IND', 'India', '91', '');
INSERT INTO `country` VALUES ('102', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Jakarta', '360', 'IDR', 'ID', 'IDN', 'Indonesia', '62', '');
INSERT INTO `country` VALUES ('103', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tehran', '364', 'IRR', 'IR', 'IRN', 'Iran', '98', '');
INSERT INTO `country` VALUES ('104', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Baghdad', '368', 'IQD', 'IQ', 'IRQ', 'Iraq', '964', '');
INSERT INTO `country` VALUES ('105', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Dublin', '372', 'EUR', 'IE', 'IRL', 'Ireland', '353', '');
INSERT INTO `country` VALUES ('106', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Jerusalem', '376', 'ILS', 'IL', 'ISR', 'Israel', '972', '');
INSERT INTO `country` VALUES ('107', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Rome', '380', 'EUR', 'IT', 'ITA', 'Italy', '39', '');
INSERT INTO `country` VALUES ('108', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kingston', '388', 'JMD', 'JM', 'JAM', 'Jamaica', '+1-876', '');
INSERT INTO `country` VALUES ('109', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tokyo', '392', 'JPY', 'JP', 'JPN', 'Japan', '81', '');
INSERT INTO `country` VALUES ('110', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Saint Helier', '832', 'GBP', 'JE', 'JEY', 'Jersey', '+44-1534', '');
INSERT INTO `country` VALUES ('111', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Amman', '400', 'JOD', 'JO', 'JOR', 'Jordan', '962', '');
INSERT INTO `country` VALUES ('112', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Astana', '398', 'KZT', 'KZ', 'KAZ', 'Kazakhstan', '7', '');
INSERT INTO `country` VALUES ('113', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nairobi', '404', 'KES', 'KE', 'KEN', 'Kenya', '254', '');
INSERT INTO `country` VALUES ('114', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tarawa', '296', 'AUD', 'KI', 'KIR', 'Kiribati', '686', '');
INSERT INTO `country` VALUES ('115', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Pyongyang', '408', 'KPW', 'KP', 'PRK', 'Korea North\n', '850', '');
INSERT INTO `country` VALUES ('116', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Seoul', '410', 'KRW', 'KR', 'KOR', 'Korea South', '82', '');
INSERT INTO `country` VALUES ('117', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kuwait City', '414', 'KWD', 'KW', 'KWT', 'Kuwait', '965', '');
INSERT INTO `country` VALUES ('118', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bishkek', '417', 'KGS', 'KG', 'KGZ', 'Kyrgyzstan', '996', '');
INSERT INTO `country` VALUES ('119', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Vientiane', '418', 'LAK', 'LA', 'LAO', 'Laos', '856', '');
INSERT INTO `country` VALUES ('120', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Riga', '428', 'EUR', 'LV', 'LVA', 'Latvia', '371', '');
INSERT INTO `country` VALUES ('121', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Beirut', '422', 'LBP', 'LB', 'LBN', 'Lebanon', '961', '');
INSERT INTO `country` VALUES ('122', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Maseru', '426', 'LSL', 'LS', 'LSO', 'Lesotho', '266', '');
INSERT INTO `country` VALUES ('123', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Monrovia', '430', 'LRD', 'LR', 'LBR', 'Liberia', '231', '');
INSERT INTO `country` VALUES ('124', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tripolis', '434', 'LYD', 'LY', 'LBY', 'Libya', '218', '');
INSERT INTO `country` VALUES ('125', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Vaduz', '438', 'CHF', 'LI', 'LIE', 'Liechtenstein', '423', '');
INSERT INTO `country` VALUES ('126', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Vilnius', '440', 'LTL', 'LT', 'LTU', 'Lithuania', '370', '');
INSERT INTO `country` VALUES ('127', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Luxembourg', '442', 'EUR', 'LU', 'LUX', 'Luxembourg', '352', '');
INSERT INTO `country` VALUES ('128', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Macao', '446', 'MOP', 'MO', 'MAC', 'Macau S.A.R.', '853', '');
INSERT INTO `country` VALUES ('129', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Skopje', '807', 'MKD', 'MK', 'MKD', 'Macedonia', '389', '');
INSERT INTO `country` VALUES ('130', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Antananarivo', '450', 'MGA', 'MG', 'MDG', 'Madagascar', '261', '');
INSERT INTO `country` VALUES ('131', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Lilongwe', '454', 'MWK', 'MW', 'MWI', 'Malawi', '265', '');
INSERT INTO `country` VALUES ('132', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kuala Lumpur', '458', 'MYR', 'MY', 'MYS', 'Malaysia', '60', '');
INSERT INTO `country` VALUES ('133', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Male', '462', 'MVR', 'MV', 'MDV', 'Maldives', '960', '');
INSERT INTO `country` VALUES ('134', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bamako', '466', 'XOF', 'ML', 'MLI', 'Mali', '223', '');
INSERT INTO `country` VALUES ('135', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Valletta', '470', 'EUR', 'MT', 'MLT', 'Malta', '356', '');
INSERT INTO `country` VALUES ('136', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Douglas, Isle of Man', '833', 'GBP', 'IM', 'IMN', 'Man (Isle of)', '+44-1624', '');
INSERT INTO `country` VALUES ('137', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Majuro', '584', 'USD', 'MH', 'MHL', 'Marshall Islands', '692', '');
INSERT INTO `country` VALUES ('138', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Fort-de-France', '474', 'EUR', 'MQ', 'MTQ', 'Martinique', '596', '');
INSERT INTO `country` VALUES ('139', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nouakchott', '478', 'MRO', 'MR', 'MRT', 'Mauritania', '222', '');
INSERT INTO `country` VALUES ('140', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Port Louis', '480', 'MUR', 'MU', 'MUS', 'Mauritius', '230', '');
INSERT INTO `country` VALUES ('141', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Mamoudzou', '175', 'EUR', 'YT', 'MYT', 'Mayotte', '262', '');
INSERT INTO `country` VALUES ('142', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Mexico City', '484', 'MXN', 'MX', 'MEX', 'Mexico', '52', '');
INSERT INTO `country` VALUES ('143', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Palikir', '583', 'USD', 'FM', 'FSM', 'Micronesia', '691', '');
INSERT INTO `country` VALUES ('144', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Chisinau', '498', 'MDL', 'MD', 'MDA', 'Moldova', '373', '');
INSERT INTO `country` VALUES ('145', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Monaco', '492', 'EUR', 'MC', 'MCO', 'Monaco', '377', '');
INSERT INTO `country` VALUES ('146', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Ulan Bator', '496', 'MNT', 'MN', 'MNG', 'Mongolia', '976', '');
INSERT INTO `country` VALUES ('147', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Podgorica', '499', 'EUR', 'ME', 'MNE', 'Montenegro', '382', '');
INSERT INTO `country` VALUES ('148', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Plymouth', '500', 'XCD', 'MS', 'MSR', 'Montserrat', '+1-664', '');
INSERT INTO `country` VALUES ('149', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Rabat', '504', 'MAD', 'MA', 'MAR', 'Morocco', '212', '');
INSERT INTO `country` VALUES ('150', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Maputo', '508', 'MZN', 'MZ', 'MOZ', 'Mozambique', '258', '');
INSERT INTO `country` VALUES ('151', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nay Pyi Taw', '104', 'MMK', 'MM', 'MMR', 'Myanmar', '95', '');
INSERT INTO `country` VALUES ('152', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Windhoek', '516', 'NAD', 'NA', 'NAM', 'Namibia', '264', '');
INSERT INTO `country` VALUES ('153', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Yaren', '520', 'AUD', 'NR', 'NRU', 'Nauru', '674', '');
INSERT INTO `country` VALUES ('154', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kathmandu', '524', 'NPR', 'NP', 'NPL', 'Nepal', '977', '');
INSERT INTO `country` VALUES ('155', '2018-07-20 14:41:03', '2018-07-20 14:41:03', '', '530', '', 'AN', 'ANT', 'Netherlands Antilles', '', '');
INSERT INTO `country` VALUES ('156', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Amsterdam', '528', 'EUR', 'NL', 'NLD', 'Netherlands The', '31', '');
INSERT INTO `country` VALUES ('157', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Noumea', '540', 'XPF', 'NC', 'NCL', 'New Caledonia', '687', '');
INSERT INTO `country` VALUES ('158', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Wellington', '554', 'NZD', 'NZ', 'NZL', 'New Zealand', '64', '');
INSERT INTO `country` VALUES ('159', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Managua', '558', 'NIO', 'NI', 'NIC', 'Nicaragua', '505', '');
INSERT INTO `country` VALUES ('160', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Niamey', '562', 'XOF', 'NE', 'NER', 'Niger', '227', '');
INSERT INTO `country` VALUES ('161', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Abuja', '566', 'NGN', 'NG', 'NGA', 'Nigeria', '234', '');
INSERT INTO `country` VALUES ('162', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Alofi', '570', 'NZD', 'NU', 'NIU', 'Niue', '683', '');
INSERT INTO `country` VALUES ('163', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kingston', '574', 'AUD', 'NF', 'NFK', 'Norfolk Island', '672', '');
INSERT INTO `country` VALUES ('164', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Saipan', '580', 'USD', 'MP', 'MNP', 'Northern Mariana Islands', '+1-670', '');
INSERT INTO `country` VALUES ('165', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Oslo', '578', 'NOK', 'NO', 'NOR', 'Norway', '47', '');
INSERT INTO `country` VALUES ('166', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Muscat', '512', 'OMR', 'OM', 'OMN', 'Oman', '968', '');
INSERT INTO `country` VALUES ('167', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Islamabad', '586', 'PKR', 'PK', 'PAK', 'Pakistan', '92', '');
INSERT INTO `country` VALUES ('168', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Melekeok', '585', 'USD', 'PW', 'PLW', 'Palau', '680', '');
INSERT INTO `country` VALUES ('169', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'East Jerusalem', '275', 'ILS', 'PS', 'PSE', 'Palestinian Territory Occupied', '970', '');
INSERT INTO `country` VALUES ('170', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Panama City', '591', 'PAB', 'PA', 'PAN', 'Panama', '507', '');
INSERT INTO `country` VALUES ('171', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Port Moresby', '598', 'PGK', 'PG', 'PNG', 'Papua new Guinea', '675', '');
INSERT INTO `country` VALUES ('172', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Asuncion', '600', 'PYG', 'PY', 'PRY', 'Paraguay', '595', '');
INSERT INTO `country` VALUES ('173', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Lima', '604', 'PEN', 'PE', 'PER', 'Peru', '51', '');
INSERT INTO `country` VALUES ('174', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Manila', '608', 'PHP', 'PH', 'PHL', 'Philippines', '63', '');
INSERT INTO `country` VALUES ('175', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Adamstown', '612', 'NZD', 'PN', 'PCN', 'Pitcairn Island', '870', '');
INSERT INTO `country` VALUES ('176', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Warsaw', '616', 'PLN', 'PL', 'POL', 'Poland', '48', '');
INSERT INTO `country` VALUES ('177', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Lisbon', '620', 'EUR', 'PT', 'PRT', 'Portugal', '351', '');
INSERT INTO `country` VALUES ('178', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'San Juan', '630', 'USD', 'PR', 'PRI', 'Puerto Rico', '+1-787 and 1-939', '');
INSERT INTO `country` VALUES ('179', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Doha', '634', 'QAR', 'QA', 'QAT', 'Qatar', '974', '');
INSERT INTO `country` VALUES ('180', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Saint-Denis', '638', 'EUR', 'RE', 'REU', 'Reunion', '262', '');
INSERT INTO `country` VALUES ('181', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bucharest', '642', 'RON', 'RO', 'ROU', 'Romania', '40', '');
INSERT INTO `country` VALUES ('182', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Moscow', '643', 'RUB', 'RU', 'RUS', 'Russia', '7', '');
INSERT INTO `country` VALUES ('183', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kigali', '646', 'RWF', 'RW', 'RWA', 'Rwanda', '250', '');
INSERT INTO `country` VALUES ('184', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Jamestown', '654', 'SHP', 'SH', 'SHN', 'Saint Helena', '290', '');
INSERT INTO `country` VALUES ('185', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Basseterre', '659', 'XCD', 'KN', 'KNA', 'Saint Kitts And Nevis', '+1-869', '');
INSERT INTO `country` VALUES ('186', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Castries', '662', 'XCD', 'LC', 'LCA', 'Saint Lucia', '+1-758', '');
INSERT INTO `country` VALUES ('187', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Saint-Pierre', '666', 'EUR', 'PM', 'SPM', 'Saint Pierre and Miquelon', '508', '');
INSERT INTO `country` VALUES ('188', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kingstown', '670', 'XCD', 'VC', 'VCT', 'Saint Vincent And The Grenadines', '+1-784', '');
INSERT INTO `country` VALUES ('189', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Gustavia', '652', 'EUR', 'BL', 'BLM', 'Saint-Barthelemy', '590', '');
INSERT INTO `country` VALUES ('190', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Marigot', '663', 'EUR', 'MF', 'MAF', 'Saint-Martin (French part)', '590', '');
INSERT INTO `country` VALUES ('191', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Apia', '882', 'WST', 'WS', 'WSM', 'Samoa', '685', '');
INSERT INTO `country` VALUES ('192', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'San Marino', '674', 'EUR', 'SM', 'SMR', 'San Marino', '378', '');
INSERT INTO `country` VALUES ('193', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Sao Tome', '678', 'STD', 'ST', 'STP', 'Sao Tome and Principe', '239', '');
INSERT INTO `country` VALUES ('194', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Riyadh', '682', 'SAR', 'SA', 'SAU', 'Saudi Arabia', '966', '');
INSERT INTO `country` VALUES ('195', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Dakar', '686', 'XOF', 'SN', 'SEN', 'Senegal', '221', '');
INSERT INTO `country` VALUES ('196', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Belgrade', '688', 'RSD', 'RS', 'SRB', 'Serbia', '381', '');
INSERT INTO `country` VALUES ('197', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Victoria', '690', 'SCR', 'SC', 'SYC', 'Seychelles', '248', '');
INSERT INTO `country` VALUES ('198', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Freetown', '694', 'SLL', 'SL', 'SLE', 'Sierra Leone', '232', '');
INSERT INTO `country` VALUES ('199', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Singapur', '702', 'SGD', 'SG', 'SGP', 'Singapore', '65', '');
INSERT INTO `country` VALUES ('200', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bratislava', '703', 'EUR', 'SK', 'SVK', 'Slovakia', '421', '');
INSERT INTO `country` VALUES ('201', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Ljubljana', '705', 'EUR', 'SI', 'SVN', 'Slovenia', '386', '');
INSERT INTO `country` VALUES ('202', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Honiara', '90', 'SBD', 'SB', 'SLB', 'Solomon Islands', '677', '');
INSERT INTO `country` VALUES ('203', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Mogadishu', '706', 'SOS', 'SO', 'SOM', 'Somalia', '252', '');
INSERT INTO `country` VALUES ('204', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Pretoria', '710', 'ZAR', 'ZA', 'ZAF', 'South Africa', '27', '');
INSERT INTO `country` VALUES ('205', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Grytviken', '239', 'GBP', 'GS', 'SGS', 'South Georgia', '', '');
INSERT INTO `country` VALUES ('206', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Juba', '728', 'SSP', 'SS', 'SSD', 'South Sudan', '211', '');
INSERT INTO `country` VALUES ('207', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Madrid', '724', 'EUR', 'ES', 'ESP', 'Spain', '34', '');
INSERT INTO `country` VALUES ('208', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Colombo', '144', 'LKR', 'LK', 'LKA', 'Sri Lanka', '94', '');
INSERT INTO `country` VALUES ('209', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Khartoum', '736', 'SDG', 'SD', 'SDN', 'Sudan', '249', '');
INSERT INTO `country` VALUES ('210', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Paramaribo', '740', 'SRD', 'SR', 'SUR', 'Suriname', '597', '');
INSERT INTO `country` VALUES ('211', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Longyearbyen', '744', 'NOK', 'SJ', 'SJM', 'Svalbard And Jan Mayen Islands', '47', '');
INSERT INTO `country` VALUES ('212', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Mbabane', '748', 'SZL', 'SZ', 'SWZ', 'Swaziland', '268', '');
INSERT INTO `country` VALUES ('213', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Stockholm', '752', 'SEK', 'SE', 'SWE', 'Sweden', '46', '');
INSERT INTO `country` VALUES ('214', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Berne', '756', 'CHF', 'CH', 'CHE', 'Switzerland', '41', '');
INSERT INTO `country` VALUES ('215', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Damascus', '760', 'SYP', 'SY', 'SYR', 'Syria', '963', '');
INSERT INTO `country` VALUES ('216', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Taipei', '158', 'TWD', 'TW', 'TWN', 'Taiwan', '886', '');
INSERT INTO `country` VALUES ('217', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Dushanbe', '762', 'TJS', 'TJ', 'TJK', 'Tajikistan', '992', '');
INSERT INTO `country` VALUES ('218', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Dodoma', '834', 'TZS', 'TZ', 'TZA', 'Tanzania', '255', '');
INSERT INTO `country` VALUES ('219', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Bangkok', '764', 'THB', 'TH', 'THA', 'Thailand', '66', '');
INSERT INTO `country` VALUES ('220', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Lome', '768', 'XOF', 'TG', 'TGO', 'Togo', '228', '');
INSERT INTO `country` VALUES ('221', '2018-07-20 14:41:03', '2018-07-20 14:41:03', '', '772', 'NZD', 'TK', 'TKL', 'Tokelau', '690', '');
INSERT INTO `country` VALUES ('222', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Nuku\'alofa', '776', 'TOP', 'TO', 'TON', 'Tonga', '676', '');
INSERT INTO `country` VALUES ('223', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Port of Spain', '780', 'TTD', 'TT', 'TTO', 'Trinidad And Tobago', '+1-868', '');
INSERT INTO `country` VALUES ('224', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tunis', '788', 'TND', 'TN', 'TUN', 'Tunisia', '216', '');
INSERT INTO `country` VALUES ('225', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Ankara', '792', 'TRY', 'TR', 'TUR', 'Turkey', '90', '');
INSERT INTO `country` VALUES ('226', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Ashgabat', '795', 'TMT', 'TM', 'TKM', 'Turkmenistan', '993', '');
INSERT INTO `country` VALUES ('227', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Cockburn Town', '796', 'USD', 'TC', 'TCA', 'Turks And Caicos Islands', '+1-649', '');
INSERT INTO `country` VALUES ('228', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Funafuti', '798', 'AUD', 'TV', 'TUV', 'Tuvalu', '688', '');
INSERT INTO `country` VALUES ('229', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kampala', '800', 'UGX', 'UG', 'UGA', 'Uganda', '256', '');
INSERT INTO `country` VALUES ('230', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Kiev', '804', 'UAH', 'UA', 'UKR', 'Ukraine', '380', '');
INSERT INTO `country` VALUES ('231', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Abu Dhabi', '784', 'AED', 'AE', 'ARE', 'United Arab Emirates', '971', '');
INSERT INTO `country` VALUES ('232', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'London', '826', 'GBP', 'GB', 'GBR', 'United Kingdom', '44', '');
INSERT INTO `country` VALUES ('233', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Washington', '840', 'USD', 'US', 'USA', 'United state', '1', '');
INSERT INTO `country` VALUES ('234', '2018-07-20 14:41:03', '2018-07-20 14:41:03', '', '581', 'USD', 'UM', 'UMI', 'United state Minor Outlying Islands', '1', '');
INSERT INTO `country` VALUES ('235', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Montevideo', '858', 'UYU', 'UY', 'URY', 'Uruguay', '598', '');
INSERT INTO `country` VALUES ('236', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Tashkent', '860', 'UZS', 'UZ', 'UZB', 'Uzbekistan', '998', '');
INSERT INTO `country` VALUES ('237', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Port Vila', '548', 'VUV', 'VU', 'VUT', 'Vanuatu', '678', '');
INSERT INTO `country` VALUES ('238', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Vatican City', '336', 'EUR', 'VA', 'VAT', 'Vatican City State (Holy See)', '379', '');
INSERT INTO `country` VALUES ('239', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Caracas', '862', 'VEF', 'VE', 'VEN', 'Venezuela', '58', '');
INSERT INTO `country` VALUES ('240', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Hanoi', '704', 'VND', 'VN', 'VNM', 'Vietnam', '84', '');
INSERT INTO `country` VALUES ('241', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Road Town', '92', 'USD', 'VG', 'VGB', 'Virgin Islands (British)', '+1-284', '');
INSERT INTO `country` VALUES ('242', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Charlotte Amalie', '850', 'USD', 'VI', 'VIR', 'Virgin Islands (US)', '+1-340', '');
INSERT INTO `country` VALUES ('243', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Mata Utu', '876', 'XPF', 'WF', 'WLF', 'Wallis And Futuna Islands', '681', '');
INSERT INTO `country` VALUES ('244', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'El-Aaiun', '732', 'MAD', 'EH', 'ESH', 'Western Sahara', '212', '');
INSERT INTO `country` VALUES ('245', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Sanaa', '887', 'YER', 'YE', 'YEM', 'Yemen', '967', '');
INSERT INTO `country` VALUES ('246', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Lusaka', '894', 'ZMK', 'ZM', 'ZMB', 'Zambia', '260', '');
INSERT INTO `country` VALUES ('247', '2018-07-20 14:41:03', '2018-07-20 14:41:03', 'Harare', '716', 'ZWL', 'ZW', 'ZWE', 'Zimbabwe', '263', '');
