package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;

public class main extends BaseTest {


    public static void main(String[] args) {

        double leaseRate = 29500 - (1 * Math.pow(1.015, -36));
        double l4 = (((1 - Math.pow((1 + 0.015), -(36 - 1))) / 0.015) + 1) + 85;
        double l3 = leaseRate / (((1 - Math.pow((1 + 0.015), -(36 - 1))) / 0.015) + 1) + 85;
        double l5 = leaseRate / l3;
        System.out.println(l5);
        System.out.println(leaseRate);
        System.out.println(l4);
        System.out.println(l3);
//        System.out.println(Math.rint(100 * l3) / 100.0);


    }
}