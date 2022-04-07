package cource.project.dao.impl;

import cource.project.dao.CommentRepository;
import cource.project.model.Comment;

import java.sql.Connection;

public class CommentRepositoryImpl extends AbstractRepository<Long, Comment> implements CommentRepository {


    protected CommentRepositoryImpl(Connection connection) {
        super(connection);
    }
}
