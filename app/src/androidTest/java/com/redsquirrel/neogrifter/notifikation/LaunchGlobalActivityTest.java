package com.redsquirrel.neogrifter.notifikation;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

public class LaunchGlobalActivityTest extends ActivityUnitTestCase<GlobalActivity> {

    private static final int TIMEOUT_IN_MS = 20;

    private Intent intent;
    private Button mCreateButton;

    public LaunchGlobalActivityTest() {
        super(GlobalActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        intent = new Intent(getInstrumentation().getTargetContext(), GlobalActivity.class);
        startActivity(intent, null, null);
        mCreateButton = (Button) getActivity().findViewById(R.id.notifications_add_button);
    }

    @MediumTest
    public void testUpdateActivityLaunched() {
        startActivity(intent, null, null);
        final Button createButton = (Button) getActivity().findViewById(R.id.notifications_add_button);
        createButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull(launchIntent);
        assertTrue(isFinishCalled());

        //final String payload = launchIntent.getStringExtra(UpdateActivity.EXTRAS_PAYLOAD_KEY);
        //assertEquals("Payload is empty", LaunchActivity.STRING_PAYLOAD, payload);
    }

    @MediumTest
    public void testUpdateActivityLaunchedByMonitor() {
        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(UpdateActivity.class.getName(), null, false);

        // Validate that ReceiverActivity is started
        TouchUtils.clickView(this, mCreateButton);
        UpdateActivity receiverActivity = (UpdateActivity) receiverActivityMonitor.waitForActivityWithTimeout(TIMEOUT_IN_MS);
        assertNotNull(receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", UpdateActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }
}
