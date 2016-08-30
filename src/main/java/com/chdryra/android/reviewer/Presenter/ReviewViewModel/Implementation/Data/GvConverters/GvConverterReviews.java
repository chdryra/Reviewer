/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviews
        extends GvConverterBasic<Review, GvReview, GvReviewList>
        implements DataConverter<Review, GvReview, GvReviewList> {
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    public GvConverterReviews(TagsManager tagsManager,
                              GvConverterImages converterImages,
                              GvConverterComments converterComments,
                              GvConverterLocations converterLocations) {
        super(GvReviewList.class);
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    @Override
    public GvReview convert(Review review, ReviewId parentId) {
        return new GvReview(review,
                mTagsManager, mConverterImages,
                mConverterComments, mConverterLocations);
    }

    @Override
    public GvReview convert(Review review) {
        return convert(review, null);
    }

    @Override
    public GvReviewList convert(IdableList<? extends Review> data) {
        GvReviewList list = new GvReviewList(newId(data.getReviewId()));
        for(Review datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
