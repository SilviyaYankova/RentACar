package cource.project.service;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Comment;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface CommentService {

    void addCarComment(Comment comment) throws InvalidEntityDataException, NoneExistingEntityException;

    Collection<Comment> getAllComments();

    void editComment(Comment comment) throws NoneExistingEntityException;

    void deleteComment(Long id) throws NoneExistingEntityException;

    Comment getCommentById(Long id) throws NoneExistingEntityException;
}
