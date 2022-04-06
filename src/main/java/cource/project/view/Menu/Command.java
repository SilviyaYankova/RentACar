package cource.project.view.Menu;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;

public interface Command {
    String execute() throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException;
}
