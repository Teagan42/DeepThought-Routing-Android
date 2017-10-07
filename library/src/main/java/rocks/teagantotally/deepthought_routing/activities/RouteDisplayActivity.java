package rocks.teagantotally.deepthought_routing.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rocks.teagantotally.deepthought_routing.R;
import rocks.teagantotally.deepthought_routing.Router;
import rocks.teagantotally.deepthought_routing.activities.viewmodels.RoutesViewModel;
import rocks.teagantotally.deepthought_routing.databinding.ActivityRoutesBinding;

/**
 * Created by tglenn on 10/2/17.
 */

public class RouteDisplayActivity
          extends AppCompatActivity {
    public static Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRoutesBinding binding = DataBindingUtil.setContentView(this,
                                                                       R.layout.activity_routes);
        if (router == null) {
            return;
        }
        binding.setRoutes(new RoutesViewModel(this,
                                              router.getRoutes()));
    }
}
