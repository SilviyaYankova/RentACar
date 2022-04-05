package service.impl;

import dao.CommentRepository;
import dao.UserRepository;
import exeption.ConstraintViolationException;
import exeption.InvalidEntityDataException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Comment;
import model.user.User;
import service.CarService;
import util.CommentValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class CommentServiceImpl implements service.CommentService {

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
        user.getComments().add(comment);
        try {
            userRepository.update(user);
            userRepository.save();
        } catch (NoneExistingEntityException e) {
            e.printStackTrace();
        }

        Car car = comment.getCar();
        car.getComments().add(comment);

        double rating = car.getRating() + comment.getRating();
        rating = rating / car.getComments().size();

        car.setRating(rating);
        carService.editCar(car);


        try {
            commentValidator.validate(comment);
        } catch (ConstraintViolationException ex) {
            throw new InvalidEntityDataException("Error creating comment", ex);
        }
        commentRepository.create(comment);
        commentRepository.save();

    }

    @Override
    public Collection<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void editComment(Comment comment, Car car) throws NoneExistingEntityException {
        calculateRating(car);
        carService.editCar(comment.getCar());
        comment.setEditedOn(LocalDateTime.now());
        commentRepository.update(comment);
    }


    @Override
    public void deleteComment(Long id) throws NoneExistingEntityException {
        Comment comment = commentRepository.findById(id);
        User user = comment.getUser();
        user.getComments().remove(comment);
        userRepository.update(user);
        System.out.println();

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

    @Override
    public void loadData() {
        commentRepository.load();
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
