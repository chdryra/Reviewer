/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Dialogs.AlertListener;
import com.chdryra.android.corelibrary.LocationUtils.LocationClient;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class EditorSuiteAndroid implements EditorSuite, AlertListener {
    private static final int ALERT = RequestCodeGenerator.getCode(EditorSuiteAndroid.class);

    private final FactoryReviewView mViewFactory;
    private final FactoryImageChooser mFactoryImageChooser;
    private ReviewEditor<?> mReviewEditor;
    private DiscardListener mDiscardListener;

    public EditorSuiteAndroid(FactoryReviewView viewFactory,
                              FactoryImageChooser factoryImageChooser) {
        mViewFactory = viewFactory;
        mFactoryImageChooser = factoryImageChooser;
    }

    @Override
    public void createReviewCreator(ReviewEditor.EditMode editMode, LocationClient client, @Nullable Review template) {
        mReviewEditor = mViewFactory.newReviewCreator(editMode, client, template);
    }

    @Override
    public void createReviewEditor(LocationClient client, Review toEdit, ReviewPublisher
            publisher, PublishAction.PublishCallback callback) {
        mReviewEditor = mViewFactory.newReviewEditor(toEdit, client, publisher, callback);
    }

    @Override
    public ReviewEditor<?> getEditor() {
        return mReviewEditor;
    }

    @Override
    public void discardEditor(boolean showAlert, @Nullable final DiscardListener listener) {
        mDiscardListener = listener;
        if(showAlert) {
            mReviewEditor.getCurrentScreen().showAlert(Strings.Alerts.DISCARD_REVIEW, ALERT, this, new Bundle());
        } else {
            discard();
        }
    }

    @Override
    public ImageChooser newImageChooser(String fileName) {
        return mFactoryImageChooser.newImageChooser(fileName);
    }

    private void discard() {
        if(mDiscardListener != null) mDiscardListener.onDiscarded(true);
        mReviewEditor = null;
        mDiscardListener = null;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        if(mDiscardListener != null) {
            mDiscardListener.onDiscarded(false);
            mDiscardListener = null;
        }
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        discard();
    }
}

