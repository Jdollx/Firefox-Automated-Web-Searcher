# Firefox-Automated-Web-Searcher

## About
This Java application automates web searches using Selenium WebDriver with Mozilla Firefox. It reads search terms from a JSON file, performs searches on Bing, and waits for random intervals between searches.

## Requirements
GeckoDriver: GeckoDriver is installed and set in your system's PATH. Access its location via the environment variable DRIVER.
Firefox Profile: Locate the path to your Firefox profile via the environment variable PROFILE.
JSON File: Access the JSON file containing search terms via the environment variable JSON.

## Dependencies
Selenium WebDriver: Used for the webdriver and web automation.
Jackson Core: For JSON processing.
