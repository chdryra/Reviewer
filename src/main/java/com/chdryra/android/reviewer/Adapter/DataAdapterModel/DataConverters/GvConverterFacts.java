package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterFacts extends GvConverterDataReview<DataFact, GvFactList.GvFact, GvFactList> {
    private GvConverterUrls mUrlConverter;

    public GvConverterFacts(GvConverterUrls urlConverter) {
        super(GvFactList.class);
        mUrlConverter = urlConverter;
    }

    @Override
    public GvFactList.GvFact convert(DataFact datum) {
        GvReviewId id = newId(datum.getReviewId());
        GvFactList.GvFact fact = null;
        if (datum.isUrl()) fact = getGvUrl(datum);

        if(fact == null) fact = new GvFactList.GvFact(id, datum.getLabel(), datum.getValue());

        return fact;
    }

    private GvUrlList.GvUrl getGvUrl(DataFact datum) {
        GvReviewId id = newId(datum.getReviewId());
        try {
            DataUrl urlDatum = (DataUrl) datum;
            return mUrlConverter.convert(urlDatum);
        } catch (ClassCastException e) {
            String urlGuess = URLUtil.guessUrl(datum.getValue());
            try {
                URL url = new URL(urlGuess);
                return new GvUrlList.GvUrl(id, datum.getLabel(), url);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }
}
