package ro.unibuc.tbd.it;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.tbd.model.CartRequestDTO;
import ro.unibuc.tbd.model.Client;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.repository.ClientRepository;
import ro.unibuc.tbd.repository.MealRepository;
import ro.unibuc.tbd.service.ClientService;
import ro.unibuc.tbd.service.MealService;
import ro.unibuc.tbd.service.OrderService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class ClientServiceIT {

    @MockBean
    ClientRepository clientRepository;

    @MockBean
    MealRepository mealRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    MealService mealService;

    @Autowired
    ClientService clientService;

    private Client client;
    private Meal meal;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");

        meal = new Meal();
        meal.setId("fsdvsdjmfsd1234590231sd");
        meal.setName("Paste Carbonara");
        meal.setIngredients(List.of("Paste", "Bacon"));
        meal.setPrice((float) 27.99);
        meal.setPortionSize(400);
    }

    @AfterEach
    void cleanUp() {
        clientRepository.deleteAll();
        mealRepository.deleteAll();
    }

    @Test
    void addToCart() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));
        clientService.createClient(client);
        mealService.createMeal(meal);

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        Client result = clientService.addToCart(client.getId(), cartRequest);

        // Assert
        verify(clientRepository, times(2)).save(any());
        assertTrue(result.getCart().containsKey(meal.getId()));
    }

    @Test
    void addToCart_MealNotFound() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.empty());
        clientService.createClient(client);

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act + Assert
        assertThrows(ResponseStatusException.class,
                () -> clientService.addToCart(client.getId(), cartRequest),
                "Expected addToCart() to throw a ResponseStatusException, but it didn't.");
    }

    @Test
    void removeFromCart() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));
        clientService.createClient(client);
        mealService.createMeal(meal);

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        clientService.addToCart(client.getId(), cartRequest);
        Client result = clientService.removeFromCart(client.getId(), cartRequest);

        // Assert
        assertFalse(result.getCart().containsKey(meal.getId()));
    }

    @Test
    void clearCart() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        clientService.addToCart(client.getId(), cartRequest);
        Client result = clientService.clearCart(client.getId());

        // Assert
        verify(clientRepository, times(2)).save(any());
        assertTrue(result.getCart().isEmpty());
    }

    @Test
    void placeOrder() {
        // Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.ofNullable(client));
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));
        clientService.createClient(client);
        mealService.createMeal(meal);

        CartRequestDTO cartRequest = new CartRequestDTO();
        cartRequest.setMealId(meal.getId());

        // Act
        clientService.addToCart(client.getId(), cartRequest);
        Client result = clientService.placeOrder(client.getId());

        // Assert
        verify(clientRepository, times(3)).save(any());
        assertTrue(result.getCart().isEmpty());
    }
}