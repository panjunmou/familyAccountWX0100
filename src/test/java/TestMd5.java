import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Created by PanJM on 2016/11/19.
 */
public class TestMd5 {

    @Test
    public void test() {
        String str = "panjunmou900204";
        String md5Hex = DigestUtils.md5Hex(str);
        System.out.println("md5Hex = " + md5Hex);
    }

    @Test
    public void test2() {
        String str = "926436";
        String md5Hex = DigestUtils.md5Hex(str);
        System.out.println("md5Hex = " + md5Hex);
    }
}
