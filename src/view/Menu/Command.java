package view.Menu;

import exeption.InvalidEntityDataException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;

public interface Command {
    String execute() throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException;
}
