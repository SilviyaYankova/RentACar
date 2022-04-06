package cource.project.view.Menu;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class Menu {
    private String title;
    private List<Option> options = List.of(new Option("Exit", new ExitCommand()));
    Scanner scanner = new Scanner(System.in);

    public Menu() {
    }

    public Menu(String title, List<Option> options) {
        this.title = title;
        List<Option> oldOptions = this.options;
        this.options = new ArrayList<>();
        this.options.addAll(options);
        this.options.addAll(oldOptions);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu menu = (Menu) o;

        if (getTitle() != null ? !getTitle().equals(menu.getTitle()) : menu.getTitle() != null) return false;
        return getOptions() != null ? getOptions().equals(menu.getOptions()) : menu.getOptions() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getOptions() != null ? getOptions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Menu.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("options=" + options)
                .toString();
    }

    public void show() throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException {
        while (true) {
            System.out.printf("MENU: %s%n", title);
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%2d. %s%n", i + 1, options.get(i).getText());
            }

            int choice = -1;
            do {
                System.out.printf("Enter your choice(1 - %s)%n", options.size());
                String s = scanner.nextLine();
                try {
                    choice = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid choice. Please enter valid number between 1 and " + options.size());
                }
            } while (choice < 1 || choice > options.size());

            options.get(choice - 1).getCommand().execute() ;
            if (choice == options.size()) {
                System.out.println("You exited program.");
                break;
            }
        }
    }
}
