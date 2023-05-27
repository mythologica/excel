package test;

import org.example.common.freemarker.FTLParser;

import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class TestFTL {

    public void doTest() {
        try{
//            String path = "C:\\data\\dev\\workspace\\java\\excel\\src\\main\\resources\\templates";
            String path = "/templates";
            String templateName = "mail";
            String lang = "kr";

            Map<String,Object> params = new HashMap<>();
            params.put("title","메일");

            List<Map<String,String>> rows = new ArrayList<>();

            Map<String,String> row = new HashMap<>();
            row.put("name","홍길동");
            row.put("age","19");
            rows.add(row);
            Map<String,String> row2 = new HashMap<>();
            row2.put("name","이순신");
            row2.put("age","40");
            rows.add(row2);
            params.put("rows", rows );

            List<String> userNames = new ArrayList<>();
            userNames.add("정상국");
            params.put("userNames",userNames);

            ZonedDateTime zdtSeoul = Year.of(2023)
                                        .atMonth(4)
                                        .atDay(27)
                                        .atTime(20,30)
                                        .atZone(ZoneId.of("Asia/Seoul"));
            Instant fromDate = zdtSeoul.toInstant();

            params.put("fromDate", Date.from(fromDate)  );

            ZonedDateTime zdtUs = fromDate.atZone(ZoneId.of("America/Vancouver"));

            params.put("zdtUs",  Date.from(zdtUs.toInstant())  );

            String result = new FTLParser(path).parse(templateName,lang,params);

            System.out.println("mail:"+result);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
