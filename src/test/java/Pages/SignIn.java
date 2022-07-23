package Pages;

import junit.framework.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Timestamp;
import java.time.Duration;

public class SignIn {
    WebDriver driver;
    String url = "https://www.demoblaze.com/index.html#";
    public String lastGeneratedUser = "";

    public SignIn(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }

    public Boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));


        //Find the SignUp icon
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='signin2']")));
        WebElement signUp = driver.findElement(By.xpath("//a[@id='signin2']"));
        signUp.click();

        try{
            // Find the Username Text Box
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='sign-username']")));
        } catch (Exception e){
            e.printStackTrace();
        }

        WebElement username_textbox = this.driver.findElement(By.xpath("//input[@id='sign-username']"));

        // Get time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_data_username;
        if (makeUsernameDynamic)
            // Concatenate the timestamp to string to form unique timestamp
            test_data_username = Username + "_" + String.valueOf(timestamp.getTime());
        else
            test_data_username = Username;

        //Enter the generated username
        username_textbox.clear();
        username_textbox.sendKeys(test_data_username);

        // Find the password Text Box
        WebElement password_textbox = this.driver.findElement(By.xpath("//input[@id='sign-password']"));
        // Enter the Password value
        password_textbox.clear();
        password_textbox.sendKeys(Password);

        //Find the SignUp button in the form
        WebElement signUpButton = this.driver.findElement(By.xpath("//button[text()='Sign up']"));
        signUpButton.click();

        wait.until(ExpectedConditions.alertIsPresent());

        //Switch to Alert window
        Alert alert = this.driver.switchTo().alert();
        String alertMessage = alert.getText();
        System.out.println(alertMessage);
        alert.accept();

        this.lastGeneratedUser = test_data_username;

        if (alertMessage.equals("Sign up successful.")){
            return true;
        } else {
            WebElement closeButton = this.driver.findElement(By.xpath("//button[@onclick='register()']//preceding-sibling::button"));
            closeButton.click();
            return false;
        }
        //Assert.assertEquals("Failed to register","Sign up successful.",alertMessage);
    }
}
