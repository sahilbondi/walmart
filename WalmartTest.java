import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class WalmartTest {
    //Login information
    private static String uid = "sahilbondi@gmail.com";
    private static String pwd = "111111";

    public static String getUid(){
        return uid;
    }
    public static String getPwd(){
        return pwd;
    }
    public static WebElement getByXpath(WebDriver driver, String path){
        return driver.findElement(By.xpath(path));
    }

    public static WebElement getByCss(WebDriver driver, String selector){
        return driver.findElement(By.cssSelector(selector));
    }
    public static WebElement getById(WebDriver driver, String id){
        return driver.findElement(By.id(id));
    }
    public static WebElement getByClassName(WebDriver driver, String name){
        return driver.findElement(By.className(name));
    }

    public static void homePage(WebDriver driver){

        //Opening the www.walmart.com
        driver.get("https://www.walmart.com");
        driver.manage().window().maximize();

        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle,"Walmart.com: Save money. Live better." );//Checking the page is correct
    }

    public static void extraAdvertisements(WebDriver driver){
        //Closing extra advertisements
        //that sits on the main page
        WebElement spam = getByClassName(driver, "Modal-closeButton");
        if(spam!= null)
            spam.click();
    }
    public static void signInProcess(WebDriver driver){
        //Finding the sign in button
        WebElement signIn = getByClassName(driver, "header-account-signin");
        if(signIn != null)
            signIn.click();
        else
            System.out.println("can't find the button to go to login-in page");

        //Timeout: in case page takes some time to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//Waits in case elements take time to show up

        //Entering the username
        WebElement username = getByCss(driver, ".form-control.js-email.js-login-username.parsley-validated");
        if(username != null)
            username.sendKeys(getUid());//Sending the username
        else
            System.out.println("Can't find the box to enter username");

        //Entering the password
        WebElement password = getById(driver, "login-password");
        if(password != null)
            password.sendKeys(getPwd());
        else
            System.out.println("Can't find the box to enter password");

        //Pressing the login button
        WebElement btn_login = getByCss(driver, ".btn.login-sign-in-btn.js-login-sign-in.btn-block-max-s.btn-block-max-s");
        if(btn_login != null)
            btn_login.click();
        else
            System.out.println("Can't find the login-in button");

        WebElement loginValidation = null;
        try {
            loginValidation = getByCss(driver, ".js-signin-alert.alert.signin-alert.login-input-container.alert-error.alert-block.active");
            if(loginValidation.getText().equals("Your password and email address do not match. Please try again or reset your password."))
                System.out.println("Incorrect username or password");
            else
                System.out.println("Login successful");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public static void search(WebDriver driver){
        //Searching for the TV
        WebElement searchBox = getByCss(driver, ".js-searchbar-input.js-header-instant-placeholder.searchbar-input.tt-input");
        searchBox.sendKeys("tv");

        WebElement searchBtn = getByCss(driver, ".searchbar-submit.js-searchbar-submit");
        if(searchBtn != null)
            searchBtn.click();
        else
            System.out.println("Can't find the search button");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static String addFirstMatchedItemToCart(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //click the first element
        //WebElement checkTv = getByXpath(driver, "//*[@id='tile-container']/div[1]/div/div/h4/a");
        RemoteWebElement checkTv = (RemoteWebElement) getByXpath(driver, "//*[@id='tile-container']");
        System.out.println(checkTv.toString());

        List<WebElement> elements = checkTv.findElementsByTagName("div");
        for(WebElement element : elements) {
            String itemId = ((RemoteWebElement) element).getAttribute("data-item-id");
            String tvContents = ((RemoteWebElement)element).getText();
            if(tvContents.contains("LED") && tvContents.contains("HDTV")){//Checking the link is of correct item
                WebElement firstResultImage = getByXpath(driver, "//*[@id='tile-container']/div[1]/a/img");
                firstResultImage.click();

                //Adding the item to the cart
                //Thread.sleep(2000);//Waiting in case pages takes a while to load
                getByXpath(driver, "//*[@id='WMItemAddToCartBtn']").click();//Add to cart
                return itemId;
            }
        }
        return null;
    }
    public static void viewCart(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Viewing the cart
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //Thread.sleep(3000);
        getByXpath(driver, "//*[@id='PACViewCartBtn']").click();//View cart
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    public static void checkingCart(WebDriver driver, String itemId){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Checking if the TV is the only item in the cart
        WebElement itemsInCart = getByXpath(driver, "//*[@id='spa-layout']/div/div/div[1]/div/h3/span");
        String[] items = itemsInCart.getText().split(" ");//Extracting the number of items in cart
        int count = Integer.parseInt(items[0]);
        if(count == 1)
            System.out.println("Right element is in the cart");
        else{
            System.out.println("Incorrect number of elements in cart");
            return;
        }

        String itemInCartId = getByXpath(driver, "//*[@id='CartItemInfo']").getAttribute("data-us-item-id");
        if(!itemId.equals(itemInCartId)) {
            System.out.println("Item in the cart fails the validation");
        }
    }
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws InterruptedException {

        //Running the Webdriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/bondi/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();

        homePage(driver);
        try {
            extraAdvertisements(driver);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        signInProcess(driver);
        search(driver);

        String itemId = addFirstMatchedItemToCart(driver);
        if(itemId != null) {
            viewCart(driver);
            checkingCart(driver, itemId);
        } else {
            System.out.println("No matched item in the cart");
            return;
        }

        //Making the thread to sleep so that elements gets time to load
        //Thread.sleep(5000);
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//Waits in case elements take time to show up

    }

}
