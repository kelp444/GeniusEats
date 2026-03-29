import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestEditMeals001Test {
//normal
//min
//max
//just inside min
//just inside max

//Edit one meal in plan

//Edit one meal in plan

//highly restrictive

//out of service

    //replace perfernces

    /**
     * Tests generating a meal plan with the minimum valid number of meals (1).
     * Verifies that the service correctly creates a plan containing exactly one meal.
     * Boundary Value Analysis: minimum boundary.
     */
    @Test
    void regenerateMealPlan_withOneMeal() {
        MealPlan plan = service.generatePlan(1, preferences);
        assertEquals(1, plan.size());
    }

        /**
         * Tests generating a meal plan with a value just above the minimum boundary (2 meals).
         * Ensures the service correctly handles values just inside the valid range.
         * Boundary Value Analysis: just above minimum boundary.
         */
    @Test
    void regenerateMealPlan_withNormalMeals() {
        MealPlan plan = service.generatePlan(11, preferences);
        assertEquals(11, plan.size());
    }

        /**
         * Tests generating a meal plan with the maximum valid number of meals (21).
         * Verifies that the service correctly creates a plan at the upper boundary limit.
         * Boundary Value Analysis: maximum boundary.
         */
    @Test
    void regenerateMealPlan_withMaxMeals() {
        MealPlan plan = service.generatePlan(21, preferences);
        assertEquals(21, plan.size());
    }

        /**
         * Tests generating a meal plan with a value exceeding the maximum allowed meals.
         * Verifies that the service throws an IllegalArgumentException for invalid input.
         * Boundary Value Analysis: above maximum boundary.
         */
    @Test
    void regenerateMealPlan_withOverMaxMeals() {

        assertThrows(IllegalArgumentException.class,
                () -> service.generatePlan(25, preferences));
    }

        /**
         * Tests generating a meal plan with a value below the minimum allowed meals.
         * Verifies that the service throws an IllegalArgumentException for invalid input.
         * Boundary Value Analysis: below minimum boundary.
         */
    @Test
    void regenerateMealPlan_withUnderMinMeals() {

        assertThrows(IllegalArgumentException.class,
                () -> service.generatePlan(0, preferences));
    }


        /**
         * Tests editing the first meal in the meal plan (index 0).
         * Verifies that updating the first valid index succeeds without errors.
         * Boundary Value Analysis: first valid index.
         */
    @Test
    void editMeal_firstMeal() {
        service.editMeal(0, replacementMeal);
    }

        /**
         * Tests editing the last meal in the meal plan.
         * Ensures that updating the final valid index succeeds correctly.
         * Boundary Value Analysis: last valid index.
         */
    @Test
    void editMeal_lastMeal() {
        service.editMeal(plan.size() - 1, replacementMeal);
    }

        /**
         * Tests editing a meal using an index larger than the meal plan size.
         * Verifies that an IndexOutOfBoundsException is thrown.
         * Boundary Value Analysis: index above valid range.
         */
    @Test
    void editMeal_invalidIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> service.editMeal(100, replacementMeal));
    }

        /**
         * Tests editing a meal using a negative index value.
         * Verifies that an IndexOutOfBoundsException is thrown.
         * Boundary Value Analysis: index below valid range.
         */
    @Test
    void editMeal_invalidUnderIndex() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> service.editMeal(-4, replacementMeal));
    }


        /**
         * Tests generating a meal plan with extremely restrictive dietary preferences.
         * ex.
         * vegetarian = true
         * vegan = true
         * glutenFree = true
         * nutAllergy = true
         */
    @Test
    void regenerateMealPlan_extremeRestrictions() {
        Preferences strictPrefs =
                new Preferences(true, true, true, true);

        MealPlan plan = service.generatePlan(7, strictPrefs);

        assertNotNull(plan);
    }


        /**
         * Tests generating a meal plan while the service is offline.
         * Verifies that a ServiceUnavailableException is thrown when generation is attempted.
         * Exception handling test.
         */
    @Test
    void regenerateMealPlan_whenServiceOffline() {

        service.isOnline(false);

        assertThrows(ServiceUnavailableException.class,
                () -> service.generatePlan(7, preferences));
    }


        /**
         * Tests replacing an existing meal with a valid replacement meal.
         * Ensures the meal is successfully updated at the specified index.
         * Functional correctness test.
         */
    @Test
    void editMeal_validReplacement() {

        Meal replacement =
                new Meal("Grilled Chicken", preferences);

        service.editMeal(2, replacement);

        assertEquals(replacement,
                service.getMeal(2));
    }

        /**
         * Tests replacing a meal with one that violates dietary restrictions.
         * Verifies that an InvalidMealException is thrown.
         * Exception handling test.
         */
    @Test
    void editMeal_invalidReplacement() {

        Meal badReplacement =
                new Meal("Peanut Stir Fry", nutAllergyPrefs);

        assertThrows(InvalidMealException.class,
                () -> service.editMeal(2, badReplacement));
    }
}

