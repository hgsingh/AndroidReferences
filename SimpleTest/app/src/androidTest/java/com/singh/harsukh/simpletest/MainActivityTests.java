package com.singh.harsukh.simpletest;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by harsukh on 3/22/16.
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity>  {

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testGreet() {
        MainActivity activity = getActivity();
        final EditText nameEditText =
                (EditText) activity.findViewById(R.id.greet_edit_text);
        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                nameEditText.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Faggot");

        Button greetButton =
                (Button) activity.findViewById(R.id.greet_button);
        TouchUtils.clickView(this, greetButton);

        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
        String actualText = greetMessage.getText().toString();
        assertEquals("Hello, Faggot!", actualText);
    }

//    public void testButton()
//    {
//        MainActivity activity = getActivity();
//        Button greetButton =
//                (Button) activity.findViewById(R.id.greet_button);
//        TouchUtils.clickView(this, greetButton);
//    }
//
//    public void testTextView()
//    {
//        MainActivity activity = getActivity();
//        TextView greetMessage = (TextView) activity.findViewById(R.id.message_text_view);
//        String actualText = greetMessage.getText().toString();
//        assertEquals("Hello, Faggot!", actualText);
//    }
}
