import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pjm.familyAccountWx.vo.SelectVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void test3() {
        String str = "[{\"title\":\"潘骏谋\",\"value\":\"3\"},{\"title\":\"徐碧莹\",\"value\":\"4\"}]";
        List<SelectVo> selectVoList = JSON.parseObject(str, new TypeReference<ArrayList<SelectVo>>(){});
        for (int i = 0; i < selectVoList.size(); i++) {
            SelectVo selectVo = selectVoList.get(i);
            System.out.println("selectVo.getTitle() = " + selectVo.getTitle());
        }
    }
}
