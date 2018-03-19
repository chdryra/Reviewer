/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Authentication.Implementation.AuthorNameValidation;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterProfileEdit implements UserAccounts.CreateAccountCallback,
        UserAccounts.UpdateProfileCallback, ImageChooser.ImageChooserListener,
        ActivityResultListener {
    private static final String APP = ApplicationInstance.APP_NAME;
    private static final AuthenticationError INVALID_LENGTH = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason.INVALID_LENGTH
            .getMessage());
    private static final AuthenticationError INVALID_CHARACTERS = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason
            .INVALID_CHARACTERS.getMessage());
    private static final AuthenticationError UNKNOWN_ERROR = new AuthenticationError(APP,
            AuthenticationError.Reason.UNKNOWN_ERROR);
    private static final int IMAGE_REQUEST_CODE = RequestCodeGenerator.getCode
            (PresenterProfileEdit.class);

    private final UserAccounts mAccounts;
    private final ImageChooser mImageChooser;
    private final UiLauncher mLauncher;
    private final ProfileListener mListener;

    public interface ProfileListener {
        void onImageChosen(GvImage image, CallbackMessage message);

        void onProfileFetched(AuthorProfile profile, CallbackMessage message);

        void onProfileCreated(AuthorProfile profile, CallbackMessage message);

        void onProfileUpdated(@Nullable AuthorProfile newProfile, CallbackMessage message);

        void onProfileError(CallbackMessage message);
    }

    private PresenterProfileEdit(UserAccounts accounts, ImageChooser imageChooser, UiLauncher
            launcher, ProfileListener listener) {
        mAccounts = accounts;
        mImageChooser = imageChooser;
        mLauncher = launcher;
        mListener = listener;
    }

    public void createAccount(AuthenticatedUser user, String name, @Nullable Bitmap photo) {
        AuthorNameValidation validation = new AuthorNameValidation(name);
        String author = validation.getName();
        if (author == null) {
            notifySignUpError(getError(validation.getReason()));
        } else {
            mAccounts.createAccount(user, mAccounts.newProfile(author, photo), this);
        }
    }

    public AuthorProfile createUpdatedProfile(AuthorProfile oldProfile, @Nullable String newName,
                                              @Nullable Bitmap photo) {
        return mAccounts.newProfile(oldProfile, newName, photo);
    }

    public void getProfile(UserAccount account) {
        account.getAuthorProfile().dereference(new DataReference
                .DereferenceCallback<AuthorProfile>() {
            @Override
            public void onDereferenced(DataValue<AuthorProfile> value) {
                mListener.onProfileFetched(value.getData(), value.getMessage());
            }
        });
    }

    public void updateProfile(UserAccount account, AuthorProfile oldProfile, AuthorProfile
            newProfile) {
        mAccounts.updateProfile(account, oldProfile, newProfile, this);
    }

    public void launchImageChooser() {
        mLauncher.launchImageChooser(mImageChooser, IMAGE_REQUEST_CODE);
    }

    @Override
    public void onAccountUpdated(AuthorProfile profile, @Nullable AuthenticationError error) {
        if (error != null) {
            notifyOnError(profile, error);
            return;
        }

        mListener.onProfileUpdated(profile, CallbackMessage.ok(Strings.Callbacks.PROFILE_UPDATED));
    }

    @Override
    public void onAccountCreated(UserAccount account, AuthorProfile profile, @Nullable
            AuthenticationError error) {
        if (error != null) {
            notifyOnError(profile, error);
            return;
        }

        mListener.onProfileCreated(profile, CallbackMessage.ok(Strings.Callbacks.PROFILE_CREATED));
    }

    @Override
    public void onChosenImage(GvImage image) {
        CallbackMessage message = image.getBitmap() != null ?
                CallbackMessage.ok() : CallbackMessage.error("Error loading image");
        mListener.onImageChosen(image, message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        if (requestCode == IMAGE_REQUEST_CODE && mImageChooser.chosenImageExists(result, data)) {
            mImageChooser.getChosenImage(this);
        }
    }

    private void notifyOnError(AuthorProfile profile, AuthenticationError error) {
        String message;
        if (error.is(AuthenticationError.Reason.NAME_TAKEN)) {
            message = profile.getAuthor().getName() + " " + Strings.Callbacks.NAME_TAKEN;
        } else {
            message = error.getMessage();
        }

        mListener.onProfileError(CallbackMessage.error(message));
    }

    private void notifySignUpError(AuthenticationError error) {
        mListener.onProfileUpdated(null, CallbackMessage.error(error.getMessage()));
    }

    private AuthenticationError getError(AuthorNameValidation.Reason reason) {
        if (reason.equals(AuthorNameValidation.Reason.INVALID_LENGTH)) {
            return INVALID_LENGTH;
        } else if (reason.equals(AuthorNameValidation.Reason.INVALID_CHARACTERS)) {
            return INVALID_CHARACTERS;
        } else {
            return UNKNOWN_ERROR;
        }
    }

    public static class Builder {
        public PresenterProfileEdit build(ApplicationInstance app, String profileImageName,
                                          ProfileListener listener) {
            return new PresenterProfileEdit(app.getAccounts().getUserAccounts(),
                    app.getEditor().newImageChooser(profileImageName),
                    app.getUi().getLauncher(), listener);
        }
    }
}
