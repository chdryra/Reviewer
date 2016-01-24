/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.webkit.URLUtil;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterFacts extends GvConverterDataReview<DataFact, GvFact, GvFactList> {
    private GvConverterUrls mUrlConverter;

    public GvConverterFacts(GvConverterUrls urlConverter) {
        super(GvFactList.class);
        mUrlConverter = urlConverter;
    }

    @Override
    public GvFact convert(DataFact datum) {
        GvReviewId id = newId(datum.getReviewId());
        GvFact fact = null;
        if (datum.isUrl()) fact = getGvUrl(datum);

        if(fact == null) fact = new GvFact(id, datum.getLabel(), datum.getValue());

        return fact;
    }

    private GvUrl getGvUrl(DataFact datum) {
        GvReviewId id = newId(datum.getReviewId());
        try {
            DataUrl urlDatum = (DataUrl) datum;
            return mUrlConverter.convert(urlDatum);
        } catch (ClassCastException e) {
            String urlGuess = URLUtil.guessUrl(datum.getValue());
            try {
                URL url = new URL(urlGuess);
                return new GvUrl(id, datum.getLabel(), url);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }
}
