package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdUrlList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterFacts extends MdConverterDataReview<DataFact, MdFactList.MdFact, MdFactList> {
    private MdConverterUrl mUrlConverter;

    public MdConverterFacts(MdConverterUrl urlConverter) {
        super(MdFactList.class);
        mUrlConverter = urlConverter;
    }

    @Override
    public MdFactList.MdFact convert(DataFact datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        MdFactList.MdFact fact = null;
        if (datum.isUrl()) fact = getMdUrl(datum);

        if(fact == null) fact = new MdFactList.MdFact(id, datum.getLabel(), datum.getValue());

        return fact;
    }

    private MdUrlList.MdUrl getMdUrl(DataFact datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        try {
            DataUrl urlDatum = (DataUrl) datum;
            return mUrlConverter.convert(urlDatum);
        } catch (ClassCastException e) {
            String urlGuess = URLUtil.guessUrl(datum.getValue());
            try {
                URL url = new URL(urlGuess);
                return new MdUrlList.MdUrl(id, datum.getLabel(), url);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }
}
