package com.android.settings.nfc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.system.ResetDashboardFragment;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

public class FelicaResetDialogFragment extends InstrumentedDialogFragment implements
        DialogInterface.OnClickListener {
    public static final String TAG = "EraseFelicaDlg";
    static final String FELICA_ACTIVITY = "com.felicanetworks.mfm.memory_clear.MemoryClearActivity";
    static final String FELICA_PACKAGE = "com.felicanetworks.mfm.main";

    private MetricsFeatureProvider mMetricsFeatureProvider;
    
    public static void show(ResetDashboardFragment host) {
        if (host.getActivity() == null) {
            return;
        }
        final FelicaResetDialogFragment dialog = new FelicaResetDialogFragment();
        dialog.setTargetFragment(host, 0 /* requestCode */);
        final FragmentManager manager = host.getActivity().getSupportFragmentManager();
        dialog.show(manager, TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMetricsFeatureProvider = FeatureFactory.getFeatureFactory().getMetricsFeatureProvider();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.WITAQUA_SETTINGS;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.felica_reset_title)
                .setPositiveButton(R.string.felica_reset_button, this)
                .setNegativeButton(R.string.cancel, null)
                .setOnDismissListener(this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        final Fragment fragment = getTargetFragment();
        if (!(fragment instanceof ResetDashboardFragment)) {
            Log.e(TAG, "getTargetFragment return unexpected type");
        }
        if (which == DialogInterface.BUTTON_POSITIVE) {
            Context context = getContext();
            launchFelicaResetActivity(context);
        }
    }

    private void launchFelicaResetActivity(Context context) {
        try {
            final Intent intent = new Intent();
            intent.setClassName(FELICA_PACKAGE, FELICA_ACTIVITY);
            startActivityForResult(intent, 1000);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Felica reset activity not found", e);
        }
    }
}