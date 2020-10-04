package com.example.madd_giftme_app;

import android.app.Instrumentation;

import android.app.Instrumentation;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;


public class CancelOrderTest {

    @Rule
    public ActivityTestRule<CancelOrder> activityCancelOrder = new ActivityTestRule<>(CancelOrder.class);
    private CancelOrder cActivity  = null ;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Home.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        cActivity = activityCancelOrder.getActivity();

    }

    @Test
    public void testHomeButtonViewOrderProducts(){

        assertNotNull(cActivity.findViewById(R.id.cancel_order_button_go_to_home));
        onView(withId(R.id.cancel_order_button_go_to_home));
        getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

    }

    @After
    public void tearDown() throws Exception {

        cActivity = null ;
    }
}