package com.feuji.page;

import com.feuji.utils.LoggerUtil;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SauceHomePage extends SauceLoginPage {
    public SauceHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='app_logo']")
    WebElement appLogo;
    @FindBy(xpath = "//select[@class='product_sort_container']")
    WebElement product_sort_container;
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    WebElement shoppingChartIcon;
    @FindBy(xpath = "(//div[@class='inventory_item_price'])[1]")
    WebElement firstProduct;

    @FindBy(xpath = "(//button[normalize-space()='Add to cart'])[1]")
    WebElement firstProductAddCart;
    @FindBy(xpath = "(//button[normalize-space()='Add to cart'])[2]")
    WebElement secondProductAddCart;
    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    WebElement addedProducts;
    private String price1;
    private String price2;
    @Getter
    private float priceInt1;
    @Getter
    private float priceInt2;
    private String count;
    @Getter
    private int countInt;
    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    WebElement logoutTab;
    String[] dropdownValues = {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"};

    @FindBy(id = "react-burger-menu-btn")
    private WebElement sideBarBtn;
    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeBtn;

    public void clickOnSideBarBtn() {
        sideBarBtn.click();
    }

    public boolean sideBarValidation(String element) {
        return driver.findElement(By.xpath("//a[text()='" + element + "']")).isDisplayed();
    }

    public void clickOnCloseBtn() {
        closeBtn.click();
    }

    @Getter
    @FindBy(xpath = "//a[text()='Twitter']")
    private WebElement twitterIcon;

    @FindBy(xpath = "//footer[@class='footer']/child::div")
    private WebElement footerText;
    @FindBy(xpath = "//a[text()='LinkedIn']")
    private WebElement linkedInIcon;

    public String getFooterText() {
        String text = footerText.getText();
        return text;
    }

    public void clickLinkedIn() {
        linkedInIcon.sendKeys(Keys.CONTROL, Keys.RETURN);
    }


    public void filterProducts() {
        product_sort_container.click();
        Select dropdown = new Select(product_sort_container);
        dropdown.selectByVisibleText(dropdownValues[2]);
        price1 = firstProduct.getText().substring(1);
        LoggerUtil.log("price ------" + price1);
        priceInt1 = Float.parseFloat(price1);
        LoggerUtil.log("price ------" + priceInt1);
        dropdown.selectByVisibleText(dropdownValues[3]);
        price2 = firstProduct.getText().substring(1);
        LoggerUtil.log("price ------" + price2);
        priceInt2 = Float.parseFloat(price2);
        LoggerUtil.log("price ------" + priceInt2);

    }

    public void addToCart() {
        firstProductAddCart.click();
        secondProductAddCart.click();
        count = addedProducts.getText();
        countInt = Integer.parseInt(count);
    }

    public void logOut() {
        sideBarBtn.click();
        logoutTab.click();
    }


}
