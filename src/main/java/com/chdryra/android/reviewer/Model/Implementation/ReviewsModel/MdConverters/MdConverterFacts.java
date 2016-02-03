/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters;

import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterFacts extends MdConverterDataReview<DataFact, MdFact> {
    private MdConverterUrl mUrlConverter;

    public MdConverterFacts(MdConverterUrl urlConverter) {
        mUrlConverter = urlConverter;
    }

    @Override
    public MdFact convert(DataFact datum, ReviewId reviewId) {
        MdReviewId id = newMdReviewId(reviewId);
        MdFact fact = null;
        if (datum.isUrl()) fact = getMdUrl(id, datum);

        if(fact == null) fact = new MdFact(id, datum.getLabel(), datum.getValue());

        return fact;
    }

    @Nullable
    private MdUrl getMdUrl(MdReviewId reviewId, DataFact datum) {
        try {
            DataUrl urlDatum = (DataUrl) datum;
            return mUrlConverter.convert(urlDatum);
        } catch (ClassCastException e) {
            String urlGuess = URLUtil.guessUrl(datum.getValue());
            try {
                URL url = new URL(urlGuess);
                return new MdUrl(reviewId, datum.getLabel(), url);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }
}
