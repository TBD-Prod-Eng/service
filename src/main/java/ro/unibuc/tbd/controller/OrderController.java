package ro.unibuc.tbd.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.tbd.model.Order;
import ro.unibuc.tbd.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    MeterRegistry metricsRegistry;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @Timed(value = "tbd.order.time", description = "Time taken to create an order")
    @Counted(value = "tbd.order.count", description = "Number of orders")
    @PostMapping
    public Order registerOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

//    @PatchMapping("/{orderId}")
//    public Order updateOrder(@PathVariable String orderId, @RequestBody Order order) {
//        return orderService.updateOrder(orderId, order);
//    }

    @DeleteMapping("/{orderId}")
    public Order deleteOrder(@PathVariable String orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @DeleteMapping("/clear")
    public void deleteAllClients() {
        orderService.clearAll();
    }
}
