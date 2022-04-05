package model;

import model.user.BaseEntity;
import model.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment extends BaseEntity {
    private String content;
    private double rating;
    private User user;
    private Car car;
    private LocalDateTime postedOn;
    private LocalDateTime editedOn;

    public Comment() {
    }

    public Comment(String content, double rating, User user, Car car, LocalDateTime postedOn, LocalDateTime editedOn) {
        this.content = content;
        this.rating = rating;
        this.user = user;
        this.car = car;
        this.postedOn = postedOn;
        this.editedOn = editedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(LocalDateTime postedOn) {
        this.postedOn = postedOn;
    }

    public LocalDateTime getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(LocalDateTime editedOn) {
        this.editedOn = editedOn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(Comment.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("content = '" + content + "'")
                .append(System.lineSeparator())
                .append(String.format("\t\t rating = '%.2f' ", rating))
                .append("userId = '" + user.getId() + "' ")
                .append(String.format("brand = '%s', model = '%s'", car.getBrand(), car.getModel()))
                .append(System.lineSeparator())
                .append("\t\t postedOn = '" + postedOn.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ");

        if (editedOn != null) {
            sb.append("editedOn = '" + editedOn.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ");

        }

        return sb.toString();
    }
}
