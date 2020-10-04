package com.example.madd_giftme_app.ui.cart;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartFragmentFinalOrderTotalAmountTest {

    private static CartFragment cartFragment ;
    private double oneProductTotal, finalTotal ;

    @BeforeClass
    public static void initialObjectCreation(){

        cartFragment = new CartFragment();
    }

    @Before
    public void assignTotalAmountOfProducts(){

        oneProductTotal = 3000.00 ;
    }

    @Test
    public void checkFinalOrderAmount(){

        finalTotal = cartFragment.calculateTotalOrderPrice(oneProductTotal);
        assertEquals(3000, finalTotal, 0.001);
    }

    @After
    public void clearData() {

        oneProductTotal = 0.00 ;
        finalTotal = 0.00 ;
    }

    @AfterClass
    public static void deleteObject(){

        cartFragment = null ;
    }

}