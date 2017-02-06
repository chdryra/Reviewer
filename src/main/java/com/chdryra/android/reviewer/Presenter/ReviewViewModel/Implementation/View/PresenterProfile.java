/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorNameValidation;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterProfile implements UserAccounts.CreateAccountCallback,
        UserAccounts.UpdateProfileCallback, ImageChooser.ImageChooserListener, ActivityResultListener {
    private static final String APP = ApplicationInstance.APP_NAME;
    private static final AuthenticationError INVALID_LENGTH = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason.INVALID_LENGTH
            .getMessage());
    private static final AuthenticationError INVALID_CHARACTERS = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason
            .INVALID_CHARACTERS.getMessage());
    private static final AuthenticationError UNKNOWN_ERROR = new AuthenticationError(APP,
            AuthenticationError.Reason.UNKNOWN_ERROR);
    private static final int IMAGE_REQUEST_CODE = RequestCodeGenerator.getCode(PresenterProfile.class);

    private final UserAccounts mAccounts;
    private final ImageChooser mImageChooser;
    private final UiLauncher mLauncher;
    private final ProfileListener mListener;

    public interface ProfileListener {
        void onImageChosen(GvImage image, CallbackMessage message);

        void onProfileFetched(AuthorProfileSnapshot profile, CallbackMessage message);

        void onProfileCreated(AuthorProfileSnapshot profile, CallbackMessage message);

        void onProfileUpdated(@Nullable AuthorProfileSnapshot newProfile, CallbackMessage message);

        void onNameTaken(CallbackMessage message);
    }

    private PresenterProfile(UserAccounts accounts, ImageChooser imageChooser, UiLauncher launcher, ProfileListener listener) {
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

    public AuthorProfileSnapshot createUpdatedProfile(AuthorProfileSnapshot oldProfile, @Nullable String newName, @Nullable Bitmap photo) {
        return mAccounts.newUpdatedProfile(oldProfile, newName, photo);
    }

    public void getProfile(UserAccount account) {
        account.getAuthorProfile().getProfileSnapshot(new AuthorProfile.ProfileCallback() {
            @Override
            public void onProfile(AuthorProfileSnapshot profile, CallbackMessage message) {
                mListener.onProfileFetched(profile, message);
            }
        });
    }

    public void updateProfile(UserAccount account, AuthorProfileSnapshot oldProfile, AuthorProfileSnapshot newProfile) {
        mAccounts.updateProfile(account, oldProfile, newProfile, this);
    }

    @Override
    public void onAccountUpdated(AuthorProfileSnapshot profile, @Nullable AuthenticationError error) {
        CallbackMessage message = CallbackMessage.ok(Strings.Callbacks.PROFILE_UPDATED);
        if(error != null) {
            if(error.is(AuthenticationError.Reason.NAME_TAKEN)) {
                notifyNameTaken(profile);
                return;
            }
            message = CallbackMessage.error(error.getMessage());
        }
        mListener.onProfileUpdated(profile, message);
    }

    @Override
    public void onAccountCreated(UserAccount account, AuthorProfileSnapshot profile, @Nullable AuthenticationError error) {
        if(error != null && error.is(AuthenticationError.Reason.NAME_TAKEN)) {
            notifyNameTaken(profile);
            return;
        }

        account.getAuthorProfile().getProfileSnapshot(new AuthorProfile.ProfileCallback() {
            @Override
            public void onProfile(AuthorProfileSnapshot profile, CallbackMessage message) {
                CallbackMessage updated = message.isOk() ?
                        CallbackMessage.ok(Strings.Callbacks.PROFILE_CREATED) : message;
                mListener.onProfileCreated(profile, updated);
            }
        });
    }

    private void notifyNameTaken(AuthorProfileSnapshot profile) {
        String nameTaken = profile.getNamedAuthor().getName() + " " + Strings.Callbacks.NAME_TAKEN;
        mListener.onNameTaken(CallbackMessage.error(nameTaken));
    }

    @Override
    public void onChosenImage(GvImage image) {
        CallbackMessage message = image.getBitmap() != null ?
                CallbackMessage.ok() : CallbackMessage.error("Error loading image");
        mListener.onImageChosen(image, message);
    }

    public void launchImageChooser() {
        mLauncher.launchImageChooser(mImageChooser, IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        if (requestCode == IMAGE_REQUEST_CODE && mImageChooser.chosenImageExists(result, data)) {
            mImageChooser.getChosenImage(this);
        }
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
        public PresenterProfile build(ApplicationInstance app, String profileImageName, ProfileListener listener) {
            return new PresenterProfile(app.getAuthentication().getUserAccounts(),
                    app.getEditor().newImageChooser(profileImageName),
                    app.getUi().getLauncher(), listener);
        }
    }
}
