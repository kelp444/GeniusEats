import pytest
import os
from playwright.sync_api import Page, expect


# Creates the correct path for local file in github
def get_url(file_name):
    current_dir = os.path.dirname(os.path.abspath(__file__))
    parent_dir = os.path.dirname(current_dir)
    file_path = os.path.join(parent_dir, file_name)
    return f"file:///{file_path}".replace("\\","/")


# Regenerate an entire meal plan
def test_regenerate_entire_meal_plan(page: Page):
    page.goto(get_url("edit-meal.html"))

    # Click Regenerate Meal Plan and confirm
    page.get_by_role("button", name="Regenerate Meal Plan").click()
    page.get_by_role("button", name="Confirm Regenerate").click()

    # Expected: A new meal plan is now displayed
    expect(page.locator("#plan-title")).to_contain_text("Weekly Vegetarian Plan")


# Edit one meal in the plan
def test_edit_one_meal(page: Page):
    page.goto(get_url("edit-meal.html"))

    # Select Pasta Carbonara and click Replace
    page.locator(".meal-item", has_text="Pasta Carbonara").get_by_role("button", name="Replace").click()

    # Enter replacement and constraint (if user needs one)
    page.locator("#replace-input").fill("Greek Yogurt Bowl")
    page.locator("#constraint-input").fill("No Chicken")
    page.get_by_role("button", name="Replace Meal").click()

    # Expected: Meal is updated in the list
    expect(page.locator("#status-message")).to_contain_text("Meal updated successfully")
    expect(page.locator(".meal-item", has_text="Greek Yogurt Bowl")).to_be_visible()


# Regenerate with restrictive preferences
def test_regenerate_with_restrictive_preferences(page: Page):
    page.goto(get_url("edit-meal.html"))

    # Set restrictive preferences
    page.evaluate("sessionStorage.setItem('diet', 'vegan')")
    page.evaluate("sessionStorage.setItem('budget', 'low')")

    # Click Regenerate Meal Plan and confirm
    page.get_by_role("button", name="Regenerate Meal Plan").click()
    page.get_by_role("button", name="Confirm Regenerate").click()

    # Expected: No valid options available message
    expect(page.locator("text=No valid meal options available")).to_be_visible()


# Attempt to regenerate when service/app is unavailable
def test_regenerate_when_service_unavailable(page: Page):
    page.goto(get_url("edit-meal.html"))

    # Assume service being down
    page.evaluate("sessionStorage.setItem('serviceDown', 'true')")

    # Click Regenerate Meal Plan and confirm
    page.get_by_role("button", name="Regenerate Meal Plan").click()
    page.get_by_role("button", name="Confirm Regenerate").click()

    # Expected: An error message is shown, page does not crash
    expect(page.locator("text=Service unavailable")).to_be_visible()

    # Expected: Original plan should still be visible
    expect(page.locator("#plan-title")).to_contain_text("Weekly High Protein Plan")


# Edit meal with replacement preferences
def test_edit_meal_with_valid_replacement(page: Page):
    page.goto(get_url("edit-meal.html"))

    # Select Pasta Carbonara and click Replace
    page.locator(".meal-item", has_text="Pasta Carbonara").get_by_role("button", name="Replace").click()

    # Enter a higher protein choice/replacement
    page.locator("#replace-input").fill("Grilled Salmon & Quinoa")
    page.get_by_role("button", name="Replace Meal").click()

    # Expected: The updated meal is saved and displayed
    expect(page.locator(".meal-item", has_text="Grilled Salmon & Quinoa")).to_be_visible()
    expect(page.locator("#status-message")).to_contain_text("Meal updated successfully")
