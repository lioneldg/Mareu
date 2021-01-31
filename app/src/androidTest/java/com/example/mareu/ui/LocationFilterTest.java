package com.example.mareu.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.service.InterfaceMeetingApiService;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LocationFilterTest {
    private final InterfaceMeetingApiService service = DI.getMeetingApiService();

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void locationFilterTest() {
        //test if there's no meeting
        onView(withId(R.id.listCell)).check(doesNotExist());


        //add meeting 1
        //click on addMeeting
        onView(withId(R.id.floatingActionButton)).perform(click());

        //click on editTextMeetingSubject and write a subject
        onView(withId(R.id.editTextMeetingSubject)).perform(typeText("Meeting 1"));

        //click on editTextMeetingMaster and write the host of the meeting
        onView(withId(R.id.editTextMeetingMaster)).perform(typeText("Lionel De Gans"), closeSoftKeyboard());

        //click on buttonDatePicker to choose a date
        onView(withId(R.id.buttonDatePicker)).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());

        //click on button timePicker to chose a hour
        onView(withId(R.id.buttonTimePicker)).perform(scrollTo(), click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());

        //click on spinner
        onView(withId(R.id.spinnerRooms)).perform(click());
        //select the 2nd position
        onData(anything()).inAdapterView(childAtPosition(withClassName(is("android.widget.PopupWindow$PopupBackgroundView")))).atPosition(1).perform(click());

        //write emails
        ViewInteraction mailTextView = onView(withId(R.id.complete));
        mailTextView.perform(typeText("lioneldegans@gmail.com\n"));
        mailTextView.perform(typeText("lionel.degans@shadline.com\n"), closeSoftKeyboard());

        //validate
        onView(withId(R.id.imageButtonValidate)).perform(click());


        //add meeting 2
        //click on addMeeting
        onView(withId(R.id.floatingActionButton)).perform(click());

        //click on editTextMeetingSubject and write a subject
        onView(withId(R.id.editTextMeetingSubject)).perform(typeText("Meeting 2"));

        //click on editTextMeetingMaster and write the host of the meeting
        onView(withId(R.id.editTextMeetingMaster)).perform(typeText("Lionel De Gans"), closeSoftKeyboard());

        //click on buttonDatePicker to choose a date
        onView(withId(R.id.buttonDatePicker)).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());

        //click on button timePicker to chose a hour
        onView(withId(R.id.buttonTimePicker)).perform(scrollTo(), click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());

        //click on spinner
        onView(withId(R.id.spinnerRooms)).perform(click());
        //select the 4th position
        onData(anything()).inAdapterView(childAtPosition(withClassName(is("android.widget.PopupWindow$PopupBackgroundView")))).atPosition(3).perform(click());

        //write emails
        mailTextView = onView(withId(R.id.complete));
        mailTextView.perform(typeText("lioneldegans@gmail.com\n"));
        mailTextView.perform(typeText("lionel.degans@shadline.com\n"), closeSoftKeyboard());

        //validate
        onView(withId(R.id.imageButtonValidate)).perform(click());


        //test if the meetings exist
        onView(allOf(withId(R.id.subject), withText("Meeting 1"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.subject), withText("Meeting 2"))).check(matches(isDisplayed()));


        //click on filter by location
        onView(withId(R.id.locationFilter)).perform(click());

        //click on spinner
        onView(withId(R.id.spinner)).perform(click());

        //select the 2nd position
        onData(anything()).inAdapterView(allOf(withClassName(is("com.android.internal.app.AlertController$RecycleListView")), childAtPosition(withClassName(is("android.widget.LinearLayout"))))).atPosition(1).perform(click());

        //click on OK
        onView(withId(R.id.ok)).perform(click());

        //test if the meetings are filtered
        onView(allOf(withId(R.id.subject), withText("Meeting 1"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.subject), withText("Meeting 2"))).check(doesNotExist());

        //reset meetings
        service.clearMeetings();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + 0 + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(0));
            }
        };
    }
}
