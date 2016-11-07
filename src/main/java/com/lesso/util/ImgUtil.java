package com.lesso.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by 0003 on 2016/8/3.
 */
public class ImgUtil {
    public static String getBase64CodeByImg(String imgFilePath) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    public static boolean getImgByBase64Code(String imgStr, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
           // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void main(String[]  args){
        System.out.println(getBase64CodeByImg("C:\\Users\\0003\\Desktop\\图片\\u=1581507609,222619640&fm=21&gp=0.jpg"));
        getImgByBase64Code("/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\n" +
                "HBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\n" +
                "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCADcAUoDASIA\n" +
                "AhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\n" +
                "AAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\n" +
                "ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\n" +
                "p6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\n" +
                "AwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\n" +
                "BhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\n" +
                "U1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\n" +
                "uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD2Siil\n" +
                "FYGgUUUUAFFFFABRRRQAtFFFABRRRQAoooooAKKKKBBS0lGaG0lqNJvYWikpwFYvER6Fqm+olFLi\n" +
                "jFR9YfYr2YlFLikxTWIXVC9mFFH4UVtGpGWxDi0FFFFWSFGKXFFABRRRQAUUUUAFFFFABRRRTAKK\n" +
                "XFJQAUUUUARYpaKKkYUUUUAFFFLQAlFLRQAUUUtACYpaKKACiiloATFGcUE4GaiL7j7VnUqqC8y4\n" +
                "QcmP3Z6UoFNFOFcLk5O7OhJLRDhSikFKKBMWiilpiENJS4oosAlNIp1IaBibsdadTDSBsHnpW9Ot\n" +
                "bSRnOn1RJRilHI4orrMBMUYpaKAExS0UtMBKKWkoAKKKKACilooASilpKAIqKKKkYUUUUAFFFLQA\n" +
                "UUUUAApaKKACiiloAKKKZM/lxM35Um7K4JXdiGWTLbR0HWgGqqt3qUNXmTm5y5md0YcqsWAacKhV\n" +
                "qkDUITRLmlBpgNLmrRLRIKWmA0uapE2HUhpN1JuoCwpNMJoJppNQykgJppIpC3NNLVJQ+OTacHoa\n" +
                "sVQY1ZtpN64PUV1Yep9lmFWH2kTUtFFdZgJiloooAKKKKYBRRRQAUUUUAFFFFAENFFFSMKKKWgAo\n" +
                "oooAKWkpaAClpBS0AFFFFAgqlqEmAiDvzV2snUH/ANLx6AVhiXamzairzGq1SBqqhqkDV5yZ22LK\n" +
                "vUqtVQNUivVJktFwNTgarq1SBqpMmxKDTs1EGpc1SFYfuppamlqYXphYeXphemFqYXqWOxIWppao\n" +
                "i9NL0h2JS1EEuydSeh4NQF6jZ6FLldwcbqxvUVHA/mQI/qKkr1U7q5wPTQKKKKYgooooAKKKKACi\n" +
                "lpKACiiloAgoooqRhS0lLQAUUUtABRRS0AFFFFABRRRTAKw9ROL5voK3KwdV+W+z6qK5cX/DN8N8\n" +
                "ZErU8NUKmng15qZ3NEwapA1VwacGqkyWi2j1KrVUVqkD1oiGi1vpDJ71WMuKiab3qrisWzL70wyV\n" +
                "UMp9aA9Fx8pZL00vUO6gtSuOw8tTS1MLU0tSY7DmfFRs9Md6iaSs2x2Oj01t1kntkVbqjpP/ACD0\n" +
                "PqT/ADq/Xr0vgR51T42JRilorQgKKKKACiiigAopaSgAopaSgCCjFFLUjCiiigBaKKKAAUtJS0AF\n" +
                "FFFABRRRTAKxdcTEkMnqCprbrP1iHzrBiBkod1Y4iPNTaNKMuWaZjRc1NsNLbW4WMNK4UntVwCIr\n" +
                "jdmvKUdNT0Gyj0pQaSXhjim5oGTK1L5mKh3YpjPzVpk2JWk5qNnqlPerBcRJJwkp2q3+16fjz+VS\n" +
                "eZmhyKSLG+lD1W30u+puDRaD0b6reZS+Z71VxcpOXpjSVA0lQSTgDrWbkNRJ3lqBpc1WefJqWyjN\n" +
                "1ewwj+Jhn6d6yTcpWRbjyq7O0sI/KsIFPXYCfxq1SAADA6UtfQRVkkeO3d3CiilqhCUUtFACUClo\n" +
                "oAKKKWgBKKKKAK9FLRUjCiiigBaKKKAFxRRRQAUUUUwClpKWgAqC9k8qylf0Wp6gvozLYzIvJKnH\n" +
                "1qZ35XYcfiVzkpp5GY8mmR3Mqt9408rmmFea8Bp3ue2rWLaylxk08Gq8Z4xUy1omZMVjxUMkgQEk\n" +
                "8VK2cVi65d/ZYC/ZEeVvoqk/zq0r6CHXrWl/DJavMhJ7BxuU9iPQg1Dpl9IzvY3hAvIRyRwJU7OP\n" +
                "6+hrL8P+CdI1Pw1Z3mpWvmX12n2mS4Vyr5f5hyPQEVV1PwVrWltFeaDqk119mbeltdnc2O6q3cH0\n" +
                "OK6XhdLJmSr+R2AJpdxrJ0PWodYtC6q0VxGdk8DjDxP3BFauK42nF2Z0Jpq6HbqRnxRioJWwKTdk\n" +
                "CQkk2KqvIWNDEsaztT1a30uMbg0s78RQRjc7n2FY+9N2RppFXZclmSBdznrwAOpPoK6TwhZO7S6h\n" +
                "OMHGyNfT1/GvPII/EV3d2rm2gt3uJljRJiWcAnnCjhcDnJ5r2iztUsrOK3ToigZ9T616GEwzU+aX\n" +
                "Q4sTXThyrqT0UUV6p5wUtFFABRRRQAUUUUAFFFLQAlFFFAFelFFFSMKWkooAWiiigBaKKKACiiim\n" +
                "AUUUUALRRketJuHrQI53VbI2spmRSYXOTj+E1nMwI612ZCupUgEHqD0rJuvD9vMS0MjQt6DkflXn\n" +
                "18K2+aB3UcSkuWZhRkVZSoLizn06fy5sEH7rjo1SRtmuGzi7S3Oq6auifbkVi67YG7tWTs6PET6B\n" +
                "lxn+Vb0YyMUSwbl5GQa2j3IvqZHhGfz/AAtYoRiW2jFtKv8AdeP5SP0z+NQ+MdYutA8PTX1nCJZg\n" +
                "wUbgSqZ/iIH+eRUxsrjTr+S9sFDCXH2i3JwJMfxA9m7eh7+tXf7TtZYwJILlQ3DI9sxx7HAIrvhU\n" +
                "jJXOeUGmebaXY+I9VvJNXjltPt62yTJJDjZOD/yykA43Y5HcVu6V4z0y8t2+2SiwuI8iWK4O3BHB\n" +
                "wT15rrYr6yij2wwyqo6Ilq4/TFZFnoMF5eahPd6fGYJrnzYEmjBI+VQTjnGWBNRWUJK73KpuSdhl\n" +
                "lqHnaQ+pSqyxNukRSOdn8PHqQM/jVafUGhgnluV2C3hDyf7xGSPw4/OuoeyVowpQFRggY4GOlY+q\n" +
                "2UTWlyJIRIjId6Y+/gdK4KiR0xZzV1rgS1lEDq7wx77iYcpD7cdWPZaveE0t7uykvvsc8dyZCjyX\n" +
                "PMjYx+Q56Dir+neHtOk8NJYmKN7eZRJL5fAZuD29D/KrEsieWbGwOWHDyA5EY75Pdq6o0404mEpy\n" +
                "mzQ8MWwv9YuNSYZhtgYIPQsfvN/SuyrO0KzSw0iCFFwMZ/OtGu2krQRxVXeTCloorUzCiiigAooo\n" +
                "oAKKKKAFopKKYC0lHeloArClooqBhRRS0AFFFFABSMwUc06qk8mX2+lNIRKJC1OGfWq6tUqtVWES\n" +
                "7sdaRpAEJpAc0bAaVhkKy7jUmMigwDtTSsidOaQDgSp61Mr7h71U3t3FODH1pXGJqEEVzbNFJ9VP\n" +
                "cGuXIe3lMcnBH610siFjnNY+ptBIgUAmVejDoK5cTSU1zLc6MPUcXZ7DYZBV+Mq64rn47jacE1ci\n" +
                "u8Y5rghO2jO1wvsarQAmmfZl9KiS9BHNSi6Q960TTItJDlt19KmSJR2qH7SnrQLyMHqKtWJaZaMQ\n" +
                "I6Vm3MClyMVb+3RqMk1nS6hHLMdvQcVFXlsOClcptodnK5ZoF+Y5OOAfqO9WorOOFViiRVXgBVGB\n" +
                "UyzrjrU1h/pWoIB92P5m/pUwXM1Ecm0mzfRQiKo6AYpaKK9g80KKKKYBS0lLQIKKKKACiiigAooo\n" +
                "oAKXNJRQBBRSUVIxaKKbIdqE0ANaXnA7UquTVVDUymrSJJ91V5ICzFgakBpwosBVKOvanK1WaXYp\n" +
                "6imBErU8NTvKHak8oilcLCh6duBpgQ+lIVNLQYrbcc1XeRB0p0g96h2AmlYZG5aTjOB7VVntkkUq\n" +
                "R+NXnG1cAYNQtG2M0uW40zkNSgls5Nw5SqUWppnaW2t6GututjoUkCMD2Nchq2hqzFrdtpP8LdPz\n" +
                "rz8TgubWB30MRbSRdW/HrUqXx/vVwl++oaQpfypXA/gUbs/TFTaZ4iW6j3OjxkfeVgcj8K8yVOtT\n" +
                "3O5OEkdbqGrPbWzMnzOeFHvXNme8EpnN5L5p5znj8q0YruO4QPFKpI5BHP5ipj9gm4ltHVz1+zvw\n" +
                "fwNaQcZrV2ZvTnGmtY3M2TxXe20REyo4A+9nFWdIur66iN9dkRxMf3UQGOPU1sWnhSbUQixaeLe1\n" +
                "yGaS4OWf047CustfCllEQ9yWuGHQH5VH4CuiOFnNaHPWxdCK0WpzdiLzUZBHaxlvVjwq/U122m2C\n" +
                "afbeWG3u3Luf4jVqOKOFAkSKiDoqjAp1d2HwsaWrd2eVWxDqaLRC0UlFdZzi0UlFAC0tJRQAtFJR\n" +
                "QAuaSiigApc0lFAC5ozSUcUAQUUmaXNSAU2XJjIAzTqKAKQZeh4PvUq4NTlVbqAaZ5MfYY+lPmCw\n" +
                "gpwNHlkdG/Ok2N6inzCsPBpwaq8khiUs+Ao75qCPU7SQ4WePPpuFHMh2ZpBhS5FVBcIeVcH6Gn+c\n" +
                "PUUXFYnzTSai85fUUxpxjrQMkIzTSFAqrNfQxLl5VUe5rAvvFUIDJZjznHf+EVEqkYrVlRg5bG5d\n" +
                "XkNsheVwoHqa5DXtfvbkQ22mS+W0pIY7eVHb86yDeX2rXZEr7j1A6AU+Dfb6kpnxlRxXmYzGSVNu\n" +
                "Gh10qCT1Niw8LWiqJLySS6uDyzyOTz9KdrMllpNuI0GZnHyoTwB6mpjqaxsOeGGRXDeML2WTWVkU\n" +
                "5jaIBfwJryMLVlKprJ/ed+GoqpUSlsWWmMjb2fcfXNKVDcsmffFcot1IGBL4Aq+2quYVUOQT716d\n" +
                "k9WexJWtFLQ1obS3tp3kiQh2wxGeODnpV5fEd1CTJHdKpHGI0VT+grmLu9aVYHDHcRhvrVVWKzk5\n" +
                "6jFaLTYI4an1ivuPXvB/ix9Wu3sLl/Mk2b0c4BOOo4rs814l4ElMfjGyA/iLrx6bTXtW4ZxkZruo\n" +
                "Tco6nzua0IUq9oKyauSZopuaM1ueaOopuaXNAC0UmaM0ALS5puaM0wFoozRmgAopM0ZoAWim5ozQ\n" +
                "A6im5ozQBT85aXzlrF+1+9H2z3rm9tE19mza89fWjz1rF+1+9H2v3o9tEPZs2vPX1pfPX1rE+1+9\n" +
                "H2v3o9tEPZs2/PWql7qsVnHknLHoorKn1HykJBy3YVlrFNeS7nySaipXsrR3LhSu9R13fXWoSHLE\n" +
                "J2UUtvpLyEEjFbFnpyxqCwGavFo4V5wKxVJy96bNHVUdIGdDpOwD52/A1LNa+XAxWRwQP71E2ojk\n" +
                "RjJrOna4nBBm2g9hV80I6Ii05bnFa9qmpQnEd7cogJDFHIrBstW1OS/I/tW7mhI5DSMcV21zoTzq\n" +
                "4Yh2z/30KoQ6Pb25YtFs46AVi3K909DZKNinphLrvlldycrl2z/Ordq/2Zm+XOaktNHmnlBwYoAe\n" +
                "PWrLaPcrKQGUp2OazlItJFGOeS1uPNjA57GpSXu5GlbAL8ADsauro8u4FmUj0NStpwtrN2LBpAQR\n" +
                "jsKxrrnpSQ42UkzDkvGjBimBUqePas3UfJvogpkw6/daunmsobxAXGGxwwrCvNNEEnB4ryINRd1u\n" +
                "dkKlndbnIT200L7ZFI9D2NTWtlcXTgRRswXkntXWxxqYwrKGHoRmplwgwoCj0HFdEsc0tFqdMsS3\n" +
                "01OPNtekMBaSnaefkNVLmea2uFWWGRD1wVrvi5AzniuW8YTMbaCQABQ5GfwrbD4yVWai0XDFTlJJ\n" +
                "lrwZrlvpOrSX9zGrME2x7v4c9T+VWtW8a3V74iN/bzSRxxlVhRWOOOv51wqXOV4NSW82J05zzXp8\n" +
                "00uU2VGm6ntHq9j2Sx+I0zOgurVTGSAzRgjH513MV6k0SyI2VYZB9q8Lku45AojyqbRke9el6Rcu\n" +
                "mkWqseRGK2hiLNqTPLzLA06cYypq1zrPtIpftIrn/th9aX7YfU1r9ZieT7Fm/wDaRR9pFYP2w+pp\n" +
                "Pth9aPrMRexZv/ah60n2oVg/bD60n2w+tH1mIexZv/ahR9qHrXP/AGw0fbG9aX1mI/Ys3/tQo+1j\n" +
                "1rn/ALY1H2w+tH1mIewZv/ax60n2setYH2tvWkN21L61EPYM6D7WPWj7YPWue+1tSfa29aPrUR+w\n" +
                "Y3yjS+UfWneYKXeK8rU7NBnlH1o8o+tSbxRvFGoaEflH1pHXYpYnpUu4VWuWMjBB071dOLkyZOxX\n" +
                "SNribnpW/Z2qxKDiq1jbBVBIq7POIY8Dqa7qcUveZzzlfRDp7kRDavJrMlLyn5mNDSbiSTSbhXLV\n" +
                "rObstjWnTUdeozyvel8r3p28UbxWGpqN8r3NIYATnFSbxRvFADPKo8r3p+4UbqAI/JqO4hH2eTP9\n" +
                "01YyKyfEmsJouktdMMguqdM8E8n8qOVy0Qr21IYuEAqO6tVuE96zLTxVo1wgKX0Iz2c7T+taKa3p\n" +
                "x/5fLUj/AK6ivK9hUTs4v7jRVI7pmcbaSI7SpNNKleorTk1vRkH76/tV/wC2oNYGp+LfDsGdl75j\n" +
                "ekak0LC1pfDFv5D9vHqy1JLDFGWcfrXOa9bHVbMlsrHGwbavU1ja540huYQljC4KnO5+P0rb8J+I\n" +
                "4tQ0GeO7iD3DScsBwBiu2nhKtCKrSViViIuVomFY+DNWv1E1rbsYGPysW4xW7b/DfVsBmlhQ+hbN\n" +
                "d94fIjtXhUAIpyoHvWzursdaUkbLEzWxw2neA7iOdGvbpDEhBKR5y1dssKIoVRgAYAp+6jNZt3Iq\n" +
                "1p1fjY3yhR5Yp2RS5FBkM8sUeUtPyKMigBvlrSGNafmkzQx2GeWtL5a07NJmkFhPLWk2L6Uu6lzQ\n" +
                "Anlr6Unlr6U6igBvlr6UeWtOzSZoAzxKxqRXJpyovpUioPSquKwgYmnZNPCio7jhOKcdXYT0BZEJ\n" +
                "OX6VLatDMSepB5rMncwqAoHzdSap2c8kVxIFY4xnmu+nBR0Oacmzs4mjC8Hp2rPuHMkpOeBVWOeT\n" +
                "zLfn745q3gEmliXaNkFFXd2Qc0YNTlRSYFecdRDg0YNTYFGBRcZDg0vNSYGaXAouIi5o5qXApMCk\n" +
                "AzJrkfiPGz+FmYDhZVJrscCqGsWEGpadJaXKlopOoBwauE+RqXYmS0aPm8sQxpwMigMd2G6E966T\n" +
                "xH4fs9MuCtu02PRmB/pXOFe2TgdK9ynVU0mjz5RswMjkY3GoiM9atwQLK4Ukge1dvong3S7yNZJz\n" +
                "OxOON4A/lU1sTGkryQRg2eeeUW4AJJ7Cu38NaPc2dsFeIiWQf6scmu6h8LaPp9uzW9moYD7zcn9a\n" +
                "NLgjS7RgOc9a8PFZkqy5IqyO+hR5PeZp+HbW7t7NjdLtL42qeoFbXNSUUkrKxo3djOaOadS0xDaW\n" +
                "lpaAEpMU4UUANop1FMLjcU3BqSilYLkeKUCn0GmFxtFLSUANop1FAH//2Q==","D:\\img\\test.jpg");
    }

}


