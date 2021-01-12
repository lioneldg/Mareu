package com.example.mareu.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.fragment_rv_list_container),
                                        childAtPosition(
                                                withId(R.id.activity_main),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextMeetingSubject),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("drfgdff"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextMeetingSubject), withText("drfgdff"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                0)));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextMeetingMaster),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("fgdfgd"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextMeetingMaster), withText("fgdfgd"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1)));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.complete),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                6)));
        appCompatAutoCompleteTextView.perform(scrollTo(), replaceText("lioneldegans@gmail.com\n"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.buttonDatePicker), withText(" Date"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                2)));
        materialTextView.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.buttonTimePicker), withText(" Heure"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                3)));
        materialTextView2.perform(scrollTo(), click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imageButtonValidate),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                8)));
        appCompatImageButton.perform(scrollTo(), click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.fragment_rv_list_container),
                                        childAtPosition(
                                                withId(R.id.activity_main),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextMeetingSubject),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                0)));
        appCompatEditText5.perform(scrollTo(), replaceText("ghghjh"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editTextMeetingSubject), withText("ghghjh"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                0)));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.editTextMeetingMaster),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1)));
        appCompatEditText7.perform(scrollTo(), replaceText("ghjghj"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.editTextMeetingMaster), withText("ghjghj"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1)));
        appCompatEditText8.perform(pressImeActionButton());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.complete),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                6)));
        appCompatAutoCompleteTextView2.perform(scrollTo(), replaceText("lioneldegans@gmail.com\n"), closeSoftKeyboard());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.buttonDatePicker), withText(" Date"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                2)));
        materialTextView3.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.buttonTimePicker), withText(" Heure"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                3)));
        materialTextView4.perform(scrollTo(), click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.imageButtonValidate),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                8)));
        appCompatImageButton2.perform(scrollTo(), click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                allOf(withId(R.id.fragment_rv_list_container),
                                        childAtPosition(
                                                withId(R.id.activity_main),
                                                0)),
                                1),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.editTextMeetingSubject),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                0)));
        appCompatEditText9.perform(scrollTo(), replaceText("gh"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.editTextMeetingSubject), withText("gh"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                0)));
        appCompatEditText10.perform(pressImeActionButton());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.editTextMeetingMaster),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1)));
        appCompatEditText11.perform(scrollTo(), replaceText("jjj"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.editTextMeetingMaster), withText("jjj"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                1)));
        appCompatEditText12.perform(pressImeActionButton());

        ViewInteraction appCompatAutoCompleteTextView3 = onView(
                allOf(withId(R.id.complete),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                6)));
        appCompatAutoCompleteTextView3.perform(scrollTo(), replaceText("lioneldegans@gmail.com\n"), closeSoftKeyboard());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(R.id.buttonDatePicker), withText(" Date"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                2)));
        materialTextView5.perform(scrollTo(), click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(R.id.buttonTimePicker), withText(" Heure"),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                3)));
        materialTextView6.perform(scrollTo(), click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.imageButtonValidate),
                        childAtPosition(
                                allOf(withId(R.id.activity_add_meeting),
                                        childAtPosition(
                                                withId(R.id.main_container),
                                                0)),
                                8)));
        appCompatImageButton3.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
