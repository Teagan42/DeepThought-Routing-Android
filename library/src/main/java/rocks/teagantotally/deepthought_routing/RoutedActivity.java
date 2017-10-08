package rocks.teagantotally.deepthought_routing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import rocks.teagantotally.deepthought_routing.interfaces.FragmentContainer;

/**
 * Created by tglenn on 9/29/17.
 */

public abstract class RoutedActivity
          extends AppCompatActivity
          implements FragmentContainer {

    private static final String TAG = "RoutedActivity";
    private static final String STATE_SKIP_FRAGMENT_ROUTING =
              RoutedActivity.class.getPackage()
                                  .getName() + ".skip_fragment_routing";

    private boolean skipFragmentRouting = false;

    /**
     * Set whether fragment routing should be skipped - useful for restarts, orientation changes, etc.
     *
     * @param skipFragmentRouting Whether to route to fragment
     */
    public void setSkipFragmentRouting(boolean skipFragmentRouting) {
        this.skipFragmentRouting = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }

        skipFragmentRouting = savedInstanceState.getBoolean(STATE_SKIP_FRAGMENT_ROUTING,
                                                            false);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        routeToFragment();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        routeToFragment();
    }

    @Override
    public void setContentView(View view,
                               ViewGroup.LayoutParams params) {
        super.setContentView(view,
                             params);
        routeToFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SKIP_FRAGMENT_ROUTING,
                            skipFragmentRouting);
    }

    private void routeToFragment() {
        if (getIntent().getExtras() == null || skipFragmentRouting) {
            return;
        }

        Bundle args = getIntent().getExtras();
        boolean isRouted = args.getBoolean(Router.EXTRA_ROUTED,
                                           false);
        boolean shouldLoadFragment = args.get(Router.EXTRA_FRAGMENT) != null
                                     && getContainerViewId() != 0;

        if (!isRouted || !shouldLoadFragment) {
            return;
        }

        String tag = (String) args.get(Router.EXTRA_ROUTE);
        Class fragmentClass = (Class) args.get(Router.EXTRA_FRAGMENT);
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(args);

            FragmentTransaction transaction =
                      getSupportFragmentManager().beginTransaction()
                                                 .replace(getContainerViewId(),
                                                          fragment,
                                                          tag);
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_NO_HISTORY) == 0) {
                transaction.addToBackStack(tag);
            }

            transaction.commit();
        } catch (Exception e) {
            Log.e(TAG,
                  "onCreate: Error adding fragment",
                  e
            );
        }
    }
}
