package com.example.mareu.ui;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.service.InterfaceMeetingApiService;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddMeetingAvailabilityTest {
    private final InterfaceMeetingApiService service = DI.getMeetingApiService();

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void availabilityTest() {
        //test if there's no meeting
        onView(withId(R.id.listCell)).check(doesNotExist());

        //create a meeting on 2 january 2021 at 15h00 room 1
        createMeeting("Meeting 1",1, 2, 1, 2021, 15, 0);
        //test if the meeting is displayed
        onView(withId(R.id.listCell)).check(matches(isDisplayed()));

        //room 1 unavailable 44 minutes before (at 14h16)
        createMeeting("Meeting 2",1, 2, 1, 2021, 14, 44);
        //test if the meeting is not displayed
        onView(withId(R.id.listCell)).check(doesNotExist());

        //room 1 available 45 minutes before (at 14h15)
        createMeeting("Meeting 3",1, 2, 1, 2021, 14, 15);
        //test if the meeting is displayed
        onView(withId(R.id.listCell)).check(matches(isDisplayed()));

        //room 1 unavailable 44 minutes after (at 15h44)
        createMeeting("Meeting 4",1, 2, 1, 2021, 15, 44);
        //test if the meeting is not displayed
        onView(withId(R.id.listCell)).check(doesNotExist());

        //room 1 available 45 minutes after (at 15h45)
        createMeeting("Meeting 5",1, 2, 1, 2021, 15, 45);
        //test if the meeting is displayed
        onView(withId(R.id.listCell)).check(matches(isDisplayed()));

        //we can use room 2 if room 2 is available and room 1 isn't
        createMeeting("Meeting 6",2, 2, 1, 2021, 15, 0);
        //test if the meeting is displayed
        onView(withId(R.id.listCell)).check(matches(isDisplayed()));

        //reset meetings
        service.clearMeetings();
    }

    private void createMeeting(String subject, int room, int day, int month, int year, int hour, int minute){
        //click on addMeeting
        onView(withId(R.id.floatingActionButton)).perform(click());
        //click on editTextMeetingSubject and write a subject
        onView(withId(R.id.editTextMeetingSubject)).perform(typeText(subject));
        //click on editTextMeetingMaster and write the host of the meeting
        onView(withId(R.id.editTextMeetingMaster)).perform(typeText("Lionel De Gans"), closeSoftKeyboard());
        //click on buttonDatePicker to choose a date
        onView(withId(R.id.buttonDatePicker)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        //click on button timePicker to chose a hour
        onView(withId(R.id.buttonTimePicker)).perform(scrollTo(), click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour,minute));
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(click());
        //write emails
        ViewInteraction mailTextView = onView(withId(R.id.complete));
        mailTextView.perform(typeText("lioneldegans@gmail.com\n"));
        mailTextView.perform(typeText("lionel.degans@shadline.com\n"), closeSoftKeyboard());
        //validate
        onView(withId(R.id.imageButtonValidate)).perform(click());
    }
}
