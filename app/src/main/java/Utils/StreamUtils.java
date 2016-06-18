package Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2016/6/11.
 */
/*
* 
* 描    述：输入流转化为string返回
* 作    者：ksheng
* 时    间：12/27
*/
public class StreamUtils {
        /*
        * 将输入流读取成String后返回*/
        public static String readFormStream(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            String result = out.toString();
            in.close();
            out.close();
            return result;
        }
}
