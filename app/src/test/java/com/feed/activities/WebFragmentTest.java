package com.feed.activities;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Barak Halevi on 09/02/2019.
 */
public class WebFragmentTest {

    @Test
    public void onCreateView() {
        WebFragment WebFragment = new WebFragment();
        assertNotNull(WebFragment.getLink());
    }
}