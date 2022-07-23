package Pages;

import junit.framework.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.webaudio.WebAudio;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login {
    WebDriver driver;
    String url = "https://www.demoblaze.com/index.html";

    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public void performLogin(String Username, String Password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        //Find login icon
        WebElement login = this.driver.findElement(By.id("login2"));
        login.click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
            // Find the Username TextBox
            WebElement username_textbox = this.driver.findElement(By.id("loginusername"));
            // Enter the username
            username_textbox.clear();
            username_textbox.sendKeys(Username);

            // Find the password TextBox
            WebElement password_textbox = this.driver.findElement(By.id("loginpassword"));
            // Enter the password
            password_textbox.clear();
            password_textbox.sendKeys(Password);

            // Find the Login Button
            WebElement login_button = driver.findElement(By.xpath("//button[text()='Log in']"));
            // Click the login Button
            login_button.click();

            /*wait.until(ExpectedConditions.alertIsPresent());

            //switch to Alert message
            Alert alert = this.driver.switchTo().alert();

            String alertMessage = this.driver.switchTo().alert().getText();
            System.out.println(alertMessage);
            alert.accept();

            wait.until(ExpectedConditions.invisibilityOf(login_button));

            Assert.assertEquals("Failed to login","login successful.",alertMessage);
            */
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void VerifyUserLoggedIn(String Username) {
        try {
            // Find the username label (present on the top right of the page)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
            WebElement username_label = this.driver.findElement(By.id("nameofuser"));
            Assert.assertEquals("Login Error","Welcome "+Username,username_label.getText());
            System.out.println("Login Successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean performLogout() throws InterruptedException {

        navigateToHomePage();

        synchronized (driver){
            driver.wait(2000);
        }

        //Find WebElement of logout icon and click on it
        WebElement logout = this.driver.findElement(By.xpath("//a[@id='logout2']"));
        logout.click();
        return true;
    }
}
