package rocks.teagantotally.deepthought_routing.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rocks.teagantotally.deepthought_routing.Router;

/**
 * Created by tglenn on 9/30/17.
 */

public final class RouteProcessingActivity
          extends AppCompatActivity {
    private static Router router = null;

    public static void setRouter(Router router) {
        RouteProcessingActivity.router = router;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (router == null) {
            finish();
            return;
        }

        router.handleIntent(this,
                            getIntent());

        finish();
    }
}
