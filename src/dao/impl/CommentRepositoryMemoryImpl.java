package dao.impl;

import dao.CommentRepository;
import dao.IdGenerator;
import model.Comment;

public class CommentRepositoryMemoryImpl extends AbstractPersistableRepository<Long, Comment> implements CommentRepository {

    public CommentRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
