/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 12/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncherImpl implements ReviewLauncher {
    private final ReviewsSource mReviewsSource;
    private final FactoryReviewView mViewFactory;
    private final NodeLauncher mFormatter;
    private final NodeLauncher mNodeMapper;
    private List<NodeDataLauncher<?>> mDataLaunchers;

    private UiLauncher mLauncher;
    private AuthorId mSessionAuthor;

    public ReviewLauncherImpl(ReviewsSource reviewsSource,
                              NodeLauncher formatter,
                              NodeLauncher nodeMapper,
                              List<NodeDataLauncher<?>> dataLaunchers,
                              FactoryReviewView viewFactory) {
        mReviewsSource = reviewsSource;
        mViewFactory = viewFactory;
        mFormatter = formatter;
        mNodeMapper = nodeMapper;
    }

    @Nullable
    public ReviewNode unpack(Bundle args) {
        ReviewNode formatted = mFormatter.unpack(args);
        if(formatted == null) return mNodeMapper.unpack(args);
        return formatted;
    }

    @Override
    public void setUiLauncher(UiLauncher launcher) {
        mLauncher = launcher;
        mFormatter.setUiLauncher(launcher);
        mNodeMapper.setUiLauncher(launcher);
    }

    @Override
    public void launchAsList(ReviewId reviewId) {
        launchAsList(mReviewsSource.asMetaReview(reviewId));
    }

    @Override
    public void launchAsList(ReviewNode node) {
        launchView(newListView(node), getRequestCode(node));
    }

    @Override
    public void launchSummary(final ReviewId reviewId) {
        ReviewNode node = mReviewsSource.asReviewNode(reviewId);
        launchView(mViewFactory.newSummaryView(node), getRequestCode(node));
    }

    @Override
    public void launchReviewsList(AuthorId authorId) {
        ReviewNode node = mReviewsSource.getMetaReview(authorId);
        launchView(newListView(node), getRequestCode(node));
    }

    @Override
    public void launchFormatted(ReviewNode node, boolean isPublished) {
        mFormatter.launch(node, isPublished);
    }

    @Override
    public void launchMap(ReviewNode node, boolean isPublished) {
        mNodeMapper.launch(node, isPublished);
    }

    @Override
    public void launchDataView(ReviewNode node, GvDataType<?> dataType, int datumIndex) {
        NodeDataLauncher<?> launcher = null;
        for(NodeDataLauncher<?> dataLauncher : mDataLaunchers) {
            if(dataLauncher.isOfType(dataType)) {
                launcher = dataLauncher;
                break;
            }
        }

        if(launcher != null) launcher.launch(node, datumIndex);
    }

    void setSessionAuthor(AuthorId authorId) {
        mSessionAuthor = authorId;
    }

    private int getRequestCode(ReviewNode node) {
        return RequestCodeGenerator.getCode(node.getSubject().getSubject());
    }

    private void launchView(ReviewView<?> ui, int requestCode) {
        mLauncher.launch(ui, new UiLauncherArgs(requestCode));
    }

    private ReviewView<?> newListView(ReviewNode reviewNode) {
        boolean menu = !reviewNode.getAuthorId().toString().equals(mSessionAuthor.toString());
        return mViewFactory.newReviewsListView(reviewNode, menu);
    }
}
