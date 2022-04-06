package Controller;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Order;
import model.user.User;
import service.OrderService;
import view.Menu;
import view.Option;

import java.util.Collection;
import java.util.List;

public class StatisticController {
        private final OrderService orderService;

    public StatisticController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void init(User LOGGED_IN_USER) throws NoneAvailableEntityException, InvalidEntityDataException, NoPermissionException, NoneExistingEntityException {
        Menu menu = new Menu("Statistics Menu", List.of(
                new Option("Users", () -> {

                    return "";
                }),
                new Option("Comments", () -> {

                    return "";
                }),
                new Option("Cars", () -> {

                    return "";
                }),
                new Option("Orders", () -> {

                    return "";
                }),
                new Option("Personal Sells Statistic", () -> {
                    Collection<Order> allOrders = orderService.getAllOrders();
                    int count = 0;
                    for (Order order : allOrders) {
                        if (order.getSeller().equals(LOGGED_IN_USER)) {
                            System.out.println(count + ".\t" + order);
                        }
                    }

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
