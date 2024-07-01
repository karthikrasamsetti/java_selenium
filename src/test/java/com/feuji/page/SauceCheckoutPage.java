package com.feuji.page;

import com.feuji.utils.LoggerUtil;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

public class SauceCheckoutPage extends SauceHomePage {
    SoftAssert softAssert = new SoftAssert();
    @Getter
    private int initialCount;

    @Getter
    private int finalCount;
    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeEle;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartButton;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addToCartButton2;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement shoppingCartBadge;
    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement cartBtnElement;
    @FindBy(xpath = "//button[@name='checkout']")
    private WebElement checkoutBtnElement;
    @FindBy(id = "first-name")
    private WebElement firstNameElement;
    @FindBy(id = "last-name")
    private WebElement lastNameElement;
    @FindBy(id = "postal-code")
    private WebElement postalCodeEle;
    @FindBy(id = "continue")
    private WebElement continueBtnElement;
    @FindBy(id = "finish")
    private WebElement finishBtnElement;
    @FindBy(xpath = "//h2[text()='Thank you for your order!']")
    private WebElement headerTextElement;
    @FindBy(id = "back-to-products")
    private WebElement backToProductsBtnElement;
    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartBtnElement;

    public SauceCheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void removeProductFromCart() {
        addToCart();
        initialCount = Integer.parseInt(shoppingCartBadge.getText());
        LoggerUtil.log("Initial Count: " + initialCount);
        String buttonText = removeEle.getText();
        softAssert.assertEquals("Remove", buttonText, "Button text is not 'Remove'");
        if (!buttonText.equals("Add to cart")) {
            removeEle.click();
            String addToCartText = addToCartButton.getText();
            softAssert.assertEquals(addToCartText, "Add to cart", "Button text is not 'Add to cart'");
        }
        finalCount = Integer.parseInt(shoppingCartBadge.getText());
        LoggerUtil.log("Final Count " + finalCount);

    }

    public boolean isHeaderTextVisible() {
        return headerTextElement.isDisplayed();
    }

    public String getHeaderText() {
        String text = headerTextElement.getText();
        return text;
    }

    public void clickOnBackToHomeBtn() {
        backToProductsBtnElement.click();
    }

    public void checkoutProduct(String firstname, String lastname, String pincode) {
        addToCart();
        cartBtnElement.click();
        checkoutBtnElement.click();
        firstNameElement.sendKeys(firstname);
        lastNameElement.sendKeys(lastname);
        postalCodeEle.sendKeys(pincode);
        continueBtnElement.click();
        finishBtnElement.click();
    }
}
