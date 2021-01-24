package com.example.mareu.ui;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.service.InterfaceMeetingApiService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteMeetingTest {
    private InterfaceMeetingApiService service = DI.getMeetingApiService();
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void deleteMeetingTest() {

        //test if there's no meeting
        onView(withId(R.id.listCell)).check(doesNotExist());

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

        //test if the meeting is displayed
        onView(withId(R.id.listCell)).check(matches(isDisplayed()));

        //delete the meeting
        onView(withId(R.id.delete)).perform(click());

        //test if there's no meeting
        onView(withId(R.id.listCell)).check(doesNotExist());

        //reset meetings
        service.clearMeetings();
    }
}
