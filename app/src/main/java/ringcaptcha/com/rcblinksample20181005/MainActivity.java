package ringcaptcha.com.rcblinksample20181005;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.thrivecom.ringcaptcha.RingcaptchaApplication;
import com.thrivecom.ringcaptcha.RingcaptchaApplicationHandler;
import com.thrivecom.ringcaptcha.RingcaptchaVerification;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSIONS = 1;
    public static final String RINGCAPTCHA_APP_KEY = "YOUR_APP_KEY";
    public static final String RINGCAPTCHA_SECRET_KEY = "YOUR_SECRET_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (this.checkSelfPermission(Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED || this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.READ_PHONE_STATE}, PERMISSIONS);
            } else {
                verify();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS);
            } else {
                verify();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    verify();
                } else {
                    Toast.makeText(this, "Permissions Rejected", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void verify() {
        RingcaptchaApplication.verifyPhoneNumberViaFlashCall(getApplicationContext(), RINGCAPTCHA_APP_KEY, RINGCAPTCHA_SECRET_KEY, new RingcaptchaApplicationHandler() {
            @Override
            public void onSuccess(RingcaptchaVerification ringObj) {
                Toast.makeText(getApplicationContext(), "Successfully verified!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
