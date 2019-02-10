package com.feed.application;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Barak Halevi on 09/02/2019.
 */
public class RxApplicationTest {

    @Test
    public void getInstance() {
        assertNotNull(RxApplication.Companion.getInstance_());
        assertNotNull(RxApplication.Companion.getInstance_().getNetworkService());
    }
}