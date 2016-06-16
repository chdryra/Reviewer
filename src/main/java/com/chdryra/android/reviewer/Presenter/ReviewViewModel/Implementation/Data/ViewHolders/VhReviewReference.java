/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferenceObservers;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReference;
import com.chdryra.android.reviewer.R;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewReference extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_review_overview;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int PUBLISH = R.id.review_publish_data;

    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    private TextView mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    private ReviewReference mReference;
    private SubjectObserver mSubjectObserver;
    private RatingObserver mRatingObserver;
    private AuthorObserver mAuthorObserver;
    private DateObserver mDateObserver;
    private CoverObserver mCoverObserver;
    private TagsObserver mTagsObserver;
    private CommentsObserver mCommentsObserver;
    private LocationsObserver mLocationsObserver;

    private ReviewStamp mStamp;
    private String mLocation = "";

    public VhReviewReference(TagsManager tagsManager,
                             GvConverterImages converterImages,
                             GvConverterComments converterComments,
                             GvConverterLocations converterLocations) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, PUBLISH});

        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;

        mSubjectObserver = new SubjectObserver();
        mRatingObserver = new RatingObserver();
        mAuthorObserver = new AuthorObserver();
        mDateObserver = new DateObserver();
        mCoverObserver = new CoverObserver();
        mCommentsObserver = new CommentsObserver();
        mTagsObserver = new TagsObserver();
        mLocationsObserver = new LocationsObserver();
    }

    @Override
    public void updateView(ViewHolderData data) {
        setViewsIfNecessary();
        setReference(((GvReference) data).getReference());
    }

    private void setViewsIfNecessary() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (RatingBar) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(PUBLISH);
    }

    private void setReference(ReviewReference reference) {
        if (mReference != null) unregister();
        mReference = reference;
        newStamp(mReference.getInfo().getAuthor(), mReference.getInfo().getPublishDate());
        register();
    }

    private void newStamp(DataAuthor author, DataDate publishDate) {
        mStamp = ReviewStamp.newStamp(author, publishDate);
        newFooter();
    }

    private void newFooter() {
        String text = mStamp.toReadable() + (validateString(mLocation) ? " @" + mLocation : "");
        mPublishDate.setText(text);
    }

    private void unregister() {
        mReference.unregisterObserver(mSubjectObserver);
        mReference.unregisterObserver(mRatingObserver);
        mReference.unregisterObserver(mAuthorObserver);
        mReference.unregisterObserver(mDateObserver);
        mReference.unregisterObserver(mCoverObserver);
        mReference.unregisterObserver(mCommentsObserver);
        mReference.unregisterObserver(mLocationsObserver);
        mReference.unregisterObserver(mTagsObserver);
    }

    private void register() {
        mReference.registerObserver(mSubjectObserver);
        mReference.registerObserver(mRatingObserver);
        mReference.registerObserver(mAuthorObserver);
        mReference.registerObserver(mDateObserver);
        mReference.registerObserver(mCoverObserver);
        mReference.registerObserver(mCommentsObserver);
        mReference.registerObserver(mLocationsObserver);
        mReference.registerObserver(mTagsObserver);
    }

    private void setLocationString(IdableList<? extends DataLocation> value) {
        GvLocationList locations = mConverterLocations.convert(value);
        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }
        String location = "";
        int locs = locationNames.size();
        if (locs > 0) {
            location = locationNames.get(0);
            if (locs > 1) {
                String loc = locs == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(locationNames.size() - 1) + loc;
            }
        }

        mLocation = location;
    }

    private String getTagString(ArrayList<String> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        while (i > -1 && TextUtils.isTooLargeForTextView(mTags, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        return tagsString;
    }

    private String getTagString(ArrayList<String> tags, int maxTags) {
        String tagsString = "";
        int size = Math.min(tags.size(), Math.max(maxTags, tags.size()));
        int diff = tags.size() - size;
        int i = 0;
        while (i < size) {
            tagsString += "#" + tags.get(i) + " ";
            ++i;
        }

        if (diff > 0) tagsString += "+ " + String.valueOf(diff) + "#";

        return tagsString.trim();
    }

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }

    private class SubjectObserver implements ReferenceObservers.SubjectObserver {
        @Override
        public void onValue(DataSubject value) {
            mSubject.setText(value.getSubject());
        }
    }

    private class RatingObserver implements ReferenceObservers.RatingObserver {
        @Override
        public void onValue(DataRating value) {
            mRating.setRating(value.getRating());
        }
    }

    private class CoverObserver implements ReferenceObservers.CoverObserver {
        @Override
        public void onValue(DataImage value) {
            GvImage gvCover = mConverterImages.convert(value);
            Bitmap cover = gvCover.getBitmap();
            mImage.setImageBitmap(cover);
        }
    }

    private class AuthorObserver implements ReferenceObservers.AuthorObserver {
        @Override
        public void onValue(DataAuthorReview value) {
            newStamp(value, mStamp.getDate());
        }
    }

    private class DateObserver implements ReferenceObservers.DateObserver {
        @Override
        public void onValue(DataDateReview value) {
            newStamp(mStamp.getAuthor(), value);
        }
    }

    private class TagsObserver implements ReferenceObservers.TagsObserver {
        @Override
        public void onValue(IdableList<? extends DataTag> value) {
            ItemTagCollection tags = mTagsManager.getTags(mReference.getInfo().getReviewId()
                    .toString());
            mTags.setText(getTagString(tags.toStringArray()));
        }
    }

    private class LocationsObserver implements ReferenceObservers.LocationsObserver {
        @Override
        public void onValue(IdableList<? extends DataLocation> value) {
            setLocationString(value);
            newFooter();
        }
    }

    private class CommentsObserver implements ReferenceObservers.CommentsObserver {
        @Override
        public void onValue(IdableList<? extends DataComment> value) {
            GvCommentList comments = mConverterComments.convert(value);
            GvCommentList headlines = comments.getHeadlines();
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() : null;
            if (validateString(headline)) {
                //TODO sort this out with resource strings with placeholders
                mHeadline.setText("\"" + headline + "\"");
            } else {
                mHeadline.setText("");
            }
        }
    }
}

