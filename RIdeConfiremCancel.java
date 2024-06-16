package seleniumtest;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RIdeConfiremCancel {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        String loginPageUrl = "http://localhost:5173/login";

        String[] usernames = {"shama15@gmail.com"};
        String[] passwords = {"@192Bk"};

        boolean loginSuccessful = false;
        for (int i = 0; i < usernames.length; i++) {
            driver.get(loginPageUrl);

            WebElement emailField = driver.findElement(By.className("email"));
            emailField.clear();
            emailField.sendKeys(usernames[i]);

            WebElement passField = driver.findElement(By.className("password"));
            passField.clear();
            passField.sendKeys(passwords[i]);

            Thread.sleep(5000);

            WebElement loginButton = driver.findElement(By.id("loginButton"));
            loginButton.click();

            Thread.sleep(5000);

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("http://localhost:5173/")) {
                System.out.println("ok");
                loginSuccessful = true;
                break;
            } else {
                System.out.println("no ok");
            }
        }

        if (loginSuccessful) {
            for (int j = 1; j <= 10; j++) {
                System.out.print("\n");
                System.out.println("Test iteration: " + j);

                driver.get("http://localhost:5173/carService");
                Thread.sleep2(000);

                WebElement viewDetailsButton = driver.findElement(By.id("carprimeDetails"));
                viewDetailsButton.click();
                Thread.sleep(2500);

                String currentUrl = driver.getCurrentUrl();
                if (currentUrl.equals("http://localhost:5173/primeCar")) {
                    System.out.println("User can take service from the page ok");
                    WebElement locationIcon = driver.findElement(By.id("locationIcon"));
                    locationIcon.click();

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement locationInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("destination")));

                    String[] locations = {
                        " ধানমন্ডি রোড, ঢাকা ",
                        " সাদেক খান রোড, মোহাম্মদপুর, ঢাকা",
                        " চকবাজার, চট্টগ্রাম ",
                        " সিলেট রোড, কুমিল্লা ",
                        " মদিনা নগর, রাজশাহী "
                    };

                    Random random = new Random();
                    String randomLocation = locations[random.nextInt(locations.length)];

                    locationInputField.sendKeys(randomLocation);

                    Thread.sleep(7000);
                    WebElement reqRideButton = driver.findElement(By.id("reqRide"));
                    reqRideButton.click();
                    Thread.sleep(5000);

                    System.out.println("Requested a ride to: " + randomLocation);
                    Thread.sleep(2000);
                    List<WebElement> cars = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("carlistppp")));

                    WebElement randomCar = cars.get(random.nextInt(cars.size()));
                    randomCar.click();
                    Thread.sleep(2000);
                    Actions actions = new Actions(driver);
                    actions.moveByOffset(10, 10).click().perform();
                    Thread.sleep(2000);
                    WebElement finalReqRideButton = driver.findElement(By.id("finalReqRide"));
                    finalReqRideButton.click();
                    Thread.sleep(2000);
                    System.out.println("Ride requested successfully.");
                    Thread.sleep(2500);
                    String currentUrl1 = driver.getCurrentUrl();
                    if (currentUrl1.equals("http://localhost:5173/requestedcarride")) {
                        System.out.println("confirmation ride view details");

                        if (j % 2 != 0) {
                            WebElement cancelRideButton = driver.findElement(By.id("cancelRideButton"));
                            cancelRideButton.click();
                            Thread.sleep(2500);
                            WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Yes, cancel it!']")));
                            confirmButton.click();
                            Thread.sleep(2500);
                            System.out.println("Ride cancelled successfully.");
                            Thread.sleep(2500);
                        } else {
                            System.out.println("Booking not cancelled for this iteration.");
                        }

                    } else {
                        System.out.println("no ok");
                    }

                } else {
                    System.out.println("no ok");
                }
            }
        } else {
            System.out.println("All login attempts failed.");
        }

        driver.quit();
    }



}

