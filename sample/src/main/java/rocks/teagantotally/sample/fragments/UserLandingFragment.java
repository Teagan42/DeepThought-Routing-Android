package rocks.teagantotally.sample.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rocks.teagantotally.deepthought_routing.annotations.FragmentRouteDefinition;
import rocks.teagantotally.deepthought_routing.annotations.ParameterType;
import rocks.teagantotally.deepthought_routing.annotations.QueryParam;
import rocks.teagantotally.sample.Parameters;
import rocks.teagantotally.sample.PermissionProvider;
import rocks.teagantotally.sample.R;
import rocks.teagantotally.sample.SampleApplication;
import rocks.teagantotally.sample.activities.LoggedInActivity;
import rocks.teagantotally.sample.databinding.FragmentUserLandingBinding;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;
import static rocks.teagantotally.sample.Parameters.PARAM_PASSWORD;
import static rocks.teagantotally.sample.Parameters.PARAM_USERNAME;

/**
 * Created by tglenn on 10/7/17.
 */

@FragmentRouteDefinition(
          value = UserLandingFragment.ROUTE,
          description = "Landing page for a logged in user",
          activity = LoggedInActivity.class,
          requiredPermissions = {
                    PermissionProvider.PERMISSION_ACCOUNT
          },
          queryParameters = {
                    @QueryParam(
                              value = Parameters.PARAM_USERNAME,
                              description = "The username of the logged in account",
                              type = ParameterType.STRING,
                              required = true),
                    @QueryParam(
                              value = Parameters.PARAM_PASSWORD,
                              description = "The password of the logged in account",
                              type = ParameterType.STRING,
                              required = false)}
)
public class UserLandingFragment
          extends Fragment {

    public static final String ROUTE = "link://sample/home";

    public static void navigate(Context context,
                                String userName,
                                String password) {
        Uri uri = Uri.parse(ROUTE)
                     .buildUpon()
                     .appendQueryParameter(PARAM_USERNAME,
                                           userName)
                     .appendQueryParameter(PARAM_PASSWORD,
                                           password)
                     .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    private View.OnClickListener routeClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent = new Intent(ACTION_VIEW);
                      intent.setData(SampleApplication.ROUTES_URI);
                      startActivity(intent);
                  }
              };

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentUserLandingBinding binding =
                  DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_user_landing,
                                          container,
                                          false);
        binding.setRoutesClick(routeClickListener);
        return binding.getRoot();
    }
}
