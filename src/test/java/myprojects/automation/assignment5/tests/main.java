package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;

public class main extends BaseTest {


    public static void main(String[] args) {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.out.println( "Windows");
        } else if (os.contains("nux") || os.contains("nix")) {
//                return "Linux";
            System.out.println( "Linux");
        }else if (os.contains("mac")) {
//                return “Mac”;
        }else {
//                return "Other";


        }


    }
}