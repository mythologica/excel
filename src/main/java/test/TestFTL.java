package test;

import org.apache.velocity.VelocityContext;
import org.example.common.freemarker.FTLParser;

public class TestVM {

    public void doTest() {
        try{
            String path = "/resources/mail";
            String fileName = "mail";
//            VelocityContext context = new VelocityContext();
//
//            context.put("title","메일");
//
//            String str = new FTLParser("C:\\data\\dev\\workspace\\java\\excel\\src\\main\\resources\\mail\\mail_kr.vm", "kr" , context).parse();
//
//            System.out.println("mail:"+str);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
