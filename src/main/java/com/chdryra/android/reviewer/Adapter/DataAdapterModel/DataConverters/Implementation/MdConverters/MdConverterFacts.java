package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
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
    public MdFact convert(DataFact datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        MdFact fact = null;
        if (datum.isUrl()) fact = getMdUrl(datum);

        if(fact == null) fact = new MdFact(id, datum.getLabel(), datum.getValue());

        return fact;
    }

    private MdUrl getMdUrl(DataFact datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        try {
            DataUrl urlDatum = (DataUrl) datum;
            return mUrlConverter.convert(urlDatum);
        } catch (ClassCastException e) {
            String urlGuess = URLUtil.guessUrl(datum.getValue());
            try {
                URL url = new URL(urlGuess);
                return new MdUrl(id, datum.getLabel(), url);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }
}
