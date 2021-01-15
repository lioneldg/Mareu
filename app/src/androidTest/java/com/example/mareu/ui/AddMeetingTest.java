package com.example.mareu.ui;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import com.example.mareu.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
        onView(withId(R.id.editTextMeetingSubject)).perform(typeText("Meeting 1"));

        //click on editTextMeetingMaster and write the host of the meeting
        onView(withId(R.id.editTextMeetingMaster)).perform(typeText("Lionel De Gans"), closeSoftKeyboard());

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
