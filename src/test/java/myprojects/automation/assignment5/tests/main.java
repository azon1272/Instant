package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.utils.Properties;
import org.testng.annotations.Test;

public class main extends BaseTest {


    public static void main(String[] args) {


    }

    @Test(enabled = false)
    public void getCurULR() {
        driver.get(Properties.getDefaultBaseReturned());
        String url = driver.getCurrentUrl();
        if (url.equals("https://demo.instantcarloanapproval.ca/returned")) {
            System.out.println("current");
        } else {
            System.out.println("error");
            System.out.println(driver.getCurrentUrl());
            System.out.println(url);
        }
    }

}
