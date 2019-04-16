package com.feed.application;

import org.junit.Test;

import static org.junit.Assert.*;



public class RxApplicationTest {

    @Test
    public void getInstance() {
        assertNotNull(RxApplication.Companion.getInstance_());
        assertNotNull(RxApplication.Companion.getInstance_().getNetworkService());
    }
}