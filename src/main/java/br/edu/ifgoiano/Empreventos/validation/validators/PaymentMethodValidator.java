package br.edu.ifgoiano.Empreventos.validation.validators;



import br.edu.ifgoiano.Empreventos.model.Invoice;
import br.edu.ifgoiano.Empreventos.validation.annotations.ValidPaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Validação de @NotNull deve ser separada
        }

        try {
            // Tenta converter pelo nome do enum (português)
            Invoice.PaymentMethod.valueOf(value);
            return true;
        } catch (IllegalArgumentException e1) {
            try {
                // Tenta converter pelo valor do banco (inglês)
                Invoice.PaymentMethod.fromValorBanco(value);
                return true;
            } catch (IllegalArgumentException e2) {
                return false;
            }
        }
    }

}