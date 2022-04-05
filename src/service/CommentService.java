package service;

import exeption.InvalidEntityDataException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Comment;

import java.util.Collection;

public interface CommentService {

    void addCarComment(Comment comment) throws InvalidEntityDataException, NoneExistingEntityException;

    Collection<Comment> getAllComments();

    void editComment(Comment comment) throws NoneExistingEntityException;

    void deleteComment(Long id) throws NoneExistingEntityException;

    Comment getCommentById(Long id) throws NoneExistingEntityException;

    void loadData();
}
