/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewModifier;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenModifier implements ReviewViewModifier {
    private static final int LAUNCH_SHARE_SCREEN = RequestCodeGenerator.getCode("ShareScreen");
    private static final int BUTTON_DIVIDER = R.layout.horizontal_divider;
    private static final int BUTTON_LAYOUT = R.layout.review_banner_button;
    private static final int SHARE_BUTTON = R.string.button_share;
    private static final int TOAST_ENTER_SUBJECT = R.string.toast_enter_subject;

    private LaunchableUiLauncher mLauncher;
    private LaunchableUi mShareScreenUi;

    public BuildScreenModifier(LaunchableUiLauncher launcher,
                               LaunchableUi shareScreenUi) {
        mLauncher = launcher;
        mShareScreenUi = shareScreenUi;
    }

    //Overridden
    @Override
    public View modify(ReviewView view, View v, LayoutInflater inflater,
                       ViewGroup container, Bundle savedInstanceState) {
        ReviewViewContainer parent = view.getContainer();
        parent.setBannerAsDisplay();
        parent.addView(getShareButton(parent, inflater, container));
        parent.addView(getButtonDivider(inflater, container));

        return v;
    }

    private View getButtonDivider(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(BUTTON_DIVIDER, container, false);
    }

    @NonNull
    private Button getShareButton(final ReviewViewContainer parent, LayoutInflater inflater,
                                  ViewGroup container) {
        Button shareButton = (Button) inflater.inflate(BUTTON_LAYOUT, container, false);
        shareButton.setText(parent.getActivity().getResources().getString(SHARE_BUTTON));
        shareButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
        shareButton.setOnClickListener(newLaunchShareScreenListener(parent));
        return shareButton;
    }

    @NonNull
    private View.OnClickListener newLaunchShareScreenListener(final ReviewViewContainer parent) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchShareScreen(parent);
            }
        };
    }

    private void launchShareScreen(ReviewViewContainer parent) {
        Activity activity = parent.getActivity();

        if (parent.getSubject().length() == 0) {
            Toast.makeText(activity, TOAST_ENTER_SUBJECT, Toast.LENGTH_SHORT).show();
            return;
        }

        mLauncher.launch(mShareScreenUi, activity, LAUNCH_SHARE_SCREEN, new Bundle());
    }
}
