---
sidebar_position: 2
sidebar_label: Database Containerization
---

# Database contenerization

## Creating the Staging Database

### Part 1: Setting Up the Development Database

To initialize a MySQL database locally using Docker, follow these steps to create and run the container:

#### Step 1: Start MySQL in Docker

Execute the following command to launch a MySQL container locally on port 3307:

```bash
docker run --name raisehub_database -e MYSQL_DATABASE=crowdfund_db -e MYSQL_USER=<sql_user> -e MYSQL_PASSWORD=<sql_password> -e MYSQL_ROOT_PASSWORD=<sql_root_password> -p 3307:3306 -d mysql
```

- You can remove the MYSQL_USER and MYSQL_PASSWORD environment variables if you don't want to create a new user and just use root user. MYSQL_ROOT_PASSWORD can also be removed but it is not recommended as some form of security should be set up.

:::warning
Replace `<sql_root_password>` with the desired MySQL root password and `<sql_password>` and `<sql_user>` with a secure user password.
- `raisehub_database` is a example name for the container
- `crowdfund_db` is an example name for the database but it is the name set on the **application.properties file** for database origin in the backend container

```bash, title="application.properties in the back-end container"
spring.datasource.url=jdbc:mysql://localhost:3307/crowdfund_db
```
:::

![Commands on the terminal](./img/comando_terminal.png)

After the container starts, it will have the MySQL database (`crowdfund_db`) created, accessible through any database management tool.

![MySQL Workbench connection](./img/mysqlworkbench_connection.png)

### Part 2: Creating the Database Tables

Once the MySQL container is operational, the database tables for the project need to be created. Use the following SQL script to define the database structure.

#### Step 2: Execute the SQL Script

Connect to the MySQL container and run the following SQL commands to create the required tables:

```sql
-- Dumping database structure for crowdfund_db
CREATE DATABASE IF NOT EXISTS `crowdfund_db`;
USE `crowdfund_db`;

-- Dumping structure for table crowdfund_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_picture` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Dumping structure for table crowdfund_db.projects
CREATE TABLE IF NOT EXISTS `projects` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text,
  `funding_goal` float NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `money_raised` float NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `percentage_funded` double GENERATED ALWAYS AS (((`money_raised` / `funding_goal`) * 100)) VIRTUAL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhswfwa3ga88vxv1pmboss6jhm` (`user_id`),
  CONSTRAINT `FKhswfwa3ga88vxv1pmboss6jhm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Dumping structure for table crowdfund_db.payments
CREATE TABLE IF NOT EXISTS `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `project_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7h0as5hqhn845eewc7usiy0x3` (`project_id`),
  KEY `FKj94hgy9v5fw1munb90tar2eje` (`user_id`),
  CONSTRAINT `FK7h0as5hqhn845eewc7usiy0x3` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKj94hgy9v5fw1munb90tar2eje` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Dumping structure for table crowdfund_db.project_images
CREATE TABLE IF NOT EXISTS `project_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image_order` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `project_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoej10untas4roy2rqxcmbdj42` (`project_id`),
  CONSTRAINT `FKoej10untas4roy2rqxcmbdj42` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Dumping structure for table crowdfund_db.bookmarks
CREATE TABLE IF NOT EXISTS `bookmarks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2e83pys34l1inuejvk0s0yp18` (`project_id`),
  KEY `FKdbsho2e05w5r13fkjqfjmge5f` (`user_id`),
  CONSTRAINT `FK2e83pys34l1inuejvk0s0yp18` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  CONSTRAINT `FKdbsho2e05w5r13fkjqfjmge5f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

### Part 3: Final Steps

After executing the SQL script, the database schema for the project will be established, and the tables will be created.

At this point, a fully operational container with the database and all necessary tables will be ready for development.