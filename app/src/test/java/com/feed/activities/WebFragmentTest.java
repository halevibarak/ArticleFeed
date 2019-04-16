package com.feed.activities;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;



public class WebFragmentTest {

    @Test
    public void onCreateView() {
        WebFragment WebFragment = new WebFragment();
        assertNotNull(WebFragment.getLink());
    }
}