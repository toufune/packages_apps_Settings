package com.android.settings.nfc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.system.ResetDashboardFragment;

public class FelicaResetPreferenceController extends BasePreferenceController {
    private ResetDashboardFragment mHostFragment;
    private final UserManager mUm;

    public FelicaResetPreferenceController(Context context, String str) {
        super(context, str);
        mUm = context.getSystemService(UserManager.class);
    }

    public void setFragment(ResetDashboardFragment hostFragment) {
        mHostFragment = hostFragment;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        FelicaResetDialogFragment.show(mHostFragment);
        return true;
    }

    @Override
    public int getAvailabilityStatus() {
         return AVAILABLE;
    }
}