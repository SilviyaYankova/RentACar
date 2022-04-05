package view;

import exeption.InvalidEntityDataException;
import model.user.User;

public interface EntityDialog<E> {
    E input() throws InvalidEntityDataException;
}
