/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterProfile;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentProfile extends Fragment implements PresenterProfile.ProfileListener {
    private static final String ARGS = TagKeyGenerator.getKey(FragmentProfile.class, "Args");

    private static final int LAYOUT = R.layout.fragment_profile;
    private static final int NAME_EDIT_TEXT = R.id.edit_text_author_name;
    private static final int DONE_BUTTON = R.id.done_button;
    private static final int OK = Activity.RESULT_OK;
    private static final int CANCELED = Activity.RESULT_CANCELED;

    private PresenterProfile mPresenter;
    private AuthenticatedUser mUser;
    private AuthorProfileSnapshot mProfile;
    private EditText mName;

    private boolean mLocked = false;
    private String mLockReason;

    public static FragmentProfile newInstance(AuthenticatedUser user) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS, user);
        fragment.setArguments(bundle);


        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mName = (EditText) view.findViewById(NAME_EDIT_TEXT);

        Button doneButton = (Button) view.findViewById(DONE_BUTTON);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        mPresenter = new PresenterProfile.Builder().build(getApp(), this);

        if (!initialiseUser()) {
            lock("No user");
        } else if (mUser.getAuthorId() != null) {
            lock(Strings.EditTexts.FETCHING);
            mName.setText(Strings.EditTexts.FETCHING);
            mPresenter.getProfile(getUserAccount());
        }

        return view;
    }

    @Override
    public void onProfileFetched(AuthorProfileSnapshot profile, CallbackMessage message) {
        String text = message.getMessage();
        if (message.isOk()) {
            mProfile = profile;
            text = mProfile.getNamedAuthor().getName();
            unlock();
        }

        mName.setText(text);
    }

    @Override
    public void onProfileCreated(AuthorProfileSnapshot profile, CallbackMessage message) {
        if(message.isOk()) {
            makeToast(Strings.Toasts.PROFILE_CREATED);
        } else {
            makeToast(message.getMessage());
        }
        setResultAndClose(OK);
    }

    @Override
    public void onProfileUpdated(AuthorProfileSnapshot newProfile, CallbackMessage message) {
        makeToast(message.isOk() ? Strings.Toasts.PROFILE_UPDATED : message.getMessage());
        setResultAndClose(OK);
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private UserAccount getUserAccount() {
        return getApp().getAuthentication().getUserSession().getAccount();
    }

    private void lock(String reason) {
        mLocked = true;
        mLockReason = reason;
    }

    private void unlock() {
        mLocked = false;
    }

    private boolean initialiseUser() {
        Bundle args = getArguments();
        if (args == null) return false;
        mUser = args.getParcelable(ARGS);
        return mUser != null;
    }

    private void validate() {
        String name = mName.getText().toString();
        if (mLocked) {
            makeToast(mLockReason);
            setResultAndClose(CANCELED);
            return;
        }

        if (mUser.getAuthorId() == null) {
            mPresenter.newAccount(mUser, name, null);
        } else {
            if (mProfile != null && !name.equals(mProfile.getNamedAuthor().getName())) {
                NamedAuthor author = new DefaultNamedAuthor(name,
                        mProfile.getAuthor().getAuthorId());
                mPresenter.updateProfile(getUserAccount(),
                        mProfile,
                        new AuthorProfileSnapshot(author, mProfile.getJoined(), mProfile
                                .getProfilePhoto()));
            } else {
                setResultAndClose(CANCELED);
            }
        }
    }

    private void setResultAndClose(int result) {
        getActivity().setResult(result);
        getActivity().finish();
    }

    private void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
