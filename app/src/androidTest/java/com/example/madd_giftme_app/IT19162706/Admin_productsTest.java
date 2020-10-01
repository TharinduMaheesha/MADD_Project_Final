package com.example.madd_giftme_app.IT19162706;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.madd_giftme_app.Admin_edit_product;
import com.example.madd_giftme_app.Admin_home;
import com.example.madd_giftme_app.Admin_order_home;
import com.example.madd_giftme_app.Admin_products;
import com.example.madd_giftme_app.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class Admin_productsTest {

    @Rule
    public ActivityTestRule<Admin_products> adminProductsTest = new ActivityTestRule<>(Admin_products.class);

    Admin_products admin_products = null;

    Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(Admin_products.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(Admin_order_home.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor3 = getInstrumentation().addMonitor(Admin_home.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor4 = getInstrumentation().addMonitor(Admin_edit_product.class.getName(),null,false);



    @Before
    public void setUp() throws Exception {
        admin_products = adminProductsTest.getActivity();
    }

    @Test
    public void TestLaunchOfAdminProductsActivityOnButtonClick(){

        assertNotNull(admin_products.findViewById(R.id.btnProducts));
        onView(withId(R.id.btnProducts)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor1,500);
        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @Test
    public void TestLaunchOfAdminOrdersActivityOnButtonClick(){

        assertNotNull(admin_products.findViewById(R.id.btnOrders));
        onView(withId(R.id.btnOrders)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor2,500);
        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @Test
    public void TestLaunchOfAdminHomeActivityOnButtonClick(){

        assertNotNull(admin_products.findViewById(R.id.btnHome));
        onView(withId(R.id.btnHome)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor3,500);
        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @Test
    public void TestLaunchOfEditProductsActivityOnButtonClick(){

        assertNotNull(admin_products.findViewById(R.id.btn_admin_edit));
        onView(withId(R.id.btn_admin_edit)).perform(click());
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor4,500);
        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        admin_products = null;

    }

}