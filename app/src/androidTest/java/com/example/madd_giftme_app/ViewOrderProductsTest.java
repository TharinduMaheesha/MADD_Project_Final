package com.example.madd_giftme_app;

import android.app.Instrumentation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class ViewOrderProductsTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule(ViewOrderProducts.class);
    private Class<? extends ActivityScenarioRule> viewOrderProducts = null ;
    private ViewOrderProducts vActivity  = null ;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        viewOrderProducts = activityScenarioRule.getClass();
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
