package rocks.teagantotally.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import rocks.teagantotally.sample.Parameters;
import rocks.teagantotally.sample.PermissionProvider;

/**
 * Created by tglenn on 10/7/17.
 */

public class LoggedInActivity
          extends ContainerActivity {

    private String header = "Logged In";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        buildHeader();
        super.onCreate(savedInstanceState);
    }

    private void buildHeader() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        String userName = intent.getStringExtra(Parameters.PARAM_USERNAME);
        String password = intent.getStringExtra(Parameters.PARAM_PASSWORD);
        if (TextUtils.isEmpty(userName)) {
            return;
        }

        header += " - " + userName;

        if (TextUtils.isEmpty(password)) {
            return;
        }

        header += " - " + String.format("%0" + password.length() + "d",
                                        0)
                                .replace('0',
                                         '*');
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionProvider.loggedIn) {
            onBackPressed();
        }
    }

    /**
     * @return The header text
     */
    @Override
    protected String getHeader() {
        return header;
    }
}
