package ro.unibuc.tbd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.tbd.model.Meal;
import ro.unibuc.tbd.repository.MealRepository;

import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MealServiceTest {

    private Meal meal;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    MealService mealService;

    @BeforeEach
    void setUp() {
        meal = new Meal();
        meal.setId("gfddgf4325s01583f06eff");
        meal.setName("Spaghetti Carbonara");
        List<String> ingredients = List.of("spaghetti", "eggs", "parmesan", "pepper");
        meal.setIngredients(ingredients);
        meal.setPrice(22f);
        meal.setPortionSize(220);
        meal.setRestaurantId("fdsfadsfewsds2342f");
    }

    @Test
    void getMealById() {
    }

    @Test
    void getMealByName() {
    }

    @Test
    void getAllMeals() {
    }

    @Test
    void createMeal() {
        // Arrange
        when(mealRepository.save(meal)).thenReturn(meal);

        // Act
        Meal result = mealService.createMeal(meal);

        // Assert
        assertEquals(result.getRestaurantId(), meal.getRestaurantId());
        assertEquals(result.getName(), meal.getName());
        assertEquals(result.getIngredients(), meal.getIngredients());
        assertEquals(result.getPrice(), meal.getPrice());
        assertEquals(result.getPortionSize(), meal.getPortionSize());
    }

    @Test
    void updateMeal() {
    }

    @Test
    void deleteMealById() {
    }
}