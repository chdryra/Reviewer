/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs
        .Implementation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.DialogOneButtonFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.Social.Implementation.PublisherAndroid;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogShareEditReview extends DialogOneButtonFragment implements
        LaunchableUiAlertable {
    private static final String SHARE_EDIT_REVIEW = "ShareEditReview";
    private static final int LAYOUT = R.layout.dialog_share_edit_review;
    private static final int SHARE = R.id.button_share_review;
    private static final int ANOTHER = R.id.button_another_review;
    private static final int DELETE = R.id.button_delete_review;
    private static final int DIALOG_ALERT = RequestCodeGenerator.getCode("DeleteReview");
    private static final int ALERT_DELETE_REVIEW = R.string.alert_delete_review;

    private DeleteRequestListener mDeleteRequestListener;
    private NewReviewListener mNewReviewListener;
    private ReviewId mReviewId;
    private PublisherAndroid mSharer;

    @Override
    protected Intent getReturnData() {
        return null;
    }

    @Override
    public String getLaunchTag() {
        return SHARE_EDIT_REVIEW;
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
        return layout;
    }

    private View.OnClickListener requestNewReviewUsingTemplate() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewReviewListener.onNewReviewUsingTemplate(mReviewId);
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftButtonAction(ActionType.DONE);
        setDialogTitle(null);
        hideKeyboardOnLaunch();
        mDeleteRequestListener = getTargetListener(DeleteRequestListener.class);
        mNewReviewListener = getTargetListener(NewReviewListener.class);
        setReviewIdFromArgs();

        mSharer = new PublisherAndroid(getActivity(), new ReviewSummariser(), new ReviewFormatterTwitter());
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == DIALOG_ALERT) {
            mDeleteRequestListener.onDeleteRequested(mReviewId);
            dismiss();
        }
    }

    private void shareIntent() {
        ApplicationInstance instance = ApplicationInstance.getInstance(getActivity());
        mSharer.publish(instance.getReviewFromLocalRepo(mReviewId), instance.getTagsManager());
    }

    private void setReviewIdFromArgs() {
        Bundle args = getArguments();
        if (args != null) {
            mReviewId = args.getParcelable(getLaunchTag());
        } else {
            throw new IllegalArgumentException("Must pass review id in args!");
        }
    }

    private View.OnClickListener launchDeleteAlertOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alert = getActivity().getResources().getString(ALERT_DELETE_REVIEW);
                DialogShower.showAlert(alert, getActivity(), DIALOG_ALERT, new Bundle());
            }
        };
    }

    private View.OnClickListener launchShareIntentOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent();
            }
        };
    }
}

