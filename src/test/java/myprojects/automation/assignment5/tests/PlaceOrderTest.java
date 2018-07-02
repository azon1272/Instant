package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.Data;
import myprojects.automation.assignment5.utils.Properties;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {

    @DataProvider(name = "testParams")
    public static Object[][] params() {
        return new Object[][]{{"D Koperfild"}};
    }

    @Test(enabled = true, dataProvider = "testParams")
    public void checkSignature(String fullName) throws InterruptedException {
        driver.get(Properties.getBaseUrl());
        actions.getCarList();
        actions.getBudgetList();
        actions.useFor();
        actions.setPhoneNumber();
        actions.WeatherMessageBody();
        actions.setFullName(fullName);
        actions.setDayOfBirth();
        /*man or women*/
        actions.setGender();
        /*Work or no*/
        actions.getWorkType();//true no work/ false work
        if (actions.getWorkType() == true) {
            actions.setWorkExpirience();
            actions.setMonthlyIncome();
            actions.setNoConfirmationMonthlyIncome();
            actions.setUploadLaterLink();
            actions.setDownPayment();
            actions.getAviableCarList();
            actions.viewCar();
            actions.setDeliveryOptions();
            actions.orderSignature();
            System.out.println(Data.getCapCost() + "capcost");
            System.out.println(Data.getLeaseRate() + "leaserate");
            System.out.println(Data.getTaxes() + "taxes");
            System.out.println(Data.getTotalLeasePMT() + "total");
//            actions.setCardParams();
        } else {

            actions.setDownPayment();
        }


    }


}
