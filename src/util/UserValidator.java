package util;

import exeption.ConstraintViolation;
import exeption.ConstraintViolationException;
import model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User user) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();

        int firstName = user.getFirstName().length();
        if (firstName < 2 || firstName > 50) {
            violations.add(new ConstraintViolation(user.getClass().getName(), "firstName", user.getFirstName(),
                    "User first name should be between 2 and 50 characters long."));
        }

        int lastName = user.getLastName().length();
        if (lastName < 2 || lastName > 50) {
            violations.add(new ConstraintViolation(user.getClass().getName(), "lastName", user.getLastName(),
                    "User last Name should be between 2 and 50 characters long."));
        }

        Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.find()) {
            violations.add(new ConstraintViolation(user.getClass().getName(), "email", user.getEmail(),
                    "Email must be valid."));
        }

        int phoneNumber = user.getPhoneNumber().trim().length();
        if (phoneNumber < 8 || phoneNumber > 10) {
            violations.add(new ConstraintViolation(user.getClass().getName(), "phoneNumber", user.getPhoneNumber(),
                    "PhoneNumber should be between 8 and 10 characters long."));
        }

        int username = user.getUsername().trim().length();
        if (username < 2 || username > 15) {
            violations.add(new ConstraintViolation(user.getClass().getName(), "username", user.getUsername(),
                    "Username should be between 2 and 15 characters long."));
        }

        Pattern passwordPattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$");
        Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());
        boolean correctPassword = passwordMatcher.find();
        if (!correctPassword) {
            violations.add(new ConstraintViolation(user.getClass().getName(), "username", user.getPassword(),
                    "Password length must be more than 2 and less then 15 characters long, " +
                            "contain at least one digit, one capital letter, and one sign different than letter or digit."));
        }

        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid user field", violations);
        }
    }
}
