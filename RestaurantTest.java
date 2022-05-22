import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.listeners.MockitoListener;
import java.util.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setupTestcase()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurant1 = Mockito.spy(restaurant);
        LocalTime present = LocalTime.parse("20:00:00");
        Mockito.when(restaurant1.getCurrentTime()).thenReturn(present);
        assertTrue(restaurant1.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurant1 = Mockito.spy(restaurant);
        LocalTime present = LocalTime.parse("09:00:00");
        Mockito.when(restaurant1.getCurrentTime()).thenReturn(present);
        assertFalse(restaurant1.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<Price>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void adding_2_items_to_menu_finding_total_cost_of_2_items() throws itemNotFoundException
    {
        restaurant.addToMenu("Dosa", 100);
        restaurant.addToMenu("Paratha", 150);

        ArrayList<String> items = new ArrayList<>();
        items.add("Dosa");
        items.add("Paratha");
        int totalPrice = restaurant.getCost(items);
        assertEquals(250, totalPrice);
    }

    @Test
    public void adding_2_items_to_menu_finding_total_cost_of_not_added_item_should_raise_exception() throws itemNotFoundException
    {
        restaurant.addToMenu("Dosa", 100);
        restaurant.addToMenu("Paratha", 150);

        ArrayList<String> items = new ArrayList<>();
        items.add("Dosa");
        items.add("Paratha");
        items.add("Roti");
        assertThrows(itemNotFoundException.class, ()->restaurant.getCost(items));
    }

    @Test
    public void adding_3_items_to_menu_finding_total_cost_with_one_repeated_item_should_exclude_repeated_item() throws itemNotFoundException
    {
        restaurant.addToMenu("Dosa", 100);
        restaurant.addToMenu("Paratha", 150);
        restaurant.addToMenu("Roti", 50);

        ArrayList<String> items = new ArrayList<>();
        items.add("Dosa");
        items.add("Paratha");
        items.add("Roti");
        items.add("Roti");

        int totalPrice = restaurant.getCost(items);
        assertEquals(300, totalPrice);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<Price>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}