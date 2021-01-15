package com.example.mareu.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.R;

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
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddMeetingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addMeetingTest() {
        //test if there's no meeting
        onData(withId(R.id.list)).inAdapterView(hasChildCount(0));

        //click on addMeeting
        onView(withId(R.id.floatingActionButton)).perform(click());

        //click on editTextMeetingSubject and write a subject
        onView(withId(R.id.editTextMeetingSubject)).perform(replaceText("Meeting 1"));

        //click on editTextMeetingMaster and write the host of the meeting
        onView(withId(R.id.editTextMeetingMaster)).perform(replaceText("Lionel De Gans"), closeSoftKeyboard());

        //click on buttonDatePicker to choose a date
        onView(withId(R.id.buttonDatePicker)).perform(click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());

        //click on button timePicker to chose a hour
        onView(withId(R.id.buttonTimePicker)).perform(scrollTo(), click());
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());

        //write emails
        ViewInteraction mailTextView = onView(withId(R.id.complete));
        mailTextView.perform(typeText("lioneldegans@gmail.com\n"));
        mailTextView.perform(typeText("lionel.degans@shadline.com\n"), closeSoftKeyboard());

        //validate
        onView(withId(R.id.imageButtonValidate)).perform(click());

        //test if the meeting exists
        onData(withId(R.id.list)).inAdapterView(hasChildCount(1));
    }
}
