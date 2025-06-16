package br.edu.ifgoiano.Empreventos.validation.annotations;

import br.edu.ifgoiano.Empreventos.validation.validators.PaymentMethodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PaymentMethodValidator.class)
public @interface ValidPaymentMethod {
    String message() default "Método de pagamento inválido. Valores aceitos: CARTAO_CREDITO, PIX, BOLETO, TRANSFERENCIA ou seus equivalentes em inglês (CREDIT_CARD, PIX, BANK_SLIP, TRANSFER)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}