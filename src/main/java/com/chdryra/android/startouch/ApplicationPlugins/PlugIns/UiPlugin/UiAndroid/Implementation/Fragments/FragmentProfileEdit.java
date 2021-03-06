/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.CellDimensionsCalculator;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .PresenterProfileEdit;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentProfileEdit extends Fragment implements PresenterProfileEdit
        .ProfileListener, ActivityResultListener {
    private static final String PROFILE_IMAGE_FILENAME = "Profile" + ApplicationInstance.APP_NAME;
    private static final int LAYOUT = R.layout.fragment_profile_edit;
    private static final int PROFILE_IMAGE = R.id.profile_photo;
    private static final int PROFILE_AUTHOR = R.id.edit_text_author_name;
    private static final int CANCEL_BUTTON = R.id.cancel_button;
    private static final int DONE_BUTTON = R.id.done_button;
    private static final int OK = Activity.RESULT_OK;
    private static final int CANCELED = Activity.RESULT_CANCELED;
    private static final int IMAGE_PLACEHOLDER = R.drawable.ic_face_black_36dp;

    private static final ReviewViewParams.CellDimension HALF = ReviewViewParams.CellDimension.HALF;
    private static final int IMAGE_PADDING = R.dimen.profile_image_padding;

    private PresenterProfileEdit mPresenter;
    private AuthorProfile mProfile;
    private EditText mName;
    private ImageView mImageView;

    private boolean mLocked = false;
    private String mLockReason;
    private Bitmap mImage;

    public static FragmentProfileEdit newInstance() {
        return new FragmentProfileEdit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mImageView = view.findViewById(PROFILE_IMAGE);
        mName = view.findViewById(PROFILE_AUTHOR);
        Button doneButton = view.findViewById(DONE_BUTTON);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrUpdateOrCancel();
            }
        });
        Button cancelButton = view.findViewById(CANCEL_BUTTON);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultAndClose(CANCELED);
            }
        });

        setImageView();

        ApplicationInstance app = getApp();
        mPresenter = new PresenterProfileEdit.Builder().build(app, PROFILE_IMAGE_FILENAME, this);

        if (app.getAccounts().getUserSession().getAuthorId() != null) {
            lock(Strings.EditTexts.FETCHING);
            mName.setText(Strings.EditTexts.FETCHING);
            mPresenter.getProfile(getUserAccount());
        }

        return view;
    }

    @Override
    public void onProfileFetched(AuthorProfile profile, CallbackMessage message) {
        if (message.isOk()) {
            setProfile(profile);
            unlock();
        } else {
            mName.setText(message.getMessage());
        }
    }

    @Override
    public void onProfileCreated(AuthorProfile profile, CallbackMessage message) {
        toastAndClose(message);
    }

    @Override
    public void onProfileUpdated(AuthorProfile newProfile, CallbackMessage message) {
        toastAndClose(message);
    }

    @Override
    public void onProfileError(CallbackMessage message) {
        mName.setText(mProfile != null ? mProfile.getAuthor().getName() : null);
        makeToast(message.getMessage());
    }

    @Override
    public void onImageChosen(GvImage image, CallbackMessage message) {
        if (message.isError()) {
            makeToast(message.getMessage());
        } else {
            setImage(image.getBitmap());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    private ApplicationInstance getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private UserAccount getUserAccount() {
        return getApp().getAccounts().getUserSession().getAccount();
    }

    private void setImageView() {
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        float padding = getResources().getDimension(IMAGE_PADDING);
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(HALF, HALF, (int)
                padding);
        mImageView.getLayoutParams().width = dims.getCellWidth();
        mImageView.getLayoutParams().height = dims.getCellHeight();
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) launchImageChooser();
                return true;
            }
        });
        setImagePlaceholder();
    }

    private void setImagePlaceholder() {
        mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), IMAGE_PLACEHOLDER));
    }

    private void launchImageChooser() {
        mPresenter.launchImageChooser();
    }

    private void setProfile(AuthorProfile profile) {
        mProfile = profile;
        mName.setText(mProfile.getAuthor().getName());
        ProfileImage image = profile.getImage();
        setImage(image.getBitmap());
    }

    private void setImage(@Nullable Bitmap bitmap) {
        mImage = bitmap;
        if (mImage != null) {
            mImageView.setImageBitmap(mImage);
        } else {
            setImagePlaceholder();
        }
    }

    private void toastAndClose(CallbackMessage message) {
        makeToast(message.getMessage());
        unlock();
        setResultAndClose(message.isOk() ? OK : CANCELED);
    }

    private void lock(String reason) {
        mLocked = true;
        mLockReason = reason;
    }

    private void unlock() {
        mLocked = false;
    }

    private void createOrUpdateOrCancel() {
        AuthenticatedUser user = getApp().getAccounts().getUserSession().getUser();
        String name = mName.getText().toString();
        if (mLocked) {
            makeToast(mLockReason);
            setResultAndClose(CANCELED);
            return;
        }

        if (mProfile != null) {
            AuthorProfile newProfile
                    = mPresenter.createUpdatedProfile(mProfile, name, mImage);
            if (!mProfile.equals(newProfile)) {
                makeToast(Strings.Toasts.UPDATING_PROFILE);
                mPresenter.updateProfile(getUserAccount(), mProfile, newProfile);
            } else {
                setResultAndClose(CANCELED);
            }
        } else if (user.getAuthorId() == null) {
            makeToast(Strings.Toasts.CREATING_ACCOUNT);
            mPresenter.createAccount(user, name, mImage);
        } else {
            setResultAndClose(CANCELED);
        }
    }

    private void setResultAndClose(int result) {
        getActivity().setResult(result);
        getActivity().finish();
    }

    private void makeToast(String message) {
        getApp().getUi().getCurrentScreen().showToast(message);
    }
}
