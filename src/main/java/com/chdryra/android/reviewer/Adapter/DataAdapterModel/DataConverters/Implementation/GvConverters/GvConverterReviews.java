package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces
        .GvReviewConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvReviewOverview;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvReviewOverviewList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviews extends GvConverterBasic<Review,
        GvReviewOverview, GvReviewOverviewList> implements GvReviewConverter<GvReviewOverview, GvReviewOverviewList> {
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

    public GvReviewOverview convert(Review review, String parentId) {
        String reviewId = review.getReviewId();
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

        ArrayList<String> tags = mTagsManager.getTagsArray(review.getReviewId());

        return new GvReviewOverview(id, review.getReviewId(),
                author, publishDate, review.getSubject().getSubject(),
                review.getRating().getRating(), cover, headline, locationNames, tags);
    }

    @Override
    public GvReviewOverview convert(Review review) {
        return convert(review, null);
    }

    public GvReviewOverviewList convert(IdableList<? extends Review> data) {
        GvReviewOverviewList list = new GvReviewOverviewList(newId(data.getReviewId()));
        for(Review datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}