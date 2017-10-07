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
import rocks.teagantotally.sample.R;
import rocks.teagantotally.sample.activities.LoggedOutActivity;
import rocks.teagantotally.sample.databinding.FragmentLoginBinding;
import rocks.teagantotally.sample.viewmodels.LoginViewModel;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

/**
 * Created by tglenn on 10/7/17.
 */

@FragmentRouteDefinition(
          value = LoginFragment.ROUTE,
          description = "Login page",
          activity = LoggedOutActivity.class
)
public class LoginFragment
          extends Fragment {
    public static final String ROUTE = "link://sample/login";

    public static void navigate(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(ROUTE));
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }

    private FragmentLoginBinding binding;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                                          R.layout.fragment_login,
                                          container,
                                          false);
        binding.setVm(new LoginViewModel(getContext()));

        return binding.getRoot();
    }
}
