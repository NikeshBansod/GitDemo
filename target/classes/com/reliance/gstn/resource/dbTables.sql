CREATE TABLE `registration` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`sp_name` VARCHAR(50) NOT NULL,
	`cp_name` VARCHAR(50) NOT NULL,
	`contact_no` VARCHAR(50) NOT NULL,
	`primary_email` VARCHAR(50) NOT NULL,
	`password` VARCHAR(500) NOT NULL,
	`gstn_reg_number` BIGINT(20) NOT NULL,
	`pan_number` VARCHAR(50) NOT NULL,
	`created_on` DATETIME NOT NULL,
	`created_by` VARCHAR(50) NOT NULL,
	`updated_on` DATETIME NULL,
	`updated_by` VARCHAR(50) NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `secondary_agents` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`email_id` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NOT NULL,
	`reference_id` INT(11) NULL,
	`created_on` DATETIME NOT NULL,
	`created_by` VARCHAR(50) NOT NULL,
	`updated_on` DATETIME NULL ,
	`updated_by` VARCHAR(50) NULL ,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UC_secondary_agents` (`email_id`, `reference_id`),
	INDEX `reference_id` (`reference_id`),
	CONSTRAINT `secondary_agents_ibfk_1` FOREIGN KEY (`reference_id`) REFERENCES `registration` (`id`)
);

CREATE TABLE `services` (
`id` INT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`sac_code` VARCHAR(20) NOT NULL,
`sac_description` TEXT NOT NULL,
`service_rate` DOUBLE NOT NULL,
`reference_id` INT(11) NOT NULL,
`created_on` DATETIME NOT NULL,
`created_by` VARCHAR(50) NOT NULL,
`updated_on` DATETIME,
`updated_by` VARCHAR(50),
PRIMARY KEY (`id`)
);




