SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `user` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR (100) NOT NULL,
    `email` VARCHAR (100) NOT NULL UNIQUE,
    `password` VARCHAR (255) NOT NULL,
    `cpf_cnpj` VARCHAR(20) UNIQUE,
    `phone_number` VARCHAR(20) NOT NULL ,
    `active` BOOLEAN DEFAULT TRUE,
    `avatar_url` VARCHAR(255) NULL,
    `bio` TEXT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `role` (
    `id` TINYINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` ENUM('ADMIN','SPEAKER','LISTENER','ORGANIZER') NOT NULL UNIQUE,
    `description` VARCHAR(255)
)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id` INT NOT NULL,
    `role_id` TINYINT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `speaker_details` (
    `user_id` INT NOT NULL PRIMARY KEY,
    `resume` TEXT NOT NULL,
    `specialization` VARCHAR (100) NOT NULL,
    `linkedin` VARCHAR(255) NOT NULL,
    `other_social_networks` VARCHAR(255),
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_speaker_deleted` (`deleted_at`)

)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `listener_details` (
    `user_id` INT NOT NULL PRIMARY KEY,
    `company` VARCHAR (100) NOT NULL,
    `position` VARCHAR (100) NOT NULL ,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_listener_deleted` (`deleted_at`)
)ENGINE=InnoDB;



CREATE TABLE IF NOT EXISTS `organizer_details` (
    `user_id` INT NOT NULL PRIMARY KEY,
    `company_name` VARCHAR (100) NOT NULL ,
    `brand` VARCHAR (100) NOT NULL,
    `industry_of_business` VARCHAR(100) NOT NULL,
    `website` VARCHAR (100) NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX `idx_organizer_deleted` (`deleted_at`)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `event` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NOT NULL,
    `location` VARCHAR(255) NOT NULL,
    `capacity` INT NOT NULL,
    `status` ENUM('DRAFT', 'PUBLISHED', 'CANCELED', 'COMPLETED') NOT NULL DEFAULT 'DRAFT',
    `registration_value` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    `organizer_id` INT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    CONSTRAINT `chk_dates` CHECK ( `end_date` > `start_date` ),
     CONSTRAINT `fk_event_organizer`
            FOREIGN KEY (`organizer_id`) REFERENCES `user` (`id`)

)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `subscription` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `event_id` INT NOT NULL,
    `status` ENUM('PENDING', 'CONFIRMED', 'CANCELED') NOT NULL,
    `amount_paid` DECIMAL(10,2) NOT NULL,
    `listener_id` INT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    CONSTRAINT `fk_subscription_event`
        FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE RESTRICT,
    CONSTRAINT `fk_listener_id`
        FOREIGN KEY (`listener_id`) REFERENCES `user` (`id`),
    CONSTRAINT `chk_amount` CHECK (`amount_paid` >= 0)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `rating` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `subscription_id` INT NOT NULL,
    `score` INT NOT NULL,
    `comment` TEXT,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    CONSTRAINT `fk_rating_subscription`
        FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`),
    CONSTRAINT `chk_score_range` CHECK (`score` BETWEEN 1 AND 5)

)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `certificate` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `subscription_id` INT NOT NULL,
    `file_name` VARCHAR(255) NOT NULL,
    `file_key` VARCHAR(255) NOT NULL,
    `certified_hours` INT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`),
        CONSTRAINT `chk_certificate_hours` CHECK (`certified_hours` > 0)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `activity` (
    `id`INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `event_id` INT NOT NULL,
    `speaker_id` INT NULL,
    `title` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `start_date_time` DATETIME NOT NULL,
    `end_date_time` DATETIME NOT NULL,
    `location` VARCHAR(100),
    `hours_certified` INT DEFAULT 0,
    `is_published` BOOLEAN DEFAULT FALSE,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    CONSTRAINT `fk_activity_event`
        FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
    CONSTRAINT `fk_activity_speaker`
        FOREIGN KEY (`speaker_id`) REFERENCES `user` (`id`),
    CONSTRAINT `chk_activity_dates`
        CHECK (`end_date_time` > `start_date_time`)

)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `complementary_material` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `activity_id` INT NOT NULL,
    `title` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `url` VARCHAR(255) NOT NULL,
    `type` ENUM('PDF', 'VIDEO', 'LINK', 'DOCUMENT', 'PRESENTATION') NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `payment` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `subscription_id` INT NOT NULL,
    `method` ENUM('CREDIT_CARD', 'PIX', 'BANK_SLIP', 'TRANSFER') NOT NULL,
    `status` ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    `amount` DECIMAL(10,2) NOT NULL,
    `payment_date` DATETIME DEFAULT NULL,
    `gateway_id` VARCHAR(255),
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `plan` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `type` ENUM('FREE', 'BASIC', 'PRO', 'ENTERPRISE') NOT NULL UNIQUE,
    `description` TEXT,
    `price` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    `event_limit` INT,
    `max_listeners` INT,
    `storage_mb` INT, -- Espaço para materiais (ex: 100MB)
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL
) ENGINE=InnoDB;

-- armazena a assinatura ativa do usuário com data de início e término
CREATE TABLE IF NOT EXISTS `user_plan` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `plan_id` INT NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE, -- Null indica plano ativo indefinidamente
    `last_payment_date` DATE NULL,
    `next_payment_date` DATE NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
    FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`) ON DELETE RESTRICT,
    CONSTRAINT `chk_user_plan_dates` CHECK (`end_date` IS NULL OR `end_date` > `start_date`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `invoice` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_plan_id` INT NOT NULL,
    `issue_date` DATE NOT NULL,  -- Data de emissão
    `due_date` DATE NOT NULL,  -- Data de vencimento
    `amount` DECIMAL(10,2) NOT NULL,  -- Valor
    `status` ENUM('PENDING', 'PAID', 'OVERDUE', 'CANCELED') NOT NULL DEFAULT 'PENDING',
    `payment_method` ENUM('CREDIT_CARD', 'PIX', 'BANK_SLIP', 'TRANSFER') NULL,
    `payment_date` DATE NULL,
    `gateway_id` VARCHAR(255) NULL,  -- ID do gateway (ex: Mercado Pago)
    `billing_cycle` VARCHAR(20) NOT NULL,  -- Ex: "MONTHLY", "YEARLY"
    `discount` DECIMAL(10,2) DEFAULT 0.00,  -- Descontos quando houver
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME NULL DEFAULT NULL,
    FOREIGN KEY (`user_plan_id`) REFERENCES `user_plan` (`id`) ON DELETE RESTRICT,
    CONSTRAINT `chk_invoice_dates` CHECK (`due_date` >= `issue_date`),
    CONSTRAINT `chk_invoice_amount` CHECK (`amount` > 0)
) ENGINE=InnoDB;

-- indices
CREATE INDEX `idx_user_email` ON `user` (`email`);
CREATE INDEX `idx_event_status` ON `event` (`status`);
CREATE INDEX `idx_subscription_status` ON `subscription` (`status`);
CREATE INDEX `idx_payment_status` ON `payment` (`status`);

SET FOREIGN_KEY_CHECKS = 1;

-- povoa enums e valores fixos

INSERT INTO `role` (`name`, `description`) VALUES
    ('ADMIN', 'Administrador do sistema'),
    ('SPEAKER', 'Palestrante em eventos'),
    ('LISTENER', 'Participante ouvinte'),
    ('ORGANIZER', 'Organizador de eventos');

INSERT INTO `plan` (`type`, `description`, `price`, `event_limit`, `max_listeners`, `storage_mb` )
VALUES
    ('FREE',  '1 evento/mês, 50 participantes', 0.00, 1, 50, 10),
    ('BASIC',  '3 eventos/mês, 100 participantes', 99.90, 3, 100, 50),
    ('PRO',  'Eventos ilimitados, 500 participantes', 299.90, NULL, 500, 200);