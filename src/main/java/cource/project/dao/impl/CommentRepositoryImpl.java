package cource.project.dao.impl;

import cource.project.dao.CommentRepository;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Comment;

import java.sql.Connection;
import java.util.Collection;

public class CommentRepositoryImpl implements CommentRepository {

    protected final Connection connection;
    protected CommentRepositoryImpl(Connection connection) {
       this.connection = connection;
    }

    @Override
    public Comment create(Comment entity) {
        return null;
    }

    @Override
    public Comment findById(Long id) {
        return null;
    }

    @Override
    public Collection<Comment> findAll() {
        return null;
    }

    @Override
    public void update(Comment entity) throws NoneExistingEntityException {

    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {

    }
}
