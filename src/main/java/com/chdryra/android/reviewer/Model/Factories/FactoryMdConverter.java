/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .ConverterMd;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .MdConverterComments;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .MdConverterCriteria;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .MdConverterFacts;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .MdConverterImages;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .MdConverterLocations;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters
        .MdConverterUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryMdConverter {
    public ConverterMd newMdConverter() {
        MdConverterComments converterComments = new MdConverterComments();
        MdConverterUrl converterUrl = new MdConverterUrl();
        MdConverterFacts converterFacts = new MdConverterFacts(converterUrl);
        MdConverterImages converterImages = new MdConverterImages();
        MdConverterLocations converterLocations = new MdConverterLocations();
        MdConverterCriteria converterCriteria = new MdConverterCriteria();

        return new ConverterMd(converterComments, converterFacts, converterImages,
                converterLocations, converterUrl, converterCriteria);
    }
}
