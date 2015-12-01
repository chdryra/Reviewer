package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;



import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.Interfaces.Review;

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
    public MdDataList<MdComment> toMdCommentList(Iterable<? extends DataComment> comments,
                                         String reviewId) {
        return mConverterComments.convert(comments, reviewId);
    }

    public MdDataList<MdComment> toMdCommentList(IdableList<? extends DataComment> comments) {
        return mConverterComments.convert(comments);
    }

    //Facts
    public MdDataList<MdFact> toMdFactList(Iterable<? extends DataFact> facts, String reviewId) {
        return mConverterFacts.convert(facts, reviewId);
    }

    //Images
    public MdDataList<MdImage> toMdImageList(Iterable<? extends DataImage> images, String reviewId) {
        return mConverterImages.convert(images, reviewId);
    }

    //Locations
    public MdDataList<MdLocation> toMdLocationList(Iterable<? extends DataLocation> locations,
                                           String reviewId) {

        return mConverterLocations.convert(locations, reviewId);
    }

    //Urls
    public MdDataList<MdUrl> toMdUrlList(Iterable<? extends DataUrl> urls, String reviewId) {
        return mConverterUrl.convert(urls, reviewId);
    }

    //Criteria
    public MdDataList<MdCriterion> toMdCriterionList(IdableList<? extends DataCriterionReview> criteria) {
        return mConverterCriteria.convert(criteria);
    }

    public MdDataList<MdCriterion> reviewsToMdCriterionList(Iterable<? extends Review> criteria, String reviewId) {
        return mConverterCriteria.convertReviews(criteria, reviewId);
    }
}
