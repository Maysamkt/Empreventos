-- Altera ENUMs da tabela 'invoice' para português
ALTER TABLE `invoice`
    MODIFY COLUMN `status` ENUM('PENDENTE','PAGO','ATRASADO','CANCELADO') NOT NULL DEFAULT 'PENDENTE',
    MODIFY COLUMN `payment_method` ENUM('CARTAO_CREDITO','PIX','BOLETO','TRANSFERENCIA') NULL;

-- Atualiza os valores existentes na tabela 'invoice' para português
UPDATE `invoice` SET `status` = 'PENDENTE' WHERE `status` = 'PENDING';
UPDATE `invoice` SET `status` = 'PAGO' WHERE `status` = 'PAID';
UPDATE `invoice` SET `status` = 'ATRASADO' WHERE `status` = 'OVERDUE';
UPDATE `invoice` SET `status` = 'CANCELADO' WHERE `status` = 'CANCELED';

UPDATE `invoice` SET `payment_method` = 'CARTAO_CREDITO' WHERE `payment_method` = 'CREDIT_CARD';
UPDATE `invoice` SET `payment_method` = 'BOLETO' WHERE `payment_method` = 'BANK_SLIP';
UPDATE `invoice` SET `payment_method` = 'TRANSFERENCIA' WHERE `payment_method` = 'TRANSFER';