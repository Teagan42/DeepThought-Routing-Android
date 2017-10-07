package rocks.teagantotally.deepthought_routing;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public class BaseActivity {
        public BaseActivity() {

        }
    }

    public class MyActivity extends BaseActivity {
        public MyActivity() {
            super();
        }
    }

    @Test
    public void addition_isCorrect() throws
                                     Exception {
        Class<?> baseClass = BaseActivity.class;
        Class<? extends BaseActivity> myActivityClass = MyActivity.class;

        assertTrue(baseClass.isAssignableFrom(myActivityClass));
    }
}