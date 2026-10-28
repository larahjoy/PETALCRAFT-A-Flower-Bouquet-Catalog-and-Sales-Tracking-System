package com.mycompany.flowerbouquet.data;

import com.mycompany.flowerbouquet.model.Sale;
import com.mycompany.flowerbouquet.model.Category;
import com.mycompany.flowerbouquet.model.Bouquet;
import com.mycompany.flowerbouquet.model.SaleDetail;
import com.mycompany.flowerbouquet.model.User;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    public static List<User> users = new ArrayList<>();
    public static List<Category> categories = new ArrayList<>();
    public static List<Bouquet> bouquets = new ArrayList<>();
    public static List<Sale> sales = new ArrayList<>();
    public static List<Bouquet> archivedBouquets = new ArrayList<>();

    public static int currentUserId = 1;

    static {

        // DEFAULT ADMIN USER
        users.add(
            new User(
                "Administrator",
                "admin",
                "admin123",
                "Admin"
            )
        );

        // DEFAULT CATEGORIES
        categories.add(
            new Category(
                1,
                "Romantic",
                "Bouquets for romantic occasions."
            )
        );

        categories.add(
            new Category(
                2,
                "Birthdays",
                "Bouquets for birthday celebrations."
            )
        );

        categories.add(
            new Category(
                3,
                "Weddings",
                "Bouquets for weddings."
            )
        );

        categories.add(
            new Category(
                4,
                "Sympathy",
                "Bouquets for sympathy occasions."
            )
        );

        categories.add(
            new Category(
                5,
                "Graduation",
                "Bouquets for graduation celebrations."
            )
        );

        // BOUQUET 1: CRIMSON LOVE
        Bouquet crimsonLove = new Bouquet(
            1,
            "Crimson Love",
            "Deep Love",
            560.00,
            1,
            10
        );

        crimsonLove.setImagePath(
            "/images/Crimson_Love.png"
        );

        bouquets.add(crimsonLove);

        // BOUQUET 2: HAPPY BLOOM BURST
        Bouquet happyBloomBurst = new Bouquet(
            2,
            "Happy Bloom Burst",
            "Bright sunflowers",
            24.99,
            2,
            10
        );

        happyBloomBurst.setImagePath(
            "/images/Happy_Bloom_Burst.png"
        );

        bouquets.add(happyBloomBurst);

        // BOUQUET 3: BLISSFUL BRIDE
        Bouquet blissfulBride = new Bouquet(
            3,
            "Blissful Bride",
            "Soft lavender mix",
            34.99,
            3,
            10
        );

        blissfulBride.setImagePath(
            "/images/Blissful_Bride.png"
        );

        bouquets.add(blissfulBride);

        // BOUQUET 4: HEAVENLY WHITE
        Bouquet heavenlyWhite = new Bouquet(
            4,
            "Heavenly White",
            "Exotic orchids",
            49.99,
            4,
            10
        );

        heavenlyWhite.setImagePath(
            "/images/Heavenly_White.png"
        );

        bouquets.add(heavenlyWhite);

        // BOUQUET 5: GOLDEN ACHIEVEMENT
        Bouquet goldenAchievement = new Bouquet(
            5,
            "Golden Achievement",
            "Mixed wildflowers",
            19.99,
            5,
            10
        );

        goldenAchievement.setImagePath(
            "/images/Golden_Achievements.png"
        );

        bouquets.add(goldenAchievement);
    }

    // GET NEXT BOUQUET ID
    public static int getNextBouquetId() {

        int max = 0;

        for (Bouquet b : bouquets) {
            if (b.getId() > max) {
                max = b.getId();
            }
        }

        for (Bouquet b : archivedBouquets) {
            if (b.getId() > max) {
                max = b.getId();
            }
        }

        return max + 1;
    }

    // GET NEXT CATEGORY ID
    public static int getNextCategoryId() {

        int max = 0;

        for (Category c : categories) {
            if (c.getCategoryId() > max) {
                max = c.getCategoryId();
            }
        }

        return max + 1;
    }

    // GET NEXT SALE ID
    public static int getNextSaleId() {

        int max = 0;

        for (Sale s : sales) {
            if (s.getSaleId() > max) {
                max = s.getSaleId();
            }
        }

        return max + 1;
    }

    // GET NEXT SALE DETAIL ID
    public static int getNextSaleDetailId() {

        int max = 0;

        for (Sale s : sales) {
            for (SaleDetail d : s.getDetails()) {
                if (d.getDetailId() > max) {
                    max = d.getDetailId();
                }
            }
        }

        return max + 1;
    }
}