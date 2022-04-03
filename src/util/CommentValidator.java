package util;

import exeption.ConstraintViolation;
import exeption.ConstraintViolationException;
import model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentValidator implements Validator<Comment> {


    @Override
    public void validate(Comment comment) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();

        int length = comment.getContent().length();
        if (length < 10 || length > 200) {
            violations.add(
                    new ConstraintViolation(comment.getClass().getName(), "length", comment.getContent(),
                            "Comment length should be between 10 and 200 characters long."));
        }

        double rating = comment.getRating();
        if (rating <= 0) {
            violations.add(
                    new ConstraintViolation(comment.getClass().getName(), "rating", comment.getRating(),
                            "Comment Rating should be a positive number."));
        }

        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid book field", violations);
        }
    }
}
