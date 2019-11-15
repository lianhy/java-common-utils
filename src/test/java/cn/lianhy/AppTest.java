package cn.lianhy;

import static org.junit.Assert.assertTrue;

import cn.lianhy.demo.constant.DateConstant;
import cn.lianhy.demo.utils.DateExtendUtils;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        System.out.println(DateExtendUtils.getInstance().getTime("2019-11-15 18:28:12", DateConstant.PATTERN_YMDHMS,DateConstant.INTERVAL_MILLISECOND));
    }
}
