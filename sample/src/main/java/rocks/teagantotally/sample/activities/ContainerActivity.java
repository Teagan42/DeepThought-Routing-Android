package rocks.teagantotally.sample.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import rocks.teagantotally.deepthought_routing.RoutedActivity;
import rocks.teagantotally.sample.R;

/**
 * Created by tglenn on 10/7/17.
 */

public abstract class ContainerActivity
          extends RoutedActivity {

    /**
     * @return The header text
     */
    protected abstract String getHeader();

    /**
     * Get the identifier of the view that is to contain the fragment to load
     *
     * @return View identifier
     */
    @Override
    public int getContainerViewId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this,
                                       R.layout.activity_container);
        getSupportActionBar().setTitle(getHeader());
    }
}
