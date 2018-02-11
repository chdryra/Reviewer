/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ReviewSelector;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhMapInfoWindow extends MapInfoWindow implements ReviewSelector
        .ReviewSelectorCallback {
    private static final int LAYOUT = R.layout.map_info_window;
    private static final int ABSTRACT = R.id.review_abstract;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int LOCATION = R.id.cluster_num_locations;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int STAMP = R.id.review_date_location;
    private static final int IMAGE = R.id.review_image;
    private static final int CALLBACKS = 3;

    private final ReviewNode mNode;
    private final DataLocation mLocationName;
    private final InfoWindowLauncher mLauncher;
    private final AuthorsRepo mAuthorsRepo;
    private final ReviewSelector mSelector;
    private final InfoUpdateListener mListener;

    private final TagsBinder mTagsBinder;
    private final CommentsBinder mCommentsBinder;
    private final NameBinder mNameBinder;

    private ReviewReference mReview;
    private AuthorName mAuthor;
    private int mCallbacks;

    private boolean mShowAbstract = false;
    private boolean mBound = false;

    public VhMapInfoWindow(DataLocation locationName,
                           ReviewNode node,
                           ReviewSelector selector,
                           InfoWindowLauncher launcher,
                           AuthorsRepo authorsRepo,
                           InfoUpdateListener listener) {
        super(LAYOUT, new int[]{LAYOUT, ABSTRACT, IMAGE, SUBJECT, RATING, LOCATION, HEADLINE,
                TAGS, STAMP});
        mLocationName = locationName;
        mNode = node;
        mLauncher = launcher;
        mAuthorsRepo = authorsRepo;
        mSelector = selector;
        mListener = listener;
        mCommentsBinder = new CommentsBinder();
        mTagsBinder = new TagsBinder();
        mNameBinder = new NameBinder();
    }

    protected ReviewReference getReview() {
        return mReview;
    }

    @Override
    public void unbindFromReview() {
        if (mReview == null) return;
        mReview.getComments().unbindFromValue(mCommentsBinder);
        mReview.getTags().unbindFromValue(mTagsBinder);
        mAuthorsRepo.getReference(mReview.getAuthorId()).unbindFromValue(mNameBinder);
        mSelector.unregister(mNode.getReviewId());
    }

    @Override
    public void updateView() {
        setViewsIfNecessary();
        initialiseData();
        mSelector.select(mNode, this);
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        if (review == null) return;
        mCallbacks = 0;
        mReview = review;
        setText(SUBJECT, mReview.getSubject().toString());
        setText(RATING, mReview.getRating().toString());
        notifyListener(true);
        bindIfNecessary();
    }

    @Override
    public void onClick() {
        if(mShowAbstract && mReview != null) {
            mLauncher.launchReview(mReview.getReviewId());
        } else {
            mShowAbstract = !mShowAbstract;
            setInfoVisibility();
            bindIfNecessary();
        }
    }

    @Override
    void resetWindow() {
        mShowAbstract = false;
        setInfoVisibility();
    }

    private void setGone(View view) {
        view.setVisibility(View.GONE);
    }

    private void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void bindIfNecessary() {
        if (!mBound && mShowAbstract) {
            mCallbacks = 0;
            mReview.getComments().bindToValue(mCommentsBinder);
            mReview.getTags().bindToValue(mTagsBinder);
            mAuthorsRepo.getReference(mReview.getAuthorId()).bindToValue(mNameBinder);
        }
    }

    private void setViewsIfNecessary() {
        setGone(getView(HEADLINE, TextView.class));
        setGone(getView(TAGS, TextView.class));
    }

    private void initialiseData() {
        mShowAbstract = false;
        setInfoVisibility();
        setText(SUBJECT, Strings.Progress.FETCHING);
        setText(RATING, "");
        setText(LOCATION, mLocationName.toString());
        setText(HEADLINE, null);
        setText(TAGS, null);
        setText(STAMP, null);
        mAuthor = null;
    }

    private void setInfoVisibility() {
        getView(ABSTRACT, LinearLayout.class).setVisibility(mShowAbstract ? View.VISIBLE : View.GONE);
        notifyListener(true);
    }

    private void newFooter() {
        if (mReview != null) {
            ReviewStamp stamp = ReviewStamp.newStamp(mReview.getAuthorId(), mReview
                    .getPublishDate());
            String date = stamp.toReadableDate();
            String name = mAuthor != null ? mAuthor.getName() : "";
            String text = name + " " + date;
            setText(STAMP, text);
            notifyListener(false);
        }
    }

    private void notifyListener(boolean force) {
        if (force) {
            mListener.onInfoUpdated();
        } else if (++mCallbacks == CALLBACKS) {
            mBound = true;
            mListener.onInfoUpdated();
        }
    }

    private String getTagString(IdableList<? extends DataTag> tags, int maxTags) {
        String ignoreTag = TextUtils.toTag(getView(SUBJECT, TextView.class).getText().toString());
        return DataFormatter.formatTags(tags, maxTags, ignoreTag);
    }

    private void setAuthor(@Nullable AuthorName author) {
        mAuthor = author;
        newFooter();
    }

    private void setHeadline(IdableList<DataComment> value) {
        if (value.size() > 0) {
            TextView headline = getView(HEADLINE, TextView.class);
            setVisible(headline);
            headline.setText(DataFormatter.getHeadlineQuote(value));
        }

        notifyListener(false);
    }

    private void setTags(IdableList<? extends DataTag> tags) {
        int i = tags.size();
        if (i > 0) {
            TextView tagsView = getView(TAGS, TextView.class);
            setVisible(tagsView);
            String tagsString = getTagString(tags, i--);
            while (i > -1 && TextUtils.isTooLargeForTextView(tagsView, tagsString)) {
                tagsString = getTagString(tags, i--);
            }
            tagsView.setText(tagsString);
        }

        notifyListener(false);
    }

    private class NameBinder implements ReferenceBinder<AuthorName> {
        @Override
        public void onInvalidated(DataReference<AuthorName> reference) {
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(AuthorName value) {
            setAuthor(value);
        }
    }

    private class TagsBinder extends ListBinder<DataTag> {
        @Override
        protected void onList(IdableList<DataTag> data) {
            setTags(data);
        }
    }

    private class CommentsBinder extends ListBinder<DataComment> {
        @Override
        protected void onList(IdableList<DataComment> data) {
            setHeadline(data);
        }
    }

    private abstract class ListBinder<T extends HasReviewId> implements
            ReferenceBinder<IdableList<T>> {
        protected abstract void onList(IdableList<T> data);

        @Override
        public void onReferenceValue(IdableList<T> value) {
            onList(value);
        }

        @Override
        public void onInvalidated(DataReference<IdableList<T>> reference) {
            onList(new IdableDataList<T>(mNode.getReviewId()));
        }
    }
}