ALTER TABLE `invoice`
    MODIFY COLUMN `status` ENUM('PENDING','PAID','OVERDUE','CANCELED') NOT NULL DEFAULT 'PENDING',
    MODIFY COLUMN `payment_method` ENUM('CREDIT_CARD','PIX','BANK_SLIP','TRANSFER') NULL;

UPDATE `invoice` SET `status` = 'PENDING' WHERE `status` = 'PENDENTE';
UPDATE `invoice` SET `status` = 'PAID' WHERE `status` = 'PAGO';
UPDATE `invoice` SET `status` = 'OVERDUE' WHERE `status` = 'ATRASADO';
UPDATE `invoice` SET `status` = 'CANCELED' WHERE `status` = 'CANCELADO';

UPDATE `invoice` SET `payment_method` = 'CREDIT_CARD' WHERE `payment_method` = 'CARTAO_CREDITO';
UPDATE `invoice` SET `payment_method` = 'BANK_SLIP' WHERE `payment_method` = 'BOLETO';
UPDATE `invoice` SET `payment_method` = 'TRANSFER' WHERE `payment_method` = 'TRANSFERENCIA';
