package vergecurrency.vergewallet.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kevalpatel.passcodeview.PinView;
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPinAuthenticator;
import com.kevalpatel.passcodeview.indicators.CircleIndicator;
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener;
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder;
import com.kevalpatel.passcodeview.keys.RoundKey;

import vergecurrency.vergewallet.MainActivity;
import vergecurrency.vergewallet.R;

public class SetPinActivity extends AppCompatActivity {

    //TODO: Add check if first launch too, so that it's redirected to wallet creation.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        pinView = findViewById(R.id.pin_pad_view);

        //Hardcoded pin for now, I'm not done with implementation yet.
        final int[] correctPin = new int[]{1, 2, 3,4};

        //customize the pin
        pinView.setPinAuthenticator(new PasscodeViewPinAuthenticator(correctPin));

        pinView.setKey(new RoundKey.Builder(pinView)
                .setKeyPadding(R.dimen.lib_key_padding)
                .setKeyStrokeColorResource(R.color.colorAccent)
                .setKeyStrokeWidth(R.dimen.lib_key_stroke_width)
                .setKeyTextColorResource(R.color.colorAccent)
                .setKeyTextSize(R.dimen.lib_key_text_size));

        pinView.setIndicator(new CircleIndicator.Builder(pinView)
                .setIndicatorRadius(R.dimen.lib_indicator_radius)
                .setIndicatorFilledColorResource(R.color.colorAccent)
                .setIndicatorStrokeColorResource(R.color.colorAccent)
                .setIndicatorStrokeWidth(R.dimen.lib_indicator_stroke_width));

        pinView.setPinLength(4);

        pinView.setTitle("Enter your pin");

        pinView.setKeyNames(new KeyNamesBuilder()
                .setKeyOne(this, R.string.key_1)
                .setKeyTwo(this, R.string.key_2)
                .setKeyThree(this, R.string.key_3)
                .setKeyFour(this, R.string.key_4)
                .setKeyFive(this, R.string.key_5)
                .setKeySix(this, R.string.key_6)
                .setKeySeven(this, R.string.key_7)
                .setKeyEight(this, R.string.key_8)
                .setKeyNine(this, R.string.key_9)
                .setKeyZero(this, R.string.key_0));


        //Listener for code result to choose what to do if correct or not.
        pinView.setAuthenticationListener(new AuthenticationListener() {
            @Override
            public void onAuthenticationSuccessful() {

                //For now get to the main activity, later I'll implement the "get where you were part" or just finish the activity which is also fine.
                startActivity(new Intent(SetPinActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                //Calls whenever authentication is failed or user is unauthorized.
                //Do something if you want to handle unauthorized user.

                //I want the user to dance Macarena but there's no library for this yet...
            }
        });
    }

    private PinView pinView;
    private static final String ARG_CURRENT_PIN = "";
}
