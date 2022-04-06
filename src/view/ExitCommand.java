package view;

import view.Menu.Command;

public class ExitCommand implements Command {

    @Override
    public String execute() {
        return "Executing menu: ";
    }
}
