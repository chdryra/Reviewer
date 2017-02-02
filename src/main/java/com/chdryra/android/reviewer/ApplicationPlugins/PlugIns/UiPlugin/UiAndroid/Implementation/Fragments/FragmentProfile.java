/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterProfile;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ProfileArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentProfile extends Fragment implements PresenterProfile.SignUpListener {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentProfile.class, "Args");

    private static final int LAYOUT = R.layout.fragment_sign_up;

    private static final int EMAIL_SIGN_UP = R.id.email_sign_up;
    private static final int EMAIL_EDIT_TEXT = R.id.edit_text_sign_up_email;
    private static final int PASSWORD_EDIT_TEXT = R.id.edit_text_sign_up_password;
    private static final int PASSWORD_CONFIRM_EDIT_TEXT = R.id.edit_text_confirm_password;

    private static final int NAME_EDIT_TEXT = R.id.edit_text_author_name;

    private static final int SIGN_UP_BUTTON = R.id.sign_up_button;

    private PresenterProfile mPresenter;
    private AuthenticatedUser mUser;
    private AuthorProfileSnapshot mProfile;
    private EditText mName;

    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    public static FragmentProfile newInstance(@Nullable ProfileArgs args) {
        FragmentProfile fragment = new FragmentProfile();
        if (args != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARGS, args);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LinearLayout emailSignUp = (LinearLayout) view.findViewById(EMAIL_SIGN_UP);
        mEmail = (EditText) emailSignUp.findViewById(EMAIL_EDIT_TEXT);
        mPassword = (EditText) emailSignUp.findViewById(PASSWORD_EDIT_TEXT);
        mPasswordConfirm = (EditText) emailSignUp.findViewById(PASSWORD_CONFIRM_EDIT_TEXT);

        mName = (EditText) view.findViewById(NAME_EDIT_TEXT);

        Button signUpButton = (Button) view.findViewById(SIGN_UP_BUTTON);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndCreateProfile();
            }
        });

        mPresenter = new PresenterProfile.Builder().build(getApp(), this);

        Bundle args = getArguments();
        if (args != null) initWithArgs(emailSignUp, args);

        return view;
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    @Override
    public void onSignUpComplete(@Nullable EmailPassword emailPassword, @Nullable AuthenticationError error) {
        if (error != null) {
            makeToast(error.getMessage());
        } else {
            makeToast("Sign up successful!");
            mPresenter.onSignUpComplete(emailPassword, getActivity());
        }
    }

    private void initWithArgs(LinearLayout emailSignUp, Bundle args) {
        ProfileArgs profileArgs = args.getParcelable(ARGS);
        if (profileArgs != null) {
            if (profileArgs.isEmailPassword()) {
                initEmailSignup(profileArgs.getEmail());
            } else {
                emailSignUp.setVisibility(View.GONE);
                initAuthenticatedUser(profileArgs.getUser());
            }
        }
    }

    private void initAuthenticatedUser(@Nullable AuthenticatedUser user) {
        mUser = user;
        if (mUser == null) throw new IllegalStateException("User should not be null!");
        AuthorId authorId = mUser.getAuthorId();
        if(authorId != null) {
            AuthorReference reference = getApp().getRepository().getAuthorsRepository()
                    .getReference(authorId);
            reference.dereference(new DataReference.DereferenceCallback<NamedAuthor>() {
                @Override
                public void onDereferenced(DataValue<NamedAuthor> value) {
                    if(value.hasValue()) mName.setText(value.getData().getName());
                }
            });
        }
    }

    private void initEmailSignup(@Nullable EmailAddress emailAddress) {
        if (emailAddress != null) mEmail.setText(emailAddress.toString());
    }

    private void validateAndCreateProfile() {
        String name = mName.getText().toString();
        if (mUser != null) {
            mPresenter.signUpNewAuthor(mUser, name, null);
        } else {
            String password = mPassword.getText().toString();
            String passwordConfirm = mPasswordConfirm.getText().toString();
            if (!password.equals(passwordConfirm)) {
                makeToast("Passwords don't match");
                return;
            }

            String email = mEmail.getText().toString();
            mPresenter.signUpNewAuthor(email, password, name, null);
        }
    }

    private void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
