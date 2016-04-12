/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewAsyncList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviewsAsync
        extends GvConverterBasic<Review, GvReviewAsync, GvReviewAsyncList>
        implements DataConverter<Review, GvReviewAsync, GvReviewAsyncList> {
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private GvConverterDateReviews mConverterDate;
    private GvConverterAuthors mGvConverterAuthor;
    private ReviewsRepository mRepo;
    private TagsManager mTagsManager;

    public GvConverterReviewsAsync(TagsManager tagsManager,
                                   ReviewsRepository repo,
                                   GvConverterImages converterImages,
                                   GvConverterComments converterComments,
                                   GvConverterLocations converterLocations,
                                   GvConverterDateReviews converterDate,
                                   GvConverterAuthors gvConverterAuthor) {
        super(GvReviewAsyncList.class);
        mTagsManager = tagsManager;
        mRepo = repo;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mConverterDate = converterDate;
        mGvConverterAuthor = gvConverterAuthor;
    }

    @Override
    public GvReviewAsync convert(Review review, ReviewId parentId) {
        return new GvReviewAsync(newId(review.getReviewId()), mRepo, mTagsManager, mConverterImages,
                mConverterComments, mConverterLocations, mConverterDate, mGvConverterAuthor);
    }

    @Override
    public GvReviewAsync convert(Review review) {
        return convert(review, null);
    }

    @Override
    public GvReviewAsyncList convert(IdableList<? extends Review> data) {
        GvReviewAsyncList list = new GvReviewAsyncList(newId(data.getReviewId()));
        for(Review datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
