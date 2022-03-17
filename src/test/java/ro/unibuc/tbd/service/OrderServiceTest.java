package ro.unibuc.tbd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.model.Order;
import ro.unibuc.tbd.repository.ClientRepository;
import ro.unibuc.tbd.repository.MealRepository;
import ro.unibuc.tbd.repository.OrderRepository;
import ro.unibuc.tbd.repository.RestaurantRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    MealRepository mealRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("4fdsajkl423fjdsak23");
        order.setClientId("jh4k32l1h43123h21");
        Map<String, Integer> hm = new HashMap<String, Integer>();
        hm.put("Spaghetti Carbonara", 2);
        hm.put("Bread", 3);
        order.setMeals(hm);
        order.setTotalPrice(40f);
    }

    @Test
    void getOrderById() {

    }

    @Test
    void getAllOrders() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void deleteOrderById() {
    }
}