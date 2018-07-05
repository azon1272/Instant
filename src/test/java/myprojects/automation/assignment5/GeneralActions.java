package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.Data;
import myprojects.automation.assignment5.utils.DataConverter;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private WebElement random;
    private JavascriptExecutor exec;

    By carTypeList = By.className("cartype-item");
    By budget = By.className("checkable-input");
    By wExpirience = By.className("checkable-input");
    By downPaymant = By.className("checkable-input");
    By Button = By.className("button");/*All buttons*/
    By inputField = By.className("input");/*phoneNumber,Full Name*/
    By birthdayList = By.xpath("//*[@class=\"phone-input\"]/div/input");/*birthdate*/
    By aviableCarList = By.className("car-item");
    private By uploadLaterLink = By.className("upload-sent");
    private By inputSMS1 = By.xpath("//*[@class=\"phone-input\"]/div/input[1]");
    private By inputSMS2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/input");
    private By inputSMS3 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[3]/div/input");
    private By inputSMS4 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[4]/div/input");
    private By getTestField = By.className("fieldForTest");
    @FindBy(className = "returned-button")
    private WebElement returnedButton;
    private By pageTitle = By.className("page-title");

    /*Card params*/
    @FindBy(id = "input1")
    private WebElement cardNumber;
    @FindBy(id = "input2")
    private WebElement cardHolder;
    @FindBy(name = "valid")
    private WebElement vaildTHRU;
    @FindBy(name = "code")
    private WebElement securityCode;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 60);
        exec = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    public GeneralActions getPageTitle(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
        return this;
    }

    public String getGetTestField() {
        String sms = null;
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            sms = driver.findElement(getTestField).getText();
        } catch (NoSuchElementException e) {
            CustomReporter.log("SMS not Found");
            CustomReporter.captureScreenshot(driver, "sms", "sms");
        }
        return sms;


    }

    public void getCarList() {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> links = driver.findElements(carTypeList);
            random = links.get(new Random().nextInt(links.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("Main page is passed");
        } catch (NullPointerException e) {
            CustomReporter.captureScreenshot(driver, "cartype", "cartype");
        }

    }

    public void getBudgetList() {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> links1 = driver.findElements(budget);
            random = links1.get(new Random().nextInt(links1.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("\n" + "Passed budget Page");
        } catch (NullPointerException e) {
            CustomReporter.log("\n" + "Budget page is failed");
            CustomReporter.captureScreenshot(driver, "budget", "budget");
        }
    }

    public void enableButton() {
        exec.executeScript("arguments[0].removeAttribute('disabled','disabled')", getElement());
    }

    public void useFor() {
        List<WebElement> links1 = driver.findElements(budget);
        random = links1.get(new Random().nextInt(links1.size()));
        random.click();
        waitForContenLoad(Button);
    }

    public WebElement getElement() {
        WebElement yourButton = driver.findElement(By.className("button"));
        return yourButton;
    }

    public void setPhoneNumber() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(inputField).sendKeys("(438)448-4228");
        exec.executeScript("arguments[0].removeAttribute('disabled','disabled')", getElement());
        WebElement searchinput = driver.findElement(By.className("input"));
        exec.executeScript("arguments[0].value='(438)448-4228';", searchinput);

        if (getElement().isEnabled())
            getElement().click();
    }

    public void WeatherMessageBody() throws InterruptedException {
        By reciveSMSTITLE = By.xpath("//h1[.=\"Type received code\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(reciveSMSTITLE));
        Assert.assertEquals("Type received code", driver.findElement(reciveSMSTITLE).getText());
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String SMS = getGetTestField();
            String smsArray[] = new String[4];
            Assert.assertTrue(driver.findElement(reciveSMSTITLE)
                    .getText()
                    .contains("Type received code"));

            WebElement yourButton = driver.findElement(By.className("button"));
            exec.executeScript("arguments[0].removeAttribute('disabled','disabled')", yourButton);
            Thread.sleep(500);
            for (int i = 0; i < SMS.length(); i++) {
                smsArray[i] = String.valueOf(SMS.charAt(i));
                Thread.sleep(100);
            }
            driver.findElement(inputSMS1).sendKeys(smsArray[0]);
            Thread.sleep(200);
            driver.findElement(inputSMS2).sendKeys(smsArray[1]);
            Thread.sleep(200);
            driver.findElement(inputSMS3).sendKeys(smsArray[2]);
            Thread.sleep(200);
            driver.findElement(inputSMS4).sendKeys(smsArray[3]);
//        isElementEnabled(Button, "SMS");


            if (yourButton.isEnabled()) {
                yourButton.click();
            } else {
                driver.findElement(Button).click();
            }
            CustomReporter.log("\n Set SMS Page is passed");
        } catch (NoSuchElementException e) {
            CustomReporter.logAction("Button not clicked on the set message page");
            CustomReporter.captureScreenshot(driver, "sms", "sms");
        }


    }

    public void setFullName() throws InterruptedException {
//        Thread.sleep(1000);
        By birthTite = By.xpath("//h1[.=\"What is your full name ?\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(birthTite));
        Assert.assertEquals("What is your full name ?", driver.findElement(birthTite).getText());
        try {
            enableButton();
            new Actions(driver).moveToElement(driver.findElement(inputField)).perform();
            CustomReporter.logAction("\n Passed Full Name");
            wait.until(ExpectedConditions.visibilityOfElementLocated(inputField)).sendKeys("k Kobein");
            Thread.sleep(500);
            WebElement ele = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button"));
            exec.executeScript("arguments[0].click();", ele);
            CustomReporter.log("\n Passed Full name page");
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "fullname", "fullname");
            CustomReporter.logAction("\n Full name is failed");
        }

    }


    public void setDayOfBirth() throws InterruptedException {
        By birthTtle = By.xpath("//h1[.=\"Your birthday\"]");
        wait.until(ExpectedConditions.titleIs("Instant car loan approval - Canada"));
        Assert.assertEquals("Your birthday", driver.findElement(birthTtle).getText());
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            enableButton();
            By d1 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]");
            By d2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]");
            By m1 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[3]");
            By m2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[4]");
            By y1 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[5]");
            By y2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[6]");
            By y3 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[7]");
            By y4 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[8]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(d1));
            actions.moveToElement(driver.findElement(d1)).sendKeys("1").perform();
            Thread.sleep(150);
            actions.moveToElement(driver.findElement(d2)).sendKeys("1").perform();
            Thread.sleep(150);
            actions.moveToElement(driver.findElement(m1)).sendKeys("1").perform();
            Thread.sleep(150);
            actions.moveToElement(driver.findElement(m2)).sendKeys("1").perform();
            Thread.sleep(300);
            driver.findElement(y1).click();
            actions.moveToElement(driver.findElement(y1)).sendKeys("1").perform();
            Thread.sleep(200);
            actions.moveToElement(driver.findElement(y2)).sendKeys("9").perform();
            Thread.sleep(200);
            actions.moveToElement(driver.findElement(y3)).sendKeys("8").perform();
            Thread.sleep(175);
            actions.moveToElement(driver.findElement(y4)).sendKeys("8").perform();
            CustomReporter.logAction("Birthday entered");
            Thread.sleep(500);
            WebElement ele = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button"));
            exec.executeScript("arguments[0].click();", ele);

        } catch (NullPointerException e) {
            CustomReporter.logAction("\n Error on set BithDay page");
            CustomReporter.captureScreenshot(driver, "birthday", "birthdaypage");
        }

    }

    public void setGender() {

//        List<WebElement> links1 = driver.findElements(Button);
//        random = links1.get(new Random().nextInt(links1.size()));
//
//        if (!links1.isEmpty()) {
//            random = links1.get(new Random().nextInt(links1.size()));
//        }
//        wait.until(ExpectedConditions.visibilityOfElementLocated(genderButton));
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement genderButton = driver.findElement(By.xpath("//*[@class=\"gender-types\"]/button[2]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", genderButton);
            CustomReporter.log("step Set Gender is Passed");
        } catch (NoSuchElementException e) {
            System.out.println("false");
            CustomReporter.logAction("Fail Gander");
        }
    }

    public boolean getWorkType() {

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement buttonYes = driver.findElement(By.xpath("//*[@class=\"working-types\"]/button[1]"));
            wait.until(ExpectedConditions.visibilityOf(buttonYes));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", buttonYes);
            CustomReporter.log("Step Set WorkType is Passed");
            return true;

        } catch (NoSuchElementException ex) {
            WebElement buttonNo = driver.findElement(By.xpath("//*[@class=\"working-types\"]/button[2]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", buttonNo);
            return false;
        }

    }

    public void setWorkExpirience() {
//        if(!items.isEmpty()) {
//            inv.setItem(i, items.get(r.nextInt(items.size())));
//        }
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> links1 = driver.findElements(wExpirience);
            random = links1.get(new Random().nextInt(links1.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("\n Passed workExpirience page");

        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Failed Work Expirience");
            CustomReporter.captureScreenshot(driver, "workExp", "worksexp");
        }

    }

    public void setMonthlyIncome() throws InterruptedException {
        Thread.sleep(1000);
        try {
            List<WebElement> links1 = driver.findElements(budget);
            random = links1.get(new Random().nextInt(links1.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("\n Passed monthlyIncome");
        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Fail monthlyincome");
            CustomReporter.captureScreenshot(driver, "monthlyincome", "monthly");
        }

    }

    public void setNoConfirmationMonthlyIncome() throws InterruptedException {

        Thread.sleep(1000);
//        noConfirmation.click();

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement confirmIncome = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button[1]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", confirmIncome);
            CustomReporter.log("\n Monthly income is confirmed");
        } catch (NoSuchElementException e) {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement confirmIncome = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button[1]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", confirmIncome);
            CustomReporter.log("\n Monthly income isn't confirmed");
            CustomReporter.captureScreenshot(driver, "confirmation", "confirmationPage");
        }

    }

    public void setUploadLaterLink() {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement uplLaterLink = driver.findElement(By.linkText("I will upload documents later"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", uplLaterLink);
            CustomReporter.log("\n Upload link click Passed");
        } catch (NoSuchElementException e) {
            driver.findElement(uploadLaterLink).click();
            CustomReporter.captureScreenshot(driver, "uploadlink", "uploadPage");
        }
    }

    public void setDownPayment() throws InterruptedException {
        By spanText = By.xpath("//span[.=\"available?\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(spanText));
//        Thread.sleep(1500);
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> links1 =
                    wait
                            .until(ExpectedConditions
                                    .visibilityOfAllElements(driver.findElements(downPaymant)));
            random = links1.get(new Random().nextInt(links1.size() - 1));
            random.click();
            CustomReporter.logAction("\n" + "Set downpayment" + random.getText());
            String dwPay = random.getText();
            dwPay = dwPay.startsWith("$") ? dwPay.substring(1) : dwPay;
            Data.downPayment = Integer.parseInt(dwPay);
            isElementEnabled(Button, "downpament", "downpayment");
            CustomReporter.log("\n Downpament page passed" + " " + dwPay);
        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Downpayment failed");
            CustomReporter.captureScreenshot(driver, "downPayment", "downpeyment");
        }

    }


    public void getAviableCarList() throws InterruptedException {

        By approvedTitlePage = By.xpath("//span[.=\"approved\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(approvedTitlePage));
        Thread.sleep(5000);
        try {
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            List<WebElement> links1 =
                    wait
                            .until(ExpectedConditions
                                    .visibilityOfAllElements(driver.findElements(aviableCarList)));
            random = links1.get(new Random().nextInt(links1.size() - 1));
            random.click();
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "carlist", "carlist");
        }
    }

    public void viewCar() {
//        By carBtnText = By.xpath("//button[.=\"Select this car\"]");
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement selectCar = driver.findElement(By.className("button"));
            new Actions(driver).moveToElement(driver.findElement(inputField)).perform();
            CustomReporter.logAction("Passed car selection");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", selectCar);
        } catch (NoSuchElementException e) {
            new Actions(driver).moveToElement(driver.findElement(Button)).perform();
            driver.findElement(Button).click();
            CustomReporter.logAction("Failed Car selection");
            CustomReporter.captureScreenshot(driver, "SelectCar", "selectcar");
        }
    }

    public void setDeliveryOptions() {
        By deliveryOptions = By.xpath("//p[.=\"Select delivery options\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryOptions));
        Assert.assertTrue(driver.findElement(deliveryOptions)
                .getText()
                .contains("Select delivery options"), "Contain");

        List<WebElement> itemHour = driver.findElements(By.className("item-hour"));
        random = itemHour.get(new Random().nextInt(itemHour.size() - 1));
        random.click();
    }

    public void orderSignature() throws InterruptedException {
        By signatureTitle = By.xpath("//p[.=\"Approval\"]");
        By signtatureFullName = By.xpath("//*[@class=\"page-signature\"]/div[6]/input");
        By vehicleCost = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[1]/li[1]/span");
        By leaseTerm = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[1]/span");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(signatureTitle)).getText();
        Assert.assertTrue(driver.findElement(signatureTitle)
                .getText()
                .contains("Approval"), "Okey");


        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            /*Get signature data*/

            driver.findElement(signtatureFullName).sendKeys(Keys.ARROW_DOWN, "K Kobein");
//            new Actions(driver).moveToElement(driver.findElement(signtatureFullName)).perform();
//            driver.findElement(signatureTitle).click();

            Thread.sleep(1500);
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "signatureerror", "signature");
            CustomReporter.logAction("\n Signature Page Error Look At screenshot");
        }


        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            String carCost = driver.findElement(vehicleCost).getText();
            /**/
            String vehCost = driver.findElement(vehicleCost).getText().replaceAll(",", "");
            vehCost = vehCost.startsWith("$") ? vehCost.substring(1) : vehCost;
            Data.vehicleCost = Double.parseDouble(vehCost);
            String lTerm = driver.findElement(leaseTerm).getText();
            lTerm = lTerm.substring(0, 2);
            Data.leaseTerm = Integer.parseInt(lTerm);
            checkOrderSignature();
//            wait.until(ExpectedConditions.elementToBeClickable(Button)).click();
        } catch (NullPointerException e) {

        }

    }

    public void checkOrderSignature() {
        By leaseRate = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[2]/span");
        By taxes = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[3]/span");
        By totalLeasePMT = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[4]/span");
        By capCost = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[1]/li[3]/span");
        try {
            Assert.assertEquals(Data.getLeaseRate()
                    , DataConverter.parseStringPrice(driver.findElement(leaseRate).getText()));
            CustomReporter.log("\n Lease rate is passed->" + Data.getLeaseRate() + ":" + DataConverter.parseStringPrice(driver.findElement(leaseRate).getText()));
            System.out.print("\n assertion_method_2() -> Part executed");
        } catch (NullPointerException e) {
            CustomReporter.log("\n Lease rate is fail" + Data.getLeaseRate() + ":" + DataConverter.parseStringPrice(driver.findElement(leaseRate).getText()));
        }

        try {
            Assert.assertEquals(Data.getTaxes()
                    , DataConverter.parseStringPrice(driver.findElement(taxes).getText()));
            CustomReporter.log("\n Taxes is passed ->" + Data.getTaxes() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(taxes).getText()));
        } catch (NullPointerException e) {
            CustomReporter.log("\n Taxes is failed - >" + Data.getTaxes() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(taxes).getText()));
        }

        try {
            Assert.assertEquals(Data.getTotalLeasePMT()
                    , DataConverter.parseStringPrice(driver.findElement(totalLeasePMT).getText()));
            CustomReporter.log("\n Total leasePMT is passed - >" + Data.getTotalLeasePMT() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(totalLeasePMT).getText()));
        } catch (NullPointerException e) {
            CustomReporter.log("\n Total leasePMT is failed ->" + Data.getTotalLeasePMT() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(totalLeasePMT).getText()));
        }


    }

    public void setCardParams() throws InterruptedException {
        By payDepositTitle = By.xpath("//p[.=\" Pay deposit\"]");
        By payDepositButton = By.xpath("//*[@class=\"page-payments\"]/button[1]");
//        Assert.assertTrue(driver.findElement(payDepositTitle).getText().contains(" Pay deposit"), "Pay deposit page");
        try {
            Random rndNum = new Random();
            for (int nbr = 0; nbr <= 8; nbr++) {
                String rndNum1 = String.valueOf(rndNum.nextInt());
                cardNumber.sendKeys(rndNum1);
                Thread.sleep(150);
            }
            cardHolder.sendKeys("k Kobein");
            vaildTHRU.sendKeys("01/19");
            for (int nbr = 0; nbr <= 3; nbr++) {
                String rndNum1 = String.valueOf(rndNum.nextInt());
                securityCode.sendKeys(rndNum1);
                Thread.sleep(50);
            }
            driver.findElement(payDepositButton).click();
        } catch (NullPointerException e) {
            CustomReporter.captureScreenshot(driver, "paydeposit", "paydeposit");
            CustomReporter.logAction("Somthing wrong on Pay deposit page");
        }
    }

//    public void WeatherMessageBody() {
//        try {
//            Thread.sleep(2500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Response resp = given().when().get("https://demo.instantcarloanapproval.ca/phone");
//        Response resp2 = given().when().get("https://demo.instantcarloanapproval.ca/phone");
//        System.out.println(resp.asString());
//        System.out.println(resp2);
//        Response response = given().body("{\"telephoneNumber\": \"+14384484228\"}").when()
//                .contentType(ContentType.JSON)
//                .post("https://demo.instantcarloanapproval.ca/api/sign-up");
//        String message = response.asString();
//        System.out.println(response.asString());
////        message = message.substring(90, message.length());
////        String subStr;
////        String delimeter = "\\:"; // Разделитель
////        subStr = message.replaceAll("[^\\d]", "");
////        // Вывод результата на экран
////        char[] activationCode = subStr.toCharArray();
////        try {
////            Thread.sleep(1000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////        System.out.println(subStr);
////        String[] myArray = new String[subStr.length()];
////        for (int i = 0; i < subStr.length(); i++) {
////            myArray[i] = (String.valueOf(subStr.charAt(i)));
////
////
//////            WebElement searchinput = driver.findElement(By.className("input"));
//////            JavascriptExecutor phone = ((JavascriptExecutor) driver);
//////            phone.executeScript("arguments[0].value='1';", searchinput);
////            wait
////                    .until(ExpectedConditions.visibilityOfElementLocated(SMS))
////                    .sendKeys((String.valueOf(subStr.charAt(i))));
//////            driver.findElement(SMS).sendKeys((String.valueOf(subStr.charAt(i))));
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//
//
//    }

    public GeneralActions getButton() {
        driver.findElement(Button).click();
        return this;
    }

    public boolean checkUser() {
        Boolean returned = null;
        By returnToHometitle = By.xpath("//h1[.=\"You are logged in as\"]");
        By deleteProfileLink = By.className("button-logout");
        By deleteButton = By.xpath("//*[@class=\"confirmation-buttons\"]/button[2]");

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(returnToHometitle));
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteProfileLink)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButton)).click();
            returned = true;
        } catch (NoSuchElementException e) {
            returned = false;
        }

        return returned;
    }

    public GeneralActions returnToHome() {
        returnedButton.click();
        return this;
    }

    public void waitForContenLoad(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
    }

    private boolean isElementPresent(By element, String usName, String pathName) {
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(element);
            return true;
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, usName, pathName);
            return false;
        }
    }

    private boolean isElementEnabled(By element, String screenName, String pathName) {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            if (driver.findElement(element).isEnabled()) {
                driver.findElement(element).click();
            }
            return true;
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, screenName, pathName);
            return false;
        }
    }

    public void getNewTab() {
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }


}
