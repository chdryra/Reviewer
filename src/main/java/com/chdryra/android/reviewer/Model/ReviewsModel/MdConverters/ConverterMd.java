/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdUrl;

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

    public MdDataList<MdComment> toMdCommentList(Iterable<? extends DataComment> comments,
                                         ReviewId reviewId) {
        return mConverterComments.convert(comments, reviewId);
    }

    public MdDataList<MdFact> toMdFactList(Iterable<? extends DataFact> facts, ReviewId reviewId) {
        return mConverterFacts.convert(facts, reviewId);
    }

    public MdDataList<MdImage> toMdImageList(Iterable<? extends DataImage> images, ReviewId reviewId) {
        return mConverterImages.convert(images, reviewId);
    }

    public MdDataList<MdLocation> toMdLocationList(Iterable<? extends DataLocation> locations,
                                           ReviewId reviewId) {
        return mConverterLocations.convert(locations, reviewId);
    }

    public MdDataList<MdUrl> toMdUrlList(Iterable<? extends DataUrl> urls, ReviewId reviewId) {
        return mConverterUrl.convert(urls, reviewId);
    }

    public MdDataList<MdCriterion> toMdCriterionList(Iterable<? extends DataCriterion> criteria,
                                                     ReviewId reviewId) {
        return mConverterCriteria.convert(criteria, reviewId);
    }
}
