package rocks.teagantotally.sample.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import rocks.teagantotally.sample.PermissionProvider;
import rocks.teagantotally.sample.fragments.UserLandingFragment;

/**
 * Created by tglenn on 10/7/17.
 */

public class LoginViewModel
          extends BaseObservable {
    private Context context;
    private String userName;
    private String password;
    private View.OnClickListener submitClickListener =
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                          Toast.makeText(context,
                                         "Missing required field",
                                         Toast.LENGTH_SHORT)
                               .show();
                          return;
                      }
                      PermissionProvider.loggedIn = true;
                      UserLandingFragment.navigate(context,
                                                   userName,
                                                   password);
                  }
              };

    public LoginViewModel(Context context) {
        this.context = context;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public View.OnClickListener getSubmitClickListener() {
        return submitClickListener;
    }
}
