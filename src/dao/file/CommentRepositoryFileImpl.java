package dao.file;

import dao.CommentRepository;
import dao.IdGenerator;
import dao.OrderRepository;
import dao.impl.PersistableRepositoryFileImpl;
import model.Comment;
import model.Order;

public class CommentRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Comment> implements CommentRepository {

    public CommentRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
