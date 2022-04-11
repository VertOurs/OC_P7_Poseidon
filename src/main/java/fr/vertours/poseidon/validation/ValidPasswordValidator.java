package fr.vertours.poseidon.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

//    @Override
//    public boolean isValid(String password, ConstraintValidatorContext context) {
//        PasswordValidator validator = new PasswordValidator(Arrays.asList(
//                new LengthRule(8, 30),
//                new LowercaseCharacterRule(1),
//                new UppercaseCharacterRule(1),
//                new DigitCharacterRule(1),
//                new SpecialCharacterRule(1),
//                new WhitespaceRule()));
//
//        RuleResult result = validator.validate(new PasswordData(password));
//        if (result.isValid()) {
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        String regex = "^"          // start of Regex
                + "(?=.*\\d)"       // a digit must occur at least once
                + "(?=.*[a-z])"     // a lower case letter must occur at least once
                + "(?=.*[A-Z])"     // an upper case letter must occur at least once
                + ".{4,8}"          // 4-8 character password, both inclusive
                + "$"               // end of the string
                ;
        Pattern pattern = Pattern.compile(regex);

        if(pattern.matcher(password).matches()) {
            return true;
        }

        return false;
    }
}
