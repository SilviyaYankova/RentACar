package dao;

import dao.file.PersistableRepository;
import model.Comment;

public interface CommentRepository extends PersistableRepository<Long, Comment> {
}
