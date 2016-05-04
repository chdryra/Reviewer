/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ErrorDialogFragment extends DialogFragment {
    public static final String DIALOG_ERROR = TagKeyGenerator.getKey(ErrorDialogFragment.class, "DialogError");
    private static final int RESOLVE_ERROR = RequestCodeGenerator.getCode("ResolveErrorDialog");

    public ErrorDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int errorCode = this.getArguments().getInt(DIALOG_ERROR);
        return GoogleApiAvailability.getInstance().getErrorDialog(
                this.getActivity(), errorCode, RESOLVE_ERROR);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((FragmentGoogleLogin) getTargetFragment()).onDialogDismissed();
    }
}
