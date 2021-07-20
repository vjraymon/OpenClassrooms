
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private static int FAVORITES_COUNT = 0;

    private AppCompatActivity mActivity;

    @Rule
    public ActivityTestRule<AppCompatActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // Select the tab "MY NEIGHBOURS"
        onView(allOf(ViewMatchers.withText("MY NEIGHBOURS"),ViewMatchers.isDisplayed()))
                .perform(click());
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Select the tab "MY NEIGHBOURS"
        onView(allOf(ViewMatchers.withText("MY NEIGHBOURS"),ViewMatchers.isDisplayed()))
                .perform(click());
        // Given : We remove the element at position 2
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myFavoriteNeighboursList_shouldBeEmptyAtInitialization() {
        // When perform a click on the tab "FAVORITES"
        onView(allOf(ViewMatchers.withText("FAVORITES"), ViewMatchers.isDisplayed()))
                .perform(click());
        // Check the expected count.
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .check(withItemCount(FAVORITES_COUNT));
    }

    /**
     * When we click on an item, the display activity is shown
     */
    @Test
    public void myFavoriteNeighboursList_add() {
        // When perform a click on the tab "MY NEIGHBOURS"
        onView(allOf(ViewMatchers.withText("MY NEIGHBOURS"), ViewMatchers.isDisplayed()))
                .perform(click());
        // Check it remains at least one item on the tab "MY NEIGHBOURS"
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
        // When perform a click on a line
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : it display "CAROLINE"
        onView(allOf(ViewMatchers.withId(R.id.displayName),ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.withText("Caroline")));
        // Click on add favorite
        onView(allOf(ViewMatchers.withId(R.id.add_favorite_neighbour),ViewMatchers.isDisplayed()))
                .perform(click());
        FAVORITES_COUNT++;
        onView(allOf(ViewMatchers.withId(R.id.displayName),ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.withText("Caroline")));
        // Click on the button return
        onView(allOf(ViewMatchers.withId(R.id.return_favorite_neighbour),ViewMatchers.isDisplayed()))
                .perform(click());
        // When perform a click on the tab "FAVORITES"
        onView(allOf(ViewMatchers.withText("FAVORITES"), ViewMatchers.isDisplayed()))
                .perform(click());
        // Check the expected count of items
        onView(allOf(ViewMatchers.withId(R.id.list_neighbours),ViewMatchers.isDisplayed()))
                .check(withItemCount(FAVORITES_COUNT));
    }

}