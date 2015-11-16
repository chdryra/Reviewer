package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterMd {
    private MdConverterComments mConverterComments;
    private MdConverterFacts mConverterFacts;
    private MdConverterImages mConverterImages;
    private MdConverterLocations mConverterLocations;
    private MdConverterUrl mConverterUrl;
    private MdConverterCriteria mConverterCriteria;

    public ConverterMd(MdConverterComments converterComments, MdConverterFacts converterFacts,
                       MdConverterImages converterImages, MdConverterLocations
                               converterLocations, MdConverterUrl converterUrl,
                       MdConverterCriteria converterCriteria) {
        mConverterComments = converterComments;
        mConverterFacts = converterFacts;
        mConverterImages = converterImages;
        mConverterLocations = converterLocations;
        mConverterUrl = converterUrl;
        mConverterCriteria = converterCriteria;
    }

    //Comments
    public MdCommentList toMdCommentList(Iterable<? extends DataComment> comments,
                                         String reviewId) {
        return mConverterComments.convert(comments, reviewId);
    }

    //Facts
    public MdFactList toMdFactList(Iterable<? extends DataFact> facts, String reviewId) {
        return mConverterFacts.convert(facts, reviewId);
    }

    //Images
    public MdImageList toMdImageList(Iterable<? extends DataImage> images, String reviewId) {
        return mConverterImages.convert(images, reviewId);
    }

    //Locations
    public MdLocationList toMdLocationList(Iterable<? extends DataLocation> locations,
                                           String reviewId) {

        return mConverterLocations.convert(locations, reviewId);
    }

    //Urls
    public MdUrlList toMdUrlList(Iterable<? extends DataUrl> urls, String reviewId) {
        return mConverterUrl.convert(urls, reviewId);
    }

    //Criteria
    public MdCriterionList toMdCriterionList(Iterable<? extends DataCriterion> criteria, String reviewId) {
        return mConverterCriteria.convert(criteria, reviewId);
    }

    public MdCriterionList reviewsToMdCriterionList(Iterable<? extends Review> criteria, String reviewId) {
        return mConverterCriteria.convertReviews(criteria, reviewId);
    }
}
