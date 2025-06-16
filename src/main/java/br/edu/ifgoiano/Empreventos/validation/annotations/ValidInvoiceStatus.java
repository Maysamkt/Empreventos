package br.edu.ifgoiano.Empreventos.validation.annotations;

import br.edu.ifgoiano.Empreventos.validation.validators.InvoiceStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InvoiceStatusValidator.class)
public @interface ValidInvoiceStatus {
    String message() default "Status de fatura inválido. Valores aceitos: PENDENTE, PAGO, ATRASADO, CANCELADO ou seus equivalentes em inglês";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}