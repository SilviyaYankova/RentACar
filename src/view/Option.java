package view;

import java.util.StringJoiner;

public class Option {
    private String text;
    public Command command;

    public Option(String text, Command command) {
        this.text = text;
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Option.class.getSimpleName() + "[", "]")
                .add("text='" + text + "'")
                .add("command=" + command)
                .toString();
    }
}
