package com.example.madd_giftme_app.IT19210902;

import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.madd_giftme_app.Home;
import com.example.madd_giftme_app.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class ViewOrderProductsTest {

    @Rule
    public ActivityTestRule<ViewOrderProducts> activityViewOrderProducts = new ActivityTestRule<ViewOrderProducts>(ViewOrderProducts.class);
    private ViewOrderProducts vActivity  = null ;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        vActivity = activityViewOrderProducts.getActivity();
    }

    @Test
    public void testHomeButtonViewOrderProducts(){

        assertNotNull(vActivity.findViewById(R.id.order_products_button_go_to_home));
        onView(withId(R.id.order_products_button_go_to_home));
        getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

    }

    @After
    public void tearDown() throws Exception {

        vActivity = null ;

    }
}