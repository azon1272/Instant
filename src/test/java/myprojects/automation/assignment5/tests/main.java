package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.utils.Properties;
import org.testng.annotations.Test;
import org.apache.xerces.util;

public class main extends BaseTest {


    public static void main(String[] args) {
        XMLStringBuffer buffer = new XMLStringBuffer();
        buffer.push(XMLReporterConfig.TAG_TESTNG_RESULTS);
        for (int i = 0; i < 100000; i++) {
            buffer.push("test", "count", i + "");
            buffer.pop();
        }

        buffer.pop();
        Utils.writeUtf8File(".", XMLReporter.FILE_NAME, buffer, null);
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
