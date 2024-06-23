package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        // Set path to gecko driver
        String PATH = System.getenv("DRIVER");
        System.setProperty("webdriver.gecko.driver", PATH);

        // Path to your Firefox profile
        String profilePath = System.getenv("PROFILE");

        // Set up Firefox options with your profile
        FirefoxProfile profile = new FirefoxProfile(new File(profilePath));
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        // New instance of the driver
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Open Bing
        driver.get("https://bing.com");

        // Path to JSON file
        String jsonPath = System.getenv("JSON");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        List<String> words = null;
        try {
            // Read JSON file
            words = mapper.readValue(new File(jsonPath), new TypeReference<List<String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Exit if reading the JSON file fails
        }

        // Check that there are words in JSON
        if (words != null && !words.isEmpty()) {
            Random random = new Random();
            int searchCount = 0; // Counter to keep track of the number of searches

            for (int i = 0; i < 200; i++) { // print
                int randomIndex = random.nextInt(words.size());
                String randomWord = words.get(randomIndex);

                try {
                    WebElement search = driver.findElement(By.name("q"));
                    search.clear();
                    search.sendKeys(randomWord);

                    // random time between 10 and 30 seconds
                    int waitTime = 10 + random.nextInt(21);
                    Thread.sleep(waitTime * 1000); // to milliseconds

                    search.submit();

                    // increase counter
                    searchCount++;

                    // print
                    System.out.println("Search performed: " + searchCount + " | " + LocalDateTime.now());

                    // Wait for a random time interval (between 2 and 15 minutes)
                    int minWaitTime = 2 * 60 * 1000; // 2 minutes in milliseconds
                    int maxWaitTime = 15 * 60 * 1000; // 15 minutes in milliseconds
                    waitTime = random.nextInt(maxWaitTime - minWaitTime + 1) + minWaitTime;
                    Thread.sleep(waitTime); // Wait for the randomly selected time

                    // Go back to the search page for the next search
                    driver.navigate().to("https://www.bing.com");
                } catch (NoSuchElementException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No search terms found in the JSON file.");
        }

        // close
        driver.quit();
    }
}
