-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: proyecto_javafx
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `favoritos`
--

DROP TABLE IF EXISTS `favoritos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favoritos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usuario_id` bigint NOT NULL,
  `nombre_receta` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `instruccion` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `imagen_url` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `fk_usuario` (`usuario_id`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favoritos`
--

LOCK TABLES `favoritos` WRITE;
/*!40000 ALTER TABLE `favoritos` DISABLE KEYS */;
INSERT INTO `favoritos` VALUES (2,1,'Pizza Express Margherita','1 Preheat the oven to 230°C.\r\n\r\n2 Add the sugar and crumble the fresh yeast into warm water.\r\n\r\n3 Allow the mixture to stand for 10 – 15 minutes in a warm place (we find a windowsill on a sunny day works best) until froth develops on the surface.\r\n\r\n4 Sift the flour and salt into a large mixing bowl, make a well in the middle and pour in the yeast mixture and olive oil.\r\n\r\n5 Lightly flour your hands, and slowly mix the ingredients together until they bind.\r\n\r\n6 Generously dust your surface with flour.\r\n\r\n7 Throw down the dough and begin kneading for 10 minutes until smooth, silky and soft.\r\n\r\n8 Place in a lightly oiled, non-stick baking tray (we use a round one, but any shape will do!)\r\n\r\n9 Spread the passata on top making sure you go to the edge.\r\n\r\n10 Evenly place the mozzarella (or other cheese) on top, season with the oregano and black pepper, then drizzle with a little olive oil.\r\n\r\n11 Cook in the oven for 10 – 12 minutes until the cheese slightly colours.\r\n\r\n12 When ready, place the basil leaf on top and tuck in!','https://www.themealdb.com/images/media/meals/x0lk931587671540.jpg'),(3,1,'Lasagne','Heat the oil in a large saucepan. Use kitchen scissors to snip the bacon into small pieces, or use a sharp knife to chop it on a chopping board. Add the bacon to the pan and cook for just a few mins until starting to turn golden. Add the onion, celery and carrot, and cook over a medium heat for 5 mins, stirring occasionally, until softened.\r\nAdd the garlic and cook for 1 min, then tip in the mince and cook, stirring and breaking it up with a wooden spoon, for about 6 mins until browned all over.\r\nStir in the tomato purée and cook for 1 min, mixing in well with the beef and vegetables. Tip in the chopped tomatoes. Fill each can half full with water to rinse out any tomatoes left in the can, and add to the pan. Add the honey and season to taste. Simmer for 20 mins.\r\nHeat oven to 200C/180C fan/gas 6. To assemble the lasagne, ladle a little of the ragu sauce into the bottom of the roasting tin or casserole dish, spreading the sauce all over the base. Place 2 sheets of lasagne on top of the sauce overlapping to make it fit, then repeat with more sauce and another layer of pasta. Repeat with a further 2 layers of sauce and pasta, finishing with a layer of pasta.\r\nPut the crème fraîche in a bowl and mix with 2 tbsp water to loosen it and make a smooth pourable sauce. Pour this over the top of the pasta, then top with the mozzarella. Sprinkle Parmesan over the top and bake for 25–30 mins until golden and bubbling. Serve scattered with basil, if you like.','https://www.themealdb.com/images/media/meals/wtsvxx1511296896.jpg'),(32,1,'Big Mac','For the Big Mac sauce, combine all the ingredients in a bowl, season with salt and chill until ready to use.\r\n2. To make the patties, season the mince with salt and pepper and form into 4 balls using about 1/3 cup mince each. Place each onto a square of baking paper and flatten to form into four x 15cm circles. Heat oil in a large frypan over high heat. In 2 batches, cook beef patties for 1-2 minutes each side until lightly charred and cooked through. Remove from heat and keep warm. Repeat with remaining two patties.\r\n3. Carefully slice each burger bun into three acrossways, then lightly toast.\r\n4. To assemble the burgers, spread a little Big Mac sauce over the bottom base. Top with some chopped onion, shredded lettuce, slice of cheese, beef patty and some pickle slices. Top with the middle bun layer, and spread with more Big Mac sauce, onion, lettuce, pickles, beef patty and then finish with more sauce. Top with burger lid to serve.\r\n5. After waiting half an hour for your food to settle, go for a jog.','https://www.themealdb.com/images/media/meals/urzj1d1587670726.jpg'),(38,1,'Bistek','0.	Marinate beef in soy sauce, lemon (or calamansi), and ground black pepper for at least 1 hour. Note: marinate overnight for best result\r\n1.	Heat the cooking oil in a pan then pan-fry half of the onions until the texture becomes soft. Set aside\r\n2.	Drain the marinade from the beef. Set it aside. Pan-fry the beef on the same pan where the onions were fried for 1 minute per side. Remove from the pan. Set aside\r\n3.	Add more oil if needed. Saute garlic and remaining raw onions until onion softens.\r\n4.	Pour the remaining marinade and water. Bring to a boil.\r\n5.	Add beef. Cover the pan and simmer until the meat is tender. Note: Add water as needed.\r\n6.	Season with ground black pepper and salt as needed. Top with pan-fried onions.\r\n7.	Transfer to a serving plate. Serve hot. Share and Enjoy!\r\n','https://www.themealdb.com/images/media/meals/4pqimk1683207418.jpg');
/*!40000 ALTER TABLE `favoritos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recetas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `imagen_url` varchar(255) DEFAULT NULL,
  `instrucciones` text,
  `nombre` varchar(255) NOT NULL,
  `usuario_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5den0yfgja9aa8ldx0ke9c3j` (`usuario_id`),
  CONSTRAINT `FK5den0yfgja9aa8ldx0ke9c3j` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recetas`
--

LOCK TABLES `recetas` WRITE;
/*!40000 ALTER TABLE `recetas` DISABLE KEYS */;
/*!40000 ALTER TABLE `recetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tu_receta`
--

DROP TABLE IF EXISTS `tu_receta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tu_receta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usuario_id` bigint NOT NULL,
  `nombre_receta` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `instruccion` text COLLATE utf8mb4_unicode_ci,
  `imagen_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `tu_receta_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tu_receta`
--

LOCK TABLES `tu_receta` WRITE;
/*!40000 ALTER TABLE `tu_receta` DISABLE KEYS */;
INSERT INTO `tu_receta` VALUES (2,1,'Tortilla de Patatas','Ingredientes para 4 personas: \n   - 4 patatas medianas, \n   - 4 ó 5 huevos, \n   - 1 cebolla mediana, \n   - ¼ l de aceite de oliva y sal.\n\nPelar, lavar y secar las patatas. \nCortarlas en rodajas finas e introducirlas en una sartén honda con el aceite de oliva a temperatura media \npara que se cuezan lentamente. \n\nAgregar la cebolla cortada finita. Freírla lentamente con las patatas\n hasta que estén blandas y empiecen a dorarse. \n\nConviene remover con la espumadera y cortar con la misma los ingredientes. \n\nAl cabo de unos 10 minutos se escurre el aceite.\n\nBatir en un cuenco los huevos con un poco de sal. \n\nAgregar la mezcla de las patatas con la cebolla. \n\nPoner de nuevo la sartén con un poco del aceite escurrido y añadir toda la mezcla. \n\nDejarlo a fuego lento tapando la sartén durante unos 5 ó 10 minutos, hasta que se dore por abajo. \n\nDar la vuelta a la tortilla sobre sí misma con la ayuda de una tapadera y dejarla caer por el otro lado \nen la sartén hasta que se cuaje despacio. \n\nHa de quedar dorada por ambos lados.','https://img.freepik.com/fotos-premium/tortilla-espanola-tradicional-mesa-madera_123827-1635.jpg'),(3,2,'canelones','carne \npasta','https://img.freepik.com/foto-gratis/deliciosa-pasta-plato_23-2150690697.jpg'),(4,1,'Pollo al Limón','Ingredientes Para 4 personas:\n- Pechuga de pollo x2\n- Limón x2\n- Maizena 2 cucharadas\n- Aceite de girasol para freír el pollo\n- Azúcar 2 cucharadas\n- Caldo de pollo 100 ml\n- Salsa de soja 50 ml\n\nCortamos tres rodajas finas de limón y exprimimos el resto de los dos limones. En un cazo, ponemos a cocer el zumo de limón con el azúcar, el caldo, una cucharada de Maizena y las tres rodajas, dejando a fuego lento hasta que espese como un jarabe.\n\nMarinamos en la salsa de soja durante 30 minutos las pechugas de pollo muy limpias y cortadas por la mitad para que no sean tan gruesas. Mezclamos la otra cucharada de Maizena con el huevo batido y con esa mezcla empanamos las pechugas escurridas y las freímos hasta que se doren.\n\nCortamos las pechugas en pequeñas tiras para facilitar al comensal que pueda comerlas con palillos chinos. En una fuente, ponemos las tres rodajas de limón alineadas y las cubrimos con la mitad de la salsa de limón.\n\nColocamos el pollo encima de las rodajas de limón, echamos el resto de la salsa por encima y decoramos con unas tiritas de corteza de limón y flores comestibles, llevándolo a la mesa inmediatamente para que llegue muy caliente.','https://i.blogs.es/2005c1/pollo_limon/1366_2000.jpg'),(7,1,'Tarta de Queso Philadelphia','Ingredientes Para 8 personas:\n- Queso blanco cremoso para untar estilo Philadelphia 800 g\n- Huevos de tamaño L x4\n- Maicena15 g\n- Azúcar 200 g\n- Nata líquida 400 ml\n- Galletas tipo María para la base125 g\n- Mantequilla para la base 60 g\n\nCon esta receta nos van a salir unas ocho o diez raciones,\ndependiendo de lo glotones que seamos en casa, \nasí que necesitaremos un molde desmontable de 26 centímetros\n y también un horno con ventilador. \n\nAdemás, para acelerar el proceso, podemos recurrir al robot de cocina.\n\nEn él vamos a triturar las galletas con la mantequilla\ndurante un par de minutos para que quede homogéneo \ny cuando esté esta masa lista, la ponemos en la base del molde \napretando bien y llevando al congelador entre veinte minutos \ny media hora.\n\nMientras tanto, precalentamos el horno a 170 ºC \ny vamos triturando el resto de ingredientes \nen el robot de cocina hasta que quede bien homogéneo. \n\nSacamos el molde frío del congelador, rellenamos \ncon el resto de la mezcla, y lo horneamos\n33 minutos a 170 ºC con el ventilador puesto.\n\nCuando haya pasado ese tiempo, dejamos reposar\na temperatura ambiente para que la tarta se asiente\ny cuaje, y así no se desmoronará por completo \nal cortarla. Si queréis que os quede menos cuajada\nsolo tenéis que recortar algún minuto a la cocción\ny, si la queréis más estable, aumentarla ligeramente \no dejar más reposo.','https://i.blogs.es/6ad7a5/tarta-de-queso-philadelphia2/1366_2000.jpg'),(8,1,'Prueba de Receta Sin Imagen','Probando uno dos tres cuatro','');
/*!40000 ALTER TABLE `tu_receta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuarios_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Sergio','sergio@example.com','1234'),(2,'María Gutierrez','maria.gutierrez@example.com','5678'),(3,'Carlos Ramírez','carlos.ramirez@example.com','9876'),(4,'Frank Cuesta','frank@example.com','1234'),(5,'Frodo Bolsón','frodo@example.com','1234');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'proyecto_javafx'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-01 14:54:36
