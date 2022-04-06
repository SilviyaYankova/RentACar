package cource.project.service.impl;

import cource.project.dao.CommentRepository;
import cource.project.dao.UserRepository;
import cource.project.exeption.ConstraintViolationException;
import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Comment;
import cource.project.model.user.User;
import cource.project.service.CarService;
import cource.project.service.CommentService;
import cource.project.util.validator.CommentValidator;

import java.time.LocalDateTime;
import java.util.Collection;

public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CarService carService;
    private final UserRepository userRepository;
    private final CommentValidator commentValidator;

    public CommentServiceImpl(CommentRepository commentRepository, CarService carService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.carService = carService;
        this.userRepository = userRepository;
        this.commentValidator = new CommentValidator();
    }

    @Override
    public void addCarComment(Comment comment) throws InvalidEntityDataException, NoneExistingEntityException {
        User user = comment.getUser();
        Car car = comment.getCar();
        car.getComments().add(comment);

        double rating = car.getRating() + comment.getRating();
        rating = rating / car.getComments().size();

        car.setRating(rating);

        try {
            commentValidator.validate(comment);
        } catch (ConstraintViolationException ex) {
            throw new InvalidEntityDataException("Error creating comment", ex);
        }

        commentRepository.create(comment);

        carService.editCar(car);
        user.getComments().add(comment);
        try {
            userRepository.update(user);
        } catch (NoneExistingEntityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void editComment(Comment comment ) throws NoneExistingEntityException {
        Car car = comment.getCar();
        calculateRating(car);
        carService.editCar(comment.getCar());
        comment.setEditedOn(LocalDateTime.now());
        commentRepository.update(comment);

        User user = comment.getUser();
        user.getComments().add(comment);
        userRepository.update(user);
    }


    @Override
    public void deleteComment(Long id) throws NoneExistingEntityException {
        Comment comment = commentRepository.findById(id);
        User user = comment.getUser();
        user.getComments().remove(comment);
        userRepository.update(user);

        Car car = comment.getCar();
        car.getComments().remove(comment);
        calculateRating(car);
        carService.editCar(car);

        commentRepository.deleteById(id);
    }

    @Override
    public Comment getCommentById(Long id) throws NoneExistingEntityException {
        return commentRepository.findById(id);
    }


    private void calculateRating(Car car) {
        double rating = car.getComments().stream()
                .mapToDouble(Comment::getRating).sum();

        rating /= car.getComments().size();
        if (rating > 5) {
            rating = 5;
        } else {
            car.setRating(rating);
        }
    }

}
