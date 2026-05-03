package com.smartrestaurant.config;

import com.smartrestaurant.model.MenuItem;
import com.smartrestaurant.model.Supervisor;
import com.smartrestaurant.repository.MenuItemRepository;
import com.smartrestaurant.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds the database with menu items (from original data/menu.txt)
 * and supervisor credentials (from original data/supervisors.txt).
 * All data exactly mirrors the original files.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Override
    public void run(String... args) {
        seedMenu();
        seedSupervisors();
    }

    // ─── Menu from original data/menu.txt ────────────────────────────────────
    // Format: slot, variety, varietyName, dishName, qty, price
    private void seedMenu() {
        if (menuItemRepository.count() > 0) return;

        // Slot 1 – Breakfast (6:00 AM – 11:30 AM)
        menuItemRepository.save(new MenuItem(1, 1, "Tiffin", "Dosa",      1, 40));
        menuItemRepository.save(new MenuItem(1, 1, "Tiffin", "Poori",     1, 40));
        menuItemRepository.save(new MenuItem(1, 1, "Tiffin", "Pongal",    1, 50));
        menuItemRepository.save(new MenuItem(1, 1, "Tiffin", "Idli",      1, 30));
        menuItemRepository.save(new MenuItem(1, 1, "Tiffin", "Vada",      1, 20));

        // Slot 2 – Lunch (11:30 AM – 3:30 PM)
        menuItemRepository.save(new MenuItem(2, 1, "Meals",        "Meals",         1, 120));
        menuItemRepository.save(new MenuItem(2, 1, "Meals",        "Special Meals", 1, 150));
        menuItemRepository.save(new MenuItem(2, 1, "Meals",        "Mini Meals",    1, 100));

        menuItemRepository.save(new MenuItem(2, 2, "Biryani",      "Veg Biryani",     1, 150));
        menuItemRepository.save(new MenuItem(2, 2, "Biryani",      "Mutton Biryani",  1, 250));
        menuItemRepository.save(new MenuItem(2, 2, "Biryani",      "Chicken Biryani", 1, 200));

        menuItemRepository.save(new MenuItem(2, 3, "Variety Rice", "Lemon Rice",  1, 80));
        menuItemRepository.save(new MenuItem(2, 3, "Variety Rice", "Curd Rice",   1, 70));
        menuItemRepository.save(new MenuItem(2, 3, "Variety Rice", "Tomato Rice", 1, 80));

        // Slot 3 – Snacks (3:30 PM – 6:30 PM)
        menuItemRepository.save(new MenuItem(3, 1, "Ice Cream", "Vanilla Ice Cream",    1, 60));
        menuItemRepository.save(new MenuItem(3, 1, "Ice Cream", "Chocolate Ice Cream",  1, 70));
        menuItemRepository.save(new MenuItem(3, 1, "Ice Cream", "Strawberry Ice Cream", 1, 70));

        menuItemRepository.save(new MenuItem(3, 2, "Juice",     "Apple Juice",      1, 80));
        menuItemRepository.save(new MenuItem(3, 2, "Juice",     "Orange Juice",     1, 80));
        menuItemRepository.save(new MenuItem(3, 2, "Juice",     "Watermelon Juice", 1, 90));

        menuItemRepository.save(new MenuItem(3, 3, "Snacks",    "Samosa",    1, 20));
        menuItemRepository.save(new MenuItem(3, 3, "Snacks",    "Vada",      1, 15));
        menuItemRepository.save(new MenuItem(3, 3, "Snacks",    "Bajji",     1, 15));
        menuItemRepository.save(new MenuItem(3, 3, "Snacks",    "Pani Poori",1, 30));

        // Slot 4 – Dinner (6:30 PM – 11:00 PM)
        menuItemRepository.save(new MenuItem(4, 1, "Roti",       "Parota",      1, 40));
        menuItemRepository.save(new MenuItem(4, 1, "Roti",       "Naan",        1, 50));
        menuItemRepository.save(new MenuItem(4, 1, "Roti",       "Butter Naan", 1, 60));
        menuItemRepository.save(new MenuItem(4, 1, "Roti",       "Poori",       1, 40));
        menuItemRepository.save(new MenuItem(4, 1, "Roti",       "Chapathi",    1, 30));

        menuItemRepository.save(new MenuItem(4, 2, "Dosa",       "Dosa",         1, 50));
        menuItemRepository.save(new MenuItem(4, 2, "Dosa",       "Special Dosa", 1, 80));
        menuItemRepository.save(new MenuItem(4, 2, "Dosa",       "Onion Dosa",   1, 70));
        menuItemRepository.save(new MenuItem(4, 2, "Dosa",       "Masala Dosa",  1, 80));

        menuItemRepository.save(new MenuItem(4, 3, "Fried Rice", "Veg Fried Rice",      1, 120));
        menuItemRepository.save(new MenuItem(4, 3, "Fried Rice", "Chicken Fried Rice",  1, 150));
        menuItemRepository.save(new MenuItem(4, 3, "Fried Rice", "Mushroom Fried Rice", 1, 140));
        menuItemRepository.save(new MenuItem(4, 3, "Fried Rice", "Paneer Fried Rice",   1, 150));
    }

    // ─── Supervisors from original data/supervisors.txt ───────────────────────
    // sup1,111  →  Tables 1-3  →  Supervisor 1
    // sup2,222  →  Tables 4-6  →  Supervisor 2
    // sup3,333  →  Tables 7-10 →  Supervisor 3
    private void seedSupervisors() {
        if (supervisorRepository.count() > 0) return;

        supervisorRepository.save(new Supervisor("sup1", "111", "Supervisor 1", 1, 3));
        supervisorRepository.save(new Supervisor("sup2", "222", "Supervisor 2", 4, 6));
        supervisorRepository.save(new Supervisor("sup3", "333", "Supervisor 3", 7, 10));
    }
}
