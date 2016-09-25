/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs
        .Implementation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogOneButtonFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.PublisherAndroid;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogShareEditReview extends DialogOneButtonFragment implements
        LaunchableUiAlertable {
    private static final int DIALOG_ALERT = RequestCodeGenerator.getCode("DeleteReview");
    private static final String TAG = TagKeyGenerator.getTag(DialogShareEditReview.class);

    private static final int LAYOUT = R.layout.dialog_share_edit_review;
    private static final int SHARE = R.id.button_share_review;
    private static final int ANOTHER = R.id.button_another_review;
    private static final int DELETE = R.id.button_delete_review;

    private DeleteRequestListener mDeleteListener;
    private NewReviewListener mNewReviewListener;
    private DatumAuthorId mAuthorId;
    private PublisherAndroid mSharer;
    private ApplicationInstance mApp;
    private boolean mShowDelete = false;

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUi() {
        View layout = android.view.LayoutInflater.from(getActivity()).inflate(LAYOUT, null);

        Button share = (Button) layout.findViewById(SHARE);
        Button another = (Button) layout.findViewById(ANOTHER);
        Button delete = (Button) layout.findViewById(DELETE);

        share.setOnClickListener(launchShareIntentOnClick());
        delete.setOnClickListener(launchDeleteAlertOnClick());
        another.setOnClickListener(requestNewReviewUsingTemplate());

        if (!mShowDelete) delete.setVisibility(View.GONE);

        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftButtonAction(ActionType.DONE);
        setDialogTitle(null);
        hideKeyboardOnLaunch();

        mApp = AndroidAppInstance.getInstance(getActivity());
        setAuthorIdAndDeleteOption();
        mNewReviewListener = getTargetListenerOrThrow(NewReviewListener.class);

        mSharer = new PublisherAndroid(getActivity(), new ReviewSummariser(), new
                ReviewFormatterTwitter());

    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == DIALOG_ALERT) {
            mDeleteListener.onDeleteRequested(mAuthorId.getReviewId());
            closeDialog();
        }
    }

    private View.OnClickListener requestNewReviewUsingTemplate() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewReviewListener.onNewReviewUsingTemplate(mAuthorId.getReviewId());
                closeDialog();
            }
        };
    }

    private void closeDialog() {
        DialogShareEditReview.this.dismiss();
    }

    @NonNull
    private RepositoryCallback fetchReviewCallback(final TagsManager
                                                                             tagsManager) {
        return new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                Review review = result.getReview();
                if (!result.isError() && review != null) {
                    mSharer.publish(review, tagsManager);
                } else {
                    String message = Strings.Toasts.REVIEW_NOT_FOUND;
                    if (result.isError()) message += ": " + result.getMessage();
                    mApp.getCurrentScreen().showToast(message);
                }
                closeDialog();
            }
        };
    }

    private void setAuthorIdAndDeleteOption() {
        Bundle args = getArguments();
        if (args != null) mAuthorId = args.getParcelable(getLaunchTag());

        if(mAuthorId == null){
            throw new IllegalArgumentException("Must pass DatumAuthorId in args!");
        }

        Object target = getTargetFragment() != null ? getTargetFragment() : getActivity();
        try {
            mDeleteListener = DeleteRequestListener.class.cast(target);
            if(mAuthorId.toString().equals(mApp.getUserSession().getAuthorId().toString())) {
                mShowDelete = true;
            }
        } catch (ClassCastException e) {
            mShowDelete = false;
        }
    }

    private View.OnClickListener launchDeleteAlertOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApp.getCurrentScreen().showAlert(Strings.Alerts.DELETE_REVIEW, DIALOG_ALERT, new
                        Bundle());
            }
        };
    }

    private View.OnClickListener launchShareIntentOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApp.getReview(mAuthorId.getReviewId(), fetchReviewCallback(mApp.getTagsManager()));
            }
        };
    }
}

