package util;

import exeption.ConstraintViolation;
import exeption.ConstraintViolationException;
import model.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerValidator implements Validator<Worker> {

    @Override
    public void validate(Worker worker) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();

        int firstName = worker.getFirstName().trim().length();
        if (firstName < 2 || firstName > 50) {
            violations.add(new ConstraintViolation(worker.getClass().getName(), "firstName", worker.getFirstName(),
                    "Worker first name should be between 2 and 50 characters long."));
        }

        int lastName = worker.getLastName().length();
        if (lastName < 2 || lastName > 50) {
            violations.add(new ConstraintViolation(worker.getClass().getName(), "lastName", worker.getLastName(),
                    "Worker last Name should be between 2 and 50 characters long."));
        }

        int code = worker.getCode().trim().length();
        if (code < 2) {
            violations.add(new ConstraintViolation(worker.getClass().getName(), "code", worker.getCode(),
                    "Worker Code should be at least 2 characters long."));
        }

        if(violations.size() > 0) {
            throw new ConstraintViolationException("Invalid book field", violations);
        }
    }
}
