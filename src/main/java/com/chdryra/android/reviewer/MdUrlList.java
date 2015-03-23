/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdUrlList extends MdDataList<MdUrlList.MdUrl> {

    public MdUrlList(Review holdingReview) {
        super(holdingReview);
    }

    /**
     * Review Data: URL
     * <p>
     * {@link #hasData()}: non-null URL.
     * </p>
     */
    public static class MdUrl extends MdFactList.MdFact implements DataUrl {
        private final URL mUrl;

        public MdUrl(String label, URL url, Review holdingReview) {
            super(label, url.toExternalForm(), holdingReview);
            mUrl = url;
        }

        @Override
        public boolean hasData() {
            return DataValidator.validate(this);
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
