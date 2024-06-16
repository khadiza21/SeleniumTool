package seleniumtest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.Random;

public class RIdeConfiremCancel {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Documents\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        boolean loginSuccessful = false;

        while (!loginSuccessful) {
            String signupPageUrl = "http://localhost:5173/signup";
            driver.get(signupPageUrl);

            String[] roles = {"User"};
            String[] genders = {"Female", "Male", "Other"};
            String[] names = {"name one", "name two", "name three", "name four", "name five", "name six"};

            Random random = new Random();
            String selectedRole = roles[random.nextInt(roles.length)];

            Select roleSelect = new Select(driver.findElement(By.className("role")));
            roleSelect.selectByVisibleText(selectedRole);

            String selectedName = names[random.nextInt(names.length)];
            WebElement nameField = driver.findElement(By.className("name"));
            nameField.sendKeys(selectedName);

            String cleanName = selectedName.replaceAll("\\s+", ""); 
            String randomEmail = cleanName + random.nextInt(1000) + "@gmail.com";
            WebElement emailField = driver.findElement(By.className("email"));
            emailField.sendKeys(randomEmail);

            String phoneNumber = "1" + (random.nextInt(90000000) + 10000000);
            if (random.nextBoolean()) {
                phoneNumber += random.nextInt(10);
            }
            WebElement phoneField = driver.findElement(By.className("phone"));
            phoneField.sendKeys(phoneNumber);

            String selectedGender = genders[random.nextInt(genders.length)];
            Select genderSelect = new Select(driver.findElement(By.className("gender")));
            genderSelect.selectByVisibleText(selectedGender);

            String password = "@192Bk";

            WebElement passField = driver.findElement(By.className("password"));
            passField.sendKeys(password);
            WebElement confirmPassField = driver.findElement(By.className("confirmPassword"));
            confirmPassField.sendKeys(password);

            Thread.sleep(5000);

            WebElement submitButton = driver.findElement(By.id("submit"));
            submitButton.click();

            Thread.sleep(10000);

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("http://localhost:5173/")) {
                System.out.println("Successfully Created Account.");
                loginSuccessful = true;
                break;
            } else {
                System.out.println("Provide right Information. And Try again");
            }

        }

        driver.quit();
    }
}

