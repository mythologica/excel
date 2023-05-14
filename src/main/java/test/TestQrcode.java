package test;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestQrcode {

    public static void main(String[] args) {
        String fullFileName = "src/main/resources/data/a.bmp";
        String content = "qrcode sample";
        try {
            image2base64(fullFileName, content);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> image2base64(String fullFileName, String content) throws Exception {
        Map<String, String> data = new HashMap<>();

        String fileExtName = fullFileName.substring(fullFileName.lastIndexOf(".") + 1);

        FileInputStream inputStream = null;
        ByteArrayOutputStream byteOutStream = null;

        try {
            File file = new File(fullFileName);

            if (file.exists()) {
                inputStream = new FileInputStream(file);
                byteOutStream = new ByteArrayOutputStream();

                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = inputStream.read(buf)) != -1) {
                    byteOutStream.write(buf, 0, len);
                }

                byte[] fileArray = byteOutStream.toByteArray();
                String base64str =  new String(Base64.encodeBase64(fileArray));
                String imageUrl = "data:image /" + fileExtName + ";base64, " + base64str;
                data.put("base64",base64str);
                data.put("imageUrl",imageUrl);

                System.out.println("base64str");
                System.out.println(base64str);
                System.out.println("imageUrl");
                System.out.println(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            byteOutStream.close();
        }

        return data;
    }

}
