/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvFactList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterFacts extends GvConverterReviewData.RefDataList<DataFact, GvFact,
        GvFactList, GvFact.Reference> {
    private final GvConverterUrls mUrlConverter;

    public GvConverterFacts(GvConverterUrls urlConverter) {
        super(GvFactList.class, GvFact.Reference.TYPE);
        mUrlConverter = urlConverter;
    }

    @Override
    public GvFact convert(DataFact datum, @Nullable ReviewId reviewId) {
        GvReviewId id = newId(reviewId);
        GvFact fact = null;
        if (datum.isUrl()) fact = getGvUrl(datum, id);

        if (fact == null) fact = new GvFact(id, datum.getLabel(), datum.getValue());

        return fact;
    }

    @Override
    protected GvFact.Reference convertReference(ReviewItemReference<DataFact> reference) {
        return new GvFact.Reference(reference, this);
    }

    @Nullable
    private GvUrl getGvUrl(DataFact datum, @Nullable GvReviewId id) {
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
