package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverviewList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviews extends GvConverterBasic<Review,
        GvReviewOverview, GvReviewOverviewList> implements DataConverter<Review, GvReviewOverview, GvReviewOverviewList> {
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private GvConverterDateReviews mConverterDate;
    private GvConverterAuthors mGvConverterAuthor;

    public GvConverterReviews(TagsManager tagsManager,
                              GvConverterImages converterImages, GvConverterComments
                                      converterComments, GvConverterLocations converterLocations,
                              GvConverterDateReviews converterDate, GvConverterAuthors gvConverterAuthor) {
        super(GvReviewOverviewList.class);
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mConverterDate = converterDate;
        mGvConverterAuthor = gvConverterAuthor;
    }

    @Override
    public GvReviewOverview convert(Review review, ReviewId parentId) {
        ReviewId reviewId = review.getReviewId();
        GvReviewId id = newId(parentId);
        GvImageList images = mConverterImages.convert(review.getImages(), reviewId);
        GvCommentList headlines = mConverterComments.convert(review.getComments(), reviewId).getHeadlines();
        GvLocationList locations = mConverterLocations.convert(review.getLocations(), reviewId);
        GvAuthor author = mGvConverterAuthor.convert(review.getAuthor());
        GvDate publishDate = mConverterDate.convert(review.getPublishDate());

        Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                null;

        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }

        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());

        return new GvReviewOverview(id, newId(reviewId),
                author, publishDate, review.getSubject().getSubject(),
                review.getRating().getRating(), cover, headline, locationNames,
                tags.toStringArray());
    }

    @Override
    public GvReviewOverview convert(Review review) {
        return convert(review, null);
    }

    @Override
    public GvReviewOverviewList convert(IdableList<? extends Review> data) {
        GvReviewOverviewList list = new GvReviewOverviewList(newId(data.getReviewId()));
        for(Review datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
