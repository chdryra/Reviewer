/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityUsersFeed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Other.EmailPasswordEditTexts;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterLogin;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentSignUp extends Fragment  {
    private static final int LAYOUT = R.layout.fragment_sign_up;

    private static final int EMAIL_SIGN_UP = R.id.email_sign_up;
    private static final int EMAIL_EDIT_TEXT = R.id.edit_text_sign_up_email;
    private static final int PASSWORD_EDIT_TEXT = R.id.edit_text_sign_up_password;
    private static final int PASSWORD_CONFIRM_EDIT_TEXT = R.id.edit_text_confirm_password;

    private static final int NAME_EDIT_TEXT = R.id.edit_text_author_name;

    private static final int SIGN_UP_BUTTON = R.id.sign_up_button;

    private PresenterLogin mPresenter;
    private EmailPasswordEditTexts mEmailPassword;

    public static FragmentSignUp newInstance() {
        return new FragmentSignUp();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LinearLayout emailSignUp = (LinearLayout) view.findViewById(EMAIL_SIGN_UP);
        EditText email = (EditText) emailSignUp.findViewById(EMAIL_EDIT_TEXT);
        EditText password = (EditText) emailSignUp.findViewById(PASSWORD_EDIT_TEXT);
        EditText passwordConfirm = (EditText) emailSignUp.findViewById(PASSWORD_CONFIRM_EDIT_TEXT);

        EditText name = (EditText) emailSignUp.findViewById(NAME_EDIT_TEXT);

        Button signUpButton = (Button) view.findViewById(SIGN_UP_BUTTON);

        mEmailPassword = new EmailPasswordEditTexts(email, password);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndCreateUser();
            }
        });

        return view;
    }

    private void validateAndCreateUser() {

    }

    private void launchFeedScreen() {
        Intent intent = new Intent(getActivity(), ActivityUsersFeed.class);
        startActivity(intent);
        getActivity().finish();
    }
}
