/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils
        .DataFormatter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ReviewSelector;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhMapInfoWindow extends MapInfoWindow implements ReviewSelector
        .ReviewSelectorCallback {
    private static final int LAYOUT = R.layout.review_map_info_window;
    private static final int ABSTRACT = R.id.review_abstract;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int LOCATION = R.id.review_location_name;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int STAMP = R.id.review_stamp;
    private static final int IMAGE = R.id.review_image;
    private static final int CALLBACKS = 3;

    private final ReviewNode mNode;
    private final DataLocation mLocationName;
    private final AuthorsRepository mAuthorsRepo;
    private final ReviewSelector mSelector;
    private final InfoUpdateListener mListener;

    private final TagsBinder mTagsBinder;
    private final CommentsBinder mCommentsBinder;
    private final NameBinder mNameBinder;

    private LinearLayout mAbstract;
    private TextView mSubject;
    private TextView mRating;
    private TextView mLocation;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    private ReviewReference mReview;
    private NamedAuthor mAuthor;
    private int mCallbacks;

    private boolean mShowAbstract = false;
    private boolean mBound = false;

    public VhMapInfoWindow(DataLocation locationName,
                           ReviewNode node,
                           ReviewSelector selector,
                           AuthorsRepository authorsRepo,
                           InfoUpdateListener listener) {
        super(LAYOUT, new int[]{LAYOUT, ABSTRACT, IMAGE, SUBJECT, RATING, LOCATION, HEADLINE,
                TAGS, STAMP});
        mLocationName = locationName;
        mNode = node;
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
        mSubject.setText(mReview.getSubject().toString());
        mRating.setText(mReview.getRating().toString());
        notifyListener(true);
        bindIfNecessary();
    }

    @Override
    public void onClick() {
        mShowAbstract = !mShowAbstract;
        setAbstractVisibility();
        bindIfNecessary();
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
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (TextView) getView(RATING);
        if (mAbstract == null) mAbstract = (LinearLayout) getView(ABSTRACT);
        if (mLocation == null) mLocation = (TextView) getView(LOCATION);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(STAMP);
    }

    private void initialiseData() {
        mShowAbstract = false;
        setAbstractVisibility();
        mSubject.setText(Strings.FETCHING);
        mRating.setText("");
        mLocation.setText(mLocationName.toString());
        mHeadline.setText(null);
        mTags.setText(null);
        mPublishDate.setText(null);
        mAuthor = null;
    }

    private void setAbstractVisibility() {
        mAbstract.setVisibility(mShowAbstract ? View.VISIBLE : View.GONE);
        notifyListener(true);
    }

    private void newFooter() {
        if (mReview != null) {
            ReviewStamp stamp = ReviewStamp.newStamp(mReview.getAuthorId(), mReview
                    .getPublishDate());
            String date = stamp.toReadableDate();
            String name = mAuthor != null ? mAuthor.getName() : "";
            String text = name + " " + date;
            mPublishDate.setText(text);
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
        String ignoreTag = TextUtils.toTag(mSubject.getText().toString());
        return DataFormatter.formatTags(tags, maxTags, ignoreTag);
    }

    private void setAuthor(@Nullable NamedAuthor author) {
        mAuthor = author;
        newFooter();
    }

    private void setHeadline(IdableList<DataComment> value) {
        mHeadline.setText(DataFormatter.getHeadlineQuote(value));
        notifyListener(false);
    }

    private void setTags(IdableList<? extends DataTag> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        while (i > -1 && TextUtils.isTooLargeForTextView(mTags, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        mTags.setText(tagsString);
        notifyListener(false);
    }

    private class NameBinder implements ReferenceBinder<NamedAuthor> {
        @Override
        public void onInvalidated(DataReference<NamedAuthor> reference) {
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(NamedAuthor value) {
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