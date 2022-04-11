package fr.vertours.poseidon.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
public @interface ValidPassword {

    String message() default "Please enter a Password that follows the OWASP standard";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
