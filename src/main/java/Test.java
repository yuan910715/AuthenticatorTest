import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String token = Authenticator.generateSecretKey();
        System.out.println(token);
        String str = "otpauth://totp/" + "阿原@Test应用" + "?secret=" + token;
        ConsoleQRcode.printQRcode(str);

        Scanner scanner = new Scanner(System.in);
        String inputStr="";
        do {
            System.out.println("please input your 6 code to check:");
            inputStr = scanner.nextLine();
            if(inputStr.equals("")){
                break;
            }
            if(Authenticator.check_code(token,Long.valueOf(inputStr), System.currentTimeMillis())){
                System.out.println("yes!!!");
            }else{
                System.out.println("no!!!");
            }

        }while(true);

    }
}

class ConsoleQRcode {
    public static void printQRcode(String content) {
        int width = 300;        //定义图片宽度
        int height = 300;       //定义图片高度
        String format = "png";      //定义图片格式

        //定义二维码的参数
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");     //设置编码
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);       //设置容错等级，等级越高，容量越小
        hashMap.put(EncodeHintType.MARGIN, 2);          //设置边距
        //生成二维码
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hashMap);
            Path file = new File("D:/img.png").toPath();        //设置路径
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);       //输出图像
            System.out.println("qrcode : d:\\img.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
