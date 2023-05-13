package org.example;

import lombok.extern.log4j.Log4j2;


/**
 * Hello world!
 */
@Log4j2
public class App {
    public static void main(String[] args) {
        System.out.println("=================================[start] Excel TEST ================================= ");
        try {

//            new TestJava().doTest();

//            new TestExcel().doTest();

            new TestParser().doTest();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("=================================[end] Excel TEST ================================= ");
        }

    }



    private void doTestParser() throws Exception {
        new TestParser().doTest();
    }

}
