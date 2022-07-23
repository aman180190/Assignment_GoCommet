import Pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class DemoBlazeTest {
    static WebDriver driver;
    static Boolean status;
    static String lastGeneratedUserName;

    public static void webDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s ", type, message, status));
    }

    //Verify the functionality of SignIn & Login in the Home page
    public static Boolean TestCase01() throws InterruptedException {
        logStatus("Start TestCase", "Test Case 1: Verify User SignIn", "DONE");

        //Register a new User
        SignIn registration = new SignIn(driver);
        registration.navigateToHomePage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 1", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUser;

        logStatus("TestCase 1", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToHomePage();
        login.performLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);
        login.performLogout();

        logStatus("End TestCase", "Test Case 1: Verify user SigIn & Login: ", "PASS");
        return true;
    }

    // Verify that an existing user is not allowed to re-SignIn on the Website
    public static Boolean TestCase02() throws InterruptedException {
        status = false;
        logStatus("Start Testcase", "Test Case 2: Verify User SignIn with an existing username ", "DONE");

        //Register a new User
        SignIn registration = new SignIn(driver);
        registration.navigateToHomePage();
        status = registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 2", "Test Step: User SignIn", status ? "PASS" : "FAIL");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUser;

        synchronized (driver){
            driver.wait(2000);
        }

        //Try to register using the previously registered user's credentials
        registration.navigateToHomePage();
        status = registration.registerUser(lastGeneratedUserName, "abc@123", false);

        //If status is true, then registration succeeded, else registration has failed.
        System.out.println("In this case registration failure means Success");
        logStatus("End TestCase", "Test Case 2: Verify user Re-SignIn: ", status ? "PASS" : "FAIL");
        return !status;
    }

    //Verify the searching of a Product
    public static Boolean TestCase03() throws InterruptedException {
        status = false;
        logStatus("TestCase 3", "Start test case: Verify the searching of a Product ", "DONE");

        //HomePage(Product to View)
        String category = "Phones";
        String product = "HTC One";
        Home home = new Home(driver);
        home.navigateToHomePage();
        status = home.searchForProduct(category, product);
        logStatus("TestCase 3", "Test Step: Verify searching for a Product: "+product, status ? "PASS" : "FAIL");

        //Searching for a Product that is not available in the Website
        category = "Laptops";
        product = "Lenovo XPS";
        home.navigateToHomePage();
        status = home.searchForProduct(category, product);
        logStatus("TestCase 3", "Test Step: Verify searching for a Product: "+product, status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 3: Verify searching of a Product: ", status ? "PASS" : "FAIL");
        return status;
    }

    //Verify if Add_to_Cart Button working in Product page
    public static Boolean TestCase04() throws InterruptedException {
        logStatus("TestCase 4", "Start test case : Verify if Add_to_Cart Button working in Product page", "DONE");
        status = false;

        //Register a new User
        SignIn registration = new SignIn(driver);
        registration.navigateToHomePage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 4", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUser;

        logStatus("TestCase 4", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToHomePage();
        login.performLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Home home = new Home(driver);
        home.navigateToHomePage();
        status = home.searchForProduct(category, product);
        logStatus("TestCase 4", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = home.addProductToCart();
        login.performLogout();

        logStatus("End TestCase", "Test Case 4: Verify if Add_to_Cart Button working in Product page ", status ? "PASS" : "FAIL");
        return status;
    }

    //Verify if the product description is viewable on the Product page
    public static Boolean TestCase05() throws InterruptedException {
        logStatus("TestCase 5", "Start test case : Verify if the Product description is viewable on the Product page", "DONE");
        status = false;

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "ASUS";
        Home home = new Home(driver);
        home.navigateToHomePage();
        status = home.searchForProduct(category, product);
        logStatus("TestCase 5", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //View product description
        status = home.productDescription(product);
        logStatus("End TestCase", "Test Case 5: Verify if the Product description is viewable on the Product page ", status ? "PASS" : "FAIL");

        return status;
    }

    //Verify the complete flow of buying process and placing order for a product is working correctly
    public static Boolean TestCase06() throws InterruptedException {
        status = false;
        logStatus("Start TestCase", "Test Case 6: Verify Happy Flow of buying a product", "DONE");

        //Register a new User
        SignIn registration = new SignIn(driver);
        registration.navigateToHomePage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 6", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUser;

        logStatus("TestCase 6", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToHomePage();
        login.performLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);

        logStatus("TestCase 6", "Test Step : User logIn", "PASS");

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Home home = new Home(driver);
        home.navigateToHomePage();
        status = home.searchForProduct(category, product);
        logStatus("TestCase 6", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = home.addProductToCart();
        logStatus("TestCase 6", "Test Step: Verify Product added to cart: "+ product, status ? "PASS" : "FAIL");

        //Cart(Product in cart)
        String expectedResult = product;
        Cart cart = new Cart(driver);
        cart.navigateToCart();
        cart.verifyCart(expectedResult);

        //Checkout(PlaceOrder)
        String name = "Amandeep Singh";
        String country = "India";
        String city = "Kolkata";
        String cardDetails = "XXXX-XXXX-XXXX-2RTY";
        String month = "12";
        String year = "2022";

        CheckOut checkout = new CheckOut(driver);
        checkout.navigateToCart();
        status = checkout.placeOrder(name, country, city, cardDetails, month, year);
        logStatus("TestCase 6", "Test Step: Verify order placed: "+ product, status ? "PASS" : "FAIL");

        //logout
        status = login.performLogout();
        logStatus("TestCase 6", "Test Step: Verify user logged out: "+ product, status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 6: Test Case 6: Verify Happy Flow of buying a product ", status ? "PASS" : "FAIL");
        return status;
    }



    //Verify that the contents added to the cart are saved against the user login details
    public static Boolean TestCase07() throws InterruptedException {
        logStatus("Start TestCase", "Test Case 7: Verify that the contents added to the cart are saved against the user login details", "DONE");
        status = false;

        //Register a new User
        SignIn registration = new SignIn(driver);
        registration.navigateToHomePage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 7", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUser;

        logStatus("TestCase 7", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToHomePage();
        login.performLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);

        logStatus("TestCase 7", "Test Step : User logIn", "PASS");

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Home home = new Home(driver);
        home.navigateToHomePage();
        status = home.searchForProduct(category, product);
        logStatus("TestCase 7", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = home.addProductToCart();
        logStatus("TestCase 7", "Test Step: Verify Product added to cart: "+ product, status ? "PASS" : "FAIL");

        //Cart(Product in cart)
        String expectedResult = product;
        Cart cart = new Cart(driver);
        cart.navigateToCart();

        //Perform logout
        login.navigateToHomePage();
        status = login.performLogout();
        logStatus("TestCase 7", "Test Step: User logged out: "+ product, status ? "PASS" : "FAIL");

        synchronized (driver){
            driver.wait(2000);
        }

        //Perform login
        login.performLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);
        logStatus("TestCase 7", "Test Step : User logIn", "PASS");

        //Navigate to Cart
        cart.navigateToCart();
        status = cart.verifyCart(expectedResult);
        logStatus("TestCase 7", "Test Step: Verify cart contents after login again: "+ product, status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 7: Verify that the contents added to the cart are saved against the user login details ", status ? "PASS" : "FAIL");

        return status;
    }

    //View the list of Products under each Category
    public static Boolean TestCase08() throws InterruptedException {
        logStatus("Start TestCase", "Test Case 8: View the list of Products under each Category ", "DONE");
        String category="";

        //Homepage
        Home home = new Home(driver);
        home.navigateToHomePage();

        List<WebElement> elements = driver.findElements(By.xpath("//a[@id='itemc']"));
        for(WebElement categoryElement : elements){
            category = categoryElement.getText();
            home.listOfProducts(category);
        }

        logStatus("End TestCase", "Test Case 8: View the list of Products under each Category ", status ? "PASS" : "FAIL");
        return true;
    }

    public static void main(String[] args) {
        status = false;
        String url = "https://www.demoblaze.com/index.html";
        int totalTests = 0;
        int passedTests = 0;

        DemoBlazeTest.webDriver();

        try {
            totalTests += 1;
            status = TestCase01();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase02();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase03();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase04();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase05();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase06();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase07();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            totalTests += 1;
            status = TestCase08();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            driver.quit();
            System.out.println(String.format("%s out of %s test cases Passed ", Integer.toString(passedTests), Integer.toString(totalTests)));
        }
    }
}