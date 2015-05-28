package com.redsquirrel.neogrifter.notifikation;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Grifter on 28/05/2015.
 */
public class UpdateActivityTest extends ActivityInstrumentationTestCase2<UpdateActivity> {

    private UpdateActivity activity;
    private EditText notifLabel;

    public UpdateActivityTest() {
        super(UpdateActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        notifLabel = (EditText) activity.findViewById(R.id.notif_label);
    }

    @MediumTest
    public void testNotifLabel() {
        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                notifLabel.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("This is Notif !");
        getInstrumentation().waitForIdleSync();
    }
}
