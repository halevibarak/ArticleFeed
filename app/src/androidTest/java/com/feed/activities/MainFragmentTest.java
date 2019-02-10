package com.feed.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import com.feed.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Barak Halevi on 07/02/2019.
 */
public class MainFragmentTest {
    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        activityTestRule.launchActivity(MY_ACTIVITY_INTENT);
    }


    @Test
    public void testUI() {
        Activity activity = activityTestRule.getActivity();
//        assertNotNull(activity.findViewById(R.id.title_text));
        assertNull(activity.findViewById(android.R.id.button1));
    }

    @Test
    public void checkFragment() throws InterruptedException {
        MainFragment fragment = new MainFragment();
        Thread.sleep(3000);
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        assertNotNull(fragment);
        assertNotNull(fragment.getView()!=null);
        if (fragment!=null && fragment.getView()!=null){
            assertNotNull(fragment.getView().findViewById(R.id.article_list));
            RecyclerView recyclerView = fragment.getView().findViewById(R.id.article_list);
            assertEquals(20,recyclerView.getAdapter().getItemCount());
        }

    }
    @Test
    public void testEspresso() {
        ViewInteraction interaction =
                onView(allOf(withId(R.id.title_text),
                        withText("net")));
//        interaction.perform(replaceText("how about some new text"));
    }
    @Test
    public void scrollToItemBelow() throws InterruptedException {
        // First, scroll to the position that needs to be matched and click on it.
        Thread.sleep(2000);
        onView(withId(R.id.article_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(18,
                        click()));

       onView(withId(R.id.web_view2));
    }
}