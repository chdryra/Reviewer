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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
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
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewReference extends ViewHolderBasic {
    private static final Random RAND = new Random();

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
    private CoverBinder mCoverObserver;
    private TagsBinder mTagsObserver;
    private CommentsBinder mCommentsObserver;
    private LocationsBinder mLocationsObserver;

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

        mCoverObserver = new CoverBinder();
        mCommentsObserver = new CommentsBinder();
        mTagsObserver = new TagsBinder();
        mLocationsObserver = new LocationsBinder();
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
        if (mReference != null) unbindFromReference();
        mReference = reference;
        mSubject.setText(mReference.getSubject().getSubject());
        mRating.setRating(mReference.getRating().getRating());
        newStamp(mReference.getAuthor(), mReference.getPublishDate());
        bindToReference();
    }

    private void newStamp(DataAuthor author, DataDate publishDate) {
        mStamp = ReviewStamp.newStamp(author, publishDate);
        newFooter();
    }

    private void newFooter() {
        String text = mStamp.toReadable() + (validateString(mLocation) ? " @" + mLocation : "");
        mPublishDate.setText(text);
    }

    private void unbindFromReference() {
        mReference.unbind(mCoverObserver);
        mReference.unbind(mCommentsObserver);
        mReference.unbind(mLocationsObserver);
        mReference.unbind(mTagsObserver);
    }

    private void bindToReference() {
        mReference.bind(mCoverObserver);
        mReference.bind(mCommentsObserver);
        mReference.bind(mLocationsObserver);
        mReference.bind(mTagsObserver);
    }

    private void setLocationString(IdableList<DataLocation> value) {
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

    private class CoverBinder implements ReferenceBinders.CoversBinder {
        @Override
        public void onValue(IdableList<DataImage> value) {
            int index = RAND.nextInt(value.size());
            GvImage gvCover = mConverterImages.convert(value.getItem(index));
            Bitmap cover = gvCover.getBitmap();
            mImage.setImageBitmap(cover);
        }
    }

    private class TagsBinder implements ReferenceBinders.TagsBinder {
        @Override
        public void onValue(IdableList<DataTag> value) {
            ItemTagCollection tags = mTagsManager.getTags(mReference.getReviewId()
                    .toString());
            mTags.setText(getTagString(tags.toStringArray()));
        }
    }

    private class LocationsBinder implements ReferenceBinders.LocationsBinder {
        @Override
        public void onValue(IdableList<DataLocation> value) {
            setLocationString(value);
            newFooter();
        }
    }

    private class CommentsBinder implements ReferenceBinders.CommentsBinder {
        @Override
        public void onValue(IdableList<DataComment> value) {
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

