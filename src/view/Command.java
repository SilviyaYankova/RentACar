package view;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;

public interface Command {
    String execute() throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException, NoPermissionException;
}
