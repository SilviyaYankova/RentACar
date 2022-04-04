package util;

import exeption.ConstraintViolation;
import exeption.ConstraintViolationException;
import model.Order;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderValidator implements Validator<Order> {

    List<ConstraintViolation> violations = new ArrayList<>();

    @Override
    public void validate(Order order) throws ConstraintViolationException {

        LocalDateTime pickUpDate = order.getPickUpDate();

//        if (pickUpDate.isBefore(LocalDateTime.now())) {
//            violations.add(
//                    new ConstraintViolation(order.getClass().getName(), "pickUpDate", order.getPickUpDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
//                            "Pick Up Date can not be from the past."));
//        }
//
//
//        LocalDateTime dropOffDate = order.getDropOffDate();
//        if (dropOffDate.isBefore(LocalDateTime.now())) {
//            violations.add(
//                    new ConstraintViolation(order.getClass().getName(), "dropOffDate", order.getDropOffDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
//                            "Drop Off Date can not be from the past."));
//        }

        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid book field", violations);
        }
    }
}
