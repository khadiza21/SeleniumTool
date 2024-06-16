
package seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;
import java.util.List;

public class ManageUserTest {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        String loginPageUrl = "http://localhost:5173/login";
        String currentUrl;

        String[] adminnames = {"citymover@gmail.com"};
        String[] passwords = {"@192Bk"};

        boolean loginSuccessful = false;
        for (int i = 0; i < adminnames.length; i++) {
            driver.get(loginPageUrl);

            WebElement emailField = driver.findElement(By.className("email"));
            emailField.clear();
            emailField.sendKeys(adminnames[i]);

            WebElement passField = driver.findElement(By.className("password"));
            passField.clear();
            passField.sendKeys(passwords[i]);

            Thread.sleep(5000);

            WebElement loginButton = driver.findElement(By.id("loginButton"));
            loginButton.click();

            Thread.sleep(5000);

            currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("http://localhost:5173/")) {
                System.out.println("ok");
                loginSuccessful = true;
                break;
            } else {
                System.out.println("no ok");
            }
        }

        if (loginSuccessful) {

            driver.get("http://localhost:5173/dashboard");
            Thread.sleep(2500);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profileIcon")));
            highlightElement(driver, profileIcon);  
            profileIcon.click();
            System.out.println("Clicked profile icon");
            Thread.sleep(2000);

            WebElement userManagementLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userManagement")));
            highlightElement(driver, userManagementLink);  // Highlight the user management link
            Thread.sleep(5000);
            userManagementLink.click();
            System.out.println("Clicked user management link");

            Thread.sleep(2500);

            currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("http://localhost:5173/manageuser")) {
                System.out.println("Navigated to user management successfully.");
                Thread.sleep(2000);
                List<WebElement> users = driver.findElements(By.className("userItem"));

                int verifiedCount = 0;
                int removedCount = 0;

                // Skip the first 4 users
                for (int i = 4; i < users.size(); i++) {
                    WebElement user = users.get(i);
                    boolean isOdd = (i % 2 == 1);

                    try {
                        WebElement verifyButton = user.findElement(By.className("verifyButton"));

                        if (isOdd && verifiedCount < 2) {
                            // Odd iteration: Verify the user
                            highlightElement(driver, verifyButton);  // Highlight the verify button
                            Thread.sleep(5000);
                            verifyButton.click();
                            
                            System.out.println("Clicked verify button for user " + (i + 1));
                            Thread.sleep(1500);
                            System.out.println("Verified user " + (i + 1));
                            verifiedCount++;
                        } else if (!isOdd && removedCount < 2) {
                            // Even iteration: Skip to next user
                            System.out.println("Skipping user " + (i + 1) + " for deletion as verification button is present.");
                        } else {
                            System.out.println("Skipping user " + (i + 1));
                        }

                    } catch (NoSuchElementException e) {
                        if (!isOdd && removedCount < 2) {
                            WebElement removeButton = user.findElement(By.className("removeButton"));
                            highlightElement(driver, removeButton);  // Highlight the remove button
                            
                            removeButton.click();
                            System.out.println("Clicked remove button for user " + (i + 1));
                            Thread.sleep(1500);
                            System.out.println("Removed user " + (i + 1));
                            removedCount++;

                            // Confirm the removal if necessary
                            WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Yes, delete it!']")));
                            highlightElement(driver, confirmButton);  // Highlight the confirm button
                            confirmButton.click();
                            System.out.println("Clicked confirm button for removing user " + (i + 1));
                            Thread.sleep(1500);
                        } else {
                            System.out.println("Skipping user " + (i + 1) + " for verification as verify button is not present.");
                        }
                    }

                    if (verifiedCount >= 2 && removedCount >= 2) {
                        break;
                    }
                }
            } else {
                System.out.println("Failed to navigate to user management.");
            }
        } else {
            System.out.println("All login attempts failed.");
        }

        driver.quit();
    }

    private static void highlightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }
}
