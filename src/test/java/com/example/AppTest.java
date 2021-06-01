package com.example;

import org.testng.Assert;

import org.testng.annotations.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    @Test
    public void test() {
        System.out.println("Test passed....");
        Assert.assertEquals(1, 1);
    }

}
