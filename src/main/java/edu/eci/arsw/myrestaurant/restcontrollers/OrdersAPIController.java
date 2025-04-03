package edu.eci.arsw.myrestaurant.controllers;

import edu.eci.arsw.myrestaurant.beans.BillCalculator;
import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersAPIController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private BillCalculator billCalculator;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderServices.getOrders();
        return orders.stream().map(order -> {
            double total = billCalculator.calculateBill(order);
            return new OrderDTO(order, total);
        }).collect(Collectors.toList());
    }

    public static class OrderDTO {
        private Order order;
        private double total;

        public OrderDTO(Order order, double total) {
            this.order = order;
            this.total = total;
        }

        public Order getOrder() {
            return order;
        }

        public double getTotal() {
            return total;
        }
    }
}