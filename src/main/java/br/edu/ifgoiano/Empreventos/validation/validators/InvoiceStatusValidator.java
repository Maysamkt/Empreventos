package br.edu.ifgoiano.Empreventos.validation.validators;

import br.edu.ifgoiano.Empreventos.model.Invoice;
import br.edu.ifgoiano.Empreventos.validation.annotations.ValidInvoiceStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InvoiceStatusValidator implements ConstraintValidator<ValidInvoiceStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // Validação de @NotNull deve ser separada

        try {
            // Tenta converter pelo nome do enum (português)
            Invoice.InvoiceStatus.valueOf(value);
            return true;
        } catch (IllegalArgumentException e1) {
            try {
                // Tenta converter pelo valor do banco (inglês)
                Invoice.InvoiceStatus.fromValorBanco(value);
                return true;
            } catch (IllegalArgumentException e2) {
                return false;
            }
        }
    }
}