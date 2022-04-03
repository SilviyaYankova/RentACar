package util;

import exeption.ConstraintViolationException;
import model.user.User;

public interface Validator<K> {

    void validate(K entity) throws ConstraintViolationException;
}
