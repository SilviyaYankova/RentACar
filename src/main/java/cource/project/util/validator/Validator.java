package cource.project.util.validator;

import cource.project.exeption.ConstraintViolationException;
import cource.project.model.user.User;

public interface Validator<K> {

    void validate(K entity) throws ConstraintViolationException;
}
