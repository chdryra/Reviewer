/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataUrl;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdUrlList extends MdDataList<MdUrlList.MdUrl> {

    //Constructors
    public MdUrlList(ReviewId reviewId) {
        super(reviewId);
    }

//Classes

    /**
     * Review Data: URL
     * <p>
     * {@link #hasData()}: non-null URL.
     * </p>
     */
    public static class MdUrl extends MdFactList.MdFact implements DataUrl {
        private final URL mUrl;

        //Constructors
        public MdUrl(String label, URL url, ReviewId reviewId) {
            super(label, url.toExternalForm(), reviewId);
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
}
