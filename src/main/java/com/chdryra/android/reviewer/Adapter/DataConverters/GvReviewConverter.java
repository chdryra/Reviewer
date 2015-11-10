package com.chdryra.android.reviewer.Adapter.DataConverters;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
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
public class GvReviewConverter extends GvConverterBasic<Review,
        GvReviewOverviewList.GvReviewOverview, GvReviewOverviewList>{
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    @Override
    public GvReviewOverviewList.GvReviewOverview convert(Review review) {
        String reviewId = review.getReviewId();
        //GvReviewId id = new GvReviewId(holder.toString());
        GvImageList images = mConverterImages.convert(review.getImages(), reviewId);
        GvCommentList headlines = mConverterComments.convert(review.getComments(), reviewId).getHeadlines();
        GvLocationList locations = mConverterLocations.convert(review.getLocations(), reviewId);

        Bitmap cover = images.size() > 0 ? images.getRandomCover().getBitmap() : null;
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() :
                null;

        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocationList.GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }

        ArrayList<String> tags = tagsManager.getTags(review.getMdReviewId()).toStringArray();

        return new GvReviewOverviewList.GvReviewOverview(id, review.getReviewId(),
                review.getAuthor(), review.getPublishDate().getDate(), review.getSubject().getSubject(),
                review.getRating().getRating(), cover, headline, locationNames, tags);
    }
}
