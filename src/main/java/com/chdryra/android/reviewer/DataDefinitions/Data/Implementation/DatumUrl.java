/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumUrl extends DatumFact implements DataUrl{
    private final URL mUrl;

    public DatumUrl(ReviewId reviewId, String label, URL url) {
        super(reviewId, label, url.toExternalForm());
        mUrl = url;
    }

    @Override
    public URL getUrl() {
        return mUrl;
    }

    @Override
    public boolean isUrl() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatumUrl)) return false;
        if (!super.equals(o)) return false;

        DatumUrl datumUrl = (DatumUrl) o;

        return !(mUrl != null ? !mUrl.equals(datumUrl.mUrl) : datumUrl.mUrl != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
        return result;
    }
}
