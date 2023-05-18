package org.example.aws;

import org.example.TestExcelParser;

import java.io.BufferedReader;
import java.io.FileReader;

public class TestJson {
    public static void main(String[] args) {
        System.out.println("=================================[start] Excel TEST ================================= ");
        try {
            String[] fileNames = {"Bounce","Complaint","Delivery","Send","Reject","Open","Click","RenderingFailure","DeliveryDelay","Subscription"};

            for(String fileName:fileNames) {
                String jsonString = new TestJson().read(fileName);

                SesJsonParser parser = new SesJsonParser(jsonString);

                if( parser.isSuccess() ) {
                    System.out.println("getMessageId:"+parser.getMessageId());
                    System.out.println("getEventType:"+parser.getEventType());
                    System.out.println("getOption:"+parser.getOption());
                    System.out.println("getTimestamp:"+parser.getTimestamp());

                    System.out.println("toEmail:"+parser.getToEmail());
                    System.out.println("jsonString:"+parser.getJsonString());
                    System.out.println("serverType:"+parser.getServerType());
                } else {
                    System.out.println("error");
                }

                System.out.println("=========================================================================\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("=================================[end] Excel TEST ================================= ");
        }

    }

    private String read(String fileName) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\data\\dev\\workspace\\java\\excel\\src\\main\\resources\\aws\\"+fileName+".json"));
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception ce) {
            }
        }

        return sb.toString();
    }
}
