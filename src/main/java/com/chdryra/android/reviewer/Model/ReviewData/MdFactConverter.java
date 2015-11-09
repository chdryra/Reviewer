package com.chdryra.android.reviewer.Model.ReviewData;

import android.webkit.URLUtil;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdFactConverter extends MdConverterBasic<DataFact, MdFactList.MdFact, MdFactList> {
    private MdUrlConverter mUrlConverter;

    public MdFactConverter(MdUrlConverter urlConverter) {
        super(MdFactList.class);
        mUrlConverter = urlConverter;
    }

    @Override
    public MdFactList.MdFact convert(DataFact datum) {
        ReviewId id = ReviewId.fromString(datum.getReviewId());
        MdFactList.MdFact fact = null;
        if (datum.isUrl()) fact = getMdUrl(datum);

        if(fact == null) fact = new MdFactList.MdFact(datum.getLabel(), datum.getValue(), id);

        return fact;
    }

    private MdUrlList.MdUrl getMdUrl(DataFact datum) {
        ReviewId id = ReviewId.fromString(datum.getReviewId());
        try {
            DataUrl urlDatum = (DataUrl) datum;
            return mUrlConverter.convert(urlDatum);
        } catch (ClassCastException e) {
            String urlGuess = URLUtil.guessUrl(datum.getValue());
            try {
                URL url = new URL(urlGuess);
                return new MdUrlList.MdUrl(datum.getLabel(), url, id);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }
}
