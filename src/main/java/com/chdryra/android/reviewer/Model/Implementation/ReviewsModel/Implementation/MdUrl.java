package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;

import java.net.URL;

/**
 * Review Data: URL
 */
public class MdUrl extends MdFact implements DataUrl {
    private final URL mUrl;

    //Constructors
    public MdUrl(MdReviewId reviewId, String label, URL url) {
        super(reviewId, label, url.toExternalForm());
        mUrl = url;
    }

    //Overridden
    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public boolean isUrl() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MdUrl)) return false;
        if (!super.equals(o)) return false;

        MdUrl mdUrl = (MdUrl) o;
        return mUrl.equals(mdUrl.mUrl);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mUrl.hashCode();
        return result;
    }

    @Override
    public URL getUrl() {
        return mUrl;
    }
}
