package io.github.some_example_name.lwjgl3.application_classes.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Utility class to manage dictionaries of food types and nutritionist facts
 */
public class FoodDictionary {
    // Random generator
    private final Random random = new Random();
    
    // Dictionaries for food types and facts
    private final Map<String, String> healthyDict = new HashMap<>();
    private final Map<String, String> unhealthyDict = new HashMap<>();
    private final String[] nutritionistFacts;
    
    /**
     * Constructor that initializes the dictionaries
     */
    public FoodDictionary() {
        // Initialize healthy food dictionary
        healthyDict.put("apple.png", "Contain fiber and vitamin C, and they float because they are 25% air!");
        healthyDict.put("banana.png", "Help boost energy and improve mood due to natural sugars and serotonin.");
        healthyDict.put("blueberry.png", "Rich in antioxidants that help improve brain function and memory.");
        healthyDict.put("carrot.png", "Originally purple! The orange ones were created through selective breeding.");
        
        // Initialize unhealthy food dictionary
        unhealthyDict.put("icecream.png", "Many commercial brands add artificial thickeners and extra sugar.");
        unhealthyDict.put("friedchicken.png", "Deep-fried foods absorb unhealthy fats that can clog arteries.");
        unhealthyDict.put("coke.png", "One can of soda can have 10 teaspoons of sugar, leading to energy crashes.");
        unhealthyDict.put("fries.png", "The average person eats 30 pounds of fries per year!");
        
        // Initialize nutritionist facts
        nutritionistFacts = new String[] {
            "Skipping breakfast = starting a race with no fuel!",
            "Water is the best drink! It keeps your brain fresh and your body happy.",
            "Your plate should look like a rainbow! More colors = more vitamins!",
            "The slower you eat, the faster you feel full! Try chewing slowly!",
            "Feeling tired? Instead of junk food, try nuts or fruit for natural energy!"
        };
    }
    
    /**
     * Gets a random healthy food texture name and its fact
     * @return Array with [textureName, fact]
     */
    public String[] getRandomHealthyFood() {
        String[] keys = healthyDict.keySet().toArray(new String[0]);
        String key = keys[random.nextInt(keys.length)];
        return new String[] { key, healthyDict.get(key) };
    }
    
    /**
     * Gets a random unhealthy food texture name and its fact
     * @return Array with [textureName, fact]
     */
    public String[] getRandomUnhealthyFood() {
        String[] keys = unhealthyDict.keySet().toArray(new String[0]);
        String key = keys[random.nextInt(keys.length)];
        return new String[] { key, unhealthyDict.get(key) };
    }
    
    /**
     * Gets a random nutritionist fact
     * @return A random nutritionist fact
     */
    public String getRandomNutritionistFact() {
        return nutritionistFacts[random.nextInt(nutritionistFacts.length)];
    }
}