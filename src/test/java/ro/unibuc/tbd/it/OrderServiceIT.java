package ro.unibuc.tbd.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.model.Order;
import ro.unibuc.tbd.repository.ClientRepository;
import ro.unibuc.tbd.repository.MealRepository;
import ro.unibuc.tbd.repository.OrderRepository;
import ro.unibuc.tbd.service.ClientService;
import ro.unibuc.tbd.service.MealService;
import ro.unibuc.tbd.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class OrderServiceIT {

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    MealRepository mealRepository;

    @MockBean
    ClientRepository clientRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ClientService clientService;

    @Autowired
    MealService mealService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("4fdsajkl423fjdsak23");
        order.setClientId("jh4k32l1h43123h21");
        Map<String, Integer> hm = new HashMap<>();
        hm.put("gfddgf4325s01583f06eff", 2);
        order.setMeals(hm);
        order.setTotalPrice(40f);
    }

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        mealRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void createOrder() {
        // Arrange
        Client client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");
        clientService.createClient(client);


        Meal meal = new Meal();
        meal.setId("gfddgf4325s01583f06eff");
        meal.setName("Spaghetti Carbonara");
        List<String> ingredients = List.of("spaghetti", "eggs", "parmesan", "pepper");
        meal.setIngredients(ingredients);
        meal.setPrice(22f);
        meal.setPortionSize(220);
        meal.setRestaurantId("fdsfadsfewsds2342f");
        mealService.createMeal(meal);

        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        when(mealRepository.findById(any())).thenReturn(Optional.of(meal));

        // Act
        Order result = orderService.createOrder(order);

        // Assert
        verify(orderRepository, times(1)).save(any());
        assertEquals(result.getTotalPrice(), order.getTotalPrice());
        assertEquals(result.getClientId(), order.getClientId());
        assertEquals(result.getMeals(), order.getMeals());
    }

    @Test
    void createOrder_ThrowsWhenMealNotFound() {
        // Arrange
        Client client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");
        clientService.createClient(client);

        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        when(mealRepository.findById(any())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResponseStatusException.class,
                () -> orderService.createOrder(order),
                "Expected createOrder() to throw a ResponseStatusException, but it didn't.");
    }
}
