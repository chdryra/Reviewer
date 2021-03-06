/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.LoginProviders;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Implementation.DialogShower;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginGoogle;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LoginGoogleAndroid implements ActivityResultListener, LoginGoogle, GoogleApiClient
        .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int GOOGLE_SIGN_IN = RequestCodeGenerator.getCode("GoogleSignIn");
    private static final int GOOGLE_CLIENT_ID = R.string.google_client_id;
    private static final int REQUEST_RESOLVE_ERROR = RequestCodeGenerator.getCode
            ("GoogleSignInResolvingError");
    private static final String DIALOG_ERROR = "dialog_error";

    private final GoogleApiClient mGoogleApiClient;
    private final Activity mActivity;
    private Callback mListener;
    private LogoutCallback mLogoutCallback;
    private boolean mResolvingError = false;

    public LoginGoogleAndroid(Activity activity) {
        mActivity = activity;

        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(GOOGLE_CLIENT_ID))
                .requestScopes(new Scope(Scopes.EMAIL))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    mLogoutCallback.onLoggedOut(CallbackMessage.ok());
                } else {
                    String message = status.getStatusMessage();
                    mLogoutCallback.onLoggedOut(CallbackMessage.error(message != null ? message :
                            "Error logging out"));
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        mLogoutCallback.onLoggedOut(CallbackMessage.error("Connection suspended"));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (mResolvingError) {
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(mActivity, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        } else {
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    @Override
    public void logout(LogoutCallback callback) {
        mLogoutCallback = callback;
        mGoogleApiClient.connect();
    }

    @Override
    public void login(Callback loginCallback) {
        setListener(loginCallback);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GOOGLE_SIGN_IN && mListener != null) {
            notifyListener(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    private void setListener(Callback listener) {
        mListener = listener;
    }

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        DialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        DialogShower.show(dialogFragment, mActivity, REQUEST_RESOLVE_ERROR, DIALOG_ERROR);
    }

    private void notifyListener(GoogleSignInResult result) {
        if (result.isSuccess()) {
            mListener.onSuccess(result);
        } else {
            mListener.onFailure(result);
        }
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            //((MyActivity) getActivity()).onDialogDismissed();
        }
    }
}
