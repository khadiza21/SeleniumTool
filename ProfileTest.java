
package seleniumtest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfileTest {

     public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        String loginPageUrl = "http://localhost:5173/login";

        String[] usernames = {"bibikhadiza70@gmail.com", "khadiza70@gmail.com", "bibikhadiza70@gmail.com"};
        String[] passwords = {"@192Bkk", "@192Bk", "@192Bk"};

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
            driver.get("http://localhost:5173/userprofile");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
           
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Name')]")));
            System.out.println("Navigated to the user profile page.");


        } else {
            System.out.println("All login attempts failed.");
        }

        driver.quit();
           
    }
}

