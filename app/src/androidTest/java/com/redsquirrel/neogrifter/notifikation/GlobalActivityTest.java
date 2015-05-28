package com.redsquirrel.neogrifter.notifikation;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

public class GlobalActivityTest extends ActivityInstrumentationTestCase2<GlobalActivity> {

    private GlobalActivity globalActivity;
    private ListView listView;
    private Button addButton;

    public GlobalActivityTest() {
        super(GlobalActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        globalActivity = getActivity();
        listView = (ListView) globalActivity.findViewById(R.id.notifications_listview);
        addButton = (Button) globalActivity.findViewById(R.id.notifications_add_button);
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull(globalActivity);
        assertNotNull(listView);
        assertNotNull(addButton);
    }

    @SmallTest
    public void testAddButton() {
        final String expected = globalActivity.getString(R.string.button_add_new);
        final String actual = addButton.getText().toString();
        assertEquals(expected, actual);
    }

    @MediumTest
    public void testAddButton_layout() {
        final View decorView = globalActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, addButton);

        final ViewGroup.LayoutParams layoutParams = addButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @MediumTest
    public void testClickMeButton_clickButtonAndExpectInfoText() {
        //String expectedInfoText = globalActivity.getString(R.string.info_text);
        //TouchUtils.clickView(this, addButton);

        // TODO tester l'alerte ?
        //assertTrue(View.VISIBLE == mInfoTextView.getVisibility());
        //assertEquals(expectedInfoText, mInfoTextView.getText());
    }

    @MediumTest
    public void testListView_layout() {
        final View decorView = globalActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, listView);

        assertTrue(View.VISIBLE == listView.getVisibility());
    }

}
