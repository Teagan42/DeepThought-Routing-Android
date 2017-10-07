package rocks.teagantotally.sample.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import rocks.teagantotally.sample.R;
import rocks.teagantotally.sample.databinding.ActivitySplashBinding;
import rocks.teagantotally.sample.fragments.LoginFragment;
import rocks.teagantotally.sample.fragments.RegisterFragment;

/**
 * Created by tglenn on 10/7/17.
 */

public class SplashActivity
          extends Activity {
    private ActivitySplashBinding binding;
    private View.OnClickListener loginClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      LoginFragment.navigate(SplashActivity.this);
                  }
              };
    private View.OnClickListener registerClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      RegisterFragment.navigate(SplashActivity.this);
                  }
              };

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,
                                                 R.layout.activity_splash);
        binding.setLoginClick(loginClickListener);
        binding.setRegisterClick(registerClickListener);
    }
}
