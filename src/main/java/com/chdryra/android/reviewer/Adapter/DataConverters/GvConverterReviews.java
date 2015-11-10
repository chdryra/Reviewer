package com.chdryra.android.reviewer.Adapter.DataConverters;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviews extends GvConverterBasic<Review,
        GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList>{
    private TagsManager mTagsManager;
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private GvConverterDates mConverterDate;
    private GvConverterAuthors mGvConverterAuthor;

    public GvConverterReviews(Class<GvReviewOverviewList> listClass, TagsManager tagsManager,
                              GvConverterImages converterImages, GvConverterComments
                                      converterComments, GvConverterLocations converterLocations,
                              GvConverterDates converterDate, GvConverterAuthors gvConverterAuthor) {
        super(listClass);
        mTagsManager = tagsManager;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mConverterDate = converterDate;
        mGvConverterAuthor = gvConverterAuthor;
    }

    public GvReviewOverviewList.GvReviewOverview convert(Review review, String parentId) {
        String reviewId = review.getReviewId();
        GvReviewId id = new GvReviewId(parentId);
        GvImageList images = mConverterImages.convert(review.getImages(), reviewId);
        GvCommentList headlines = mConverterComments.convert(review.getComments(), reviewId).getHeadlines();
        GvLocationList locations = mConverterLocations.convert(review.getLocations(), reviewId);
        GvAuthorList.GvAuthor author = mGvConverterAuthor.convert(review.getAuthor());
        GvDateList.GvDate publishDate = mConverterDate.convert(review.getPublishDate());

        Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                null;

        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocationList.GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }

        ArrayList<String> tags = mTagsManager.getTagsArray(review.getReviewId());

        return new GvReviewOverviewList.GvReviewOverview(id, review.getReviewId(),
                author, publishDate, review.getSubject().getSubject(),
                review.getRating().getRating(), cover, headline, locationNames, tags);
    }

    @Override
    public GvReviewOverviewList.GvReviewOverview convert(Review review) {
        return convert(review, null);
    }

    public GvReviewOverviewList convert(IdableList<? extends Review> data, String reviewId) {
        GvReviewId id = new GvReviewId(reviewId);
        GvReviewOverviewList list = new GvReviewOverviewList(id);
        for(Review datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
