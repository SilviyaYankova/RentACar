package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.user.User;
import view.EditUserDialog;
import view.Menu;
import view.Option;

import java.util.List;

public class StatisticController {


    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        Menu menu = new Menu("Statistics Menu", List.of(
                new Option("Users", () -> {

                    return "";
                }),
                new Option("Cars", () -> {

                    return "";
                }),
                new Option("Orders", () -> {

                    return "";
                }),
                new Option("Comments", () -> {

                    return "";
                }),
                new Option("Total profit", () -> {

                    return "";
                }),
                new Option("Total profit for period", () -> {

                    return "";
                })
        ));

        menu.show();
    }
}
