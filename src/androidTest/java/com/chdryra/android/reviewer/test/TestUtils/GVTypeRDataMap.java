/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GVReviewDataList;
import com.chdryra.android.reviewer.RDComment;
import com.chdryra.android.reviewer.RDFact;
import com.chdryra.android.reviewer.RDImage;
import com.chdryra.android.reviewer.RDLocation;
import com.chdryra.android.reviewer.RDUrl;
import com.chdryra.android.reviewer.RData;
import com.chdryra.android.reviewer.Review;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by: Rizwan Choudrey
 * On: 06/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GVTypeRDataMap {
    private static GVTypeRDataMap sMap;
    private BiMap<GVReviewDataList.GVType, Class<? extends RData>> mGvToRdMap = HashBiMap.create();
    private GvToRd[] mGvToRds;

    private GVTypeRDataMap() {
        mGvToRdMap.put(GVReviewDataList.GVType.COMMENTS, RDComment.class);
        mGvToRdMap.put(GVReviewDataList.GVType.FACTS, RDFact.class);
        mGvToRdMap.put(GVReviewDataList.GVType.IMAGES, RDImage.class);
        mGvToRdMap.put(GVReviewDataList.GVType.LOCATIONS, RDLocation.class);
        mGvToRdMap.put(GVReviewDataList.GVType.URLS, RDUrl.class);
        mGvToRdMap.put(GVReviewDataList.GVType.CHILDREN, NullRData.class);
        mGvToRdMap.put(GVReviewDataList.GVType.REVIEW, NullRData.class);
        mGvToRdMap.put(GVReviewDataList.GVType.SOCIAL, NullRData.class);
        mGvToRdMap.put(GVReviewDataList.GVType.TAGS, NullRData.class);

        mGvToRds = new GvToRd[]{
                new GvToRd<>(GVReviewDataList.GVType.COMMENTS, RDComment.class),
                new GvToRd<>(GVReviewDataList.GVType.FACTS, RDFact.class),
                new GvToRd<>(GVReviewDataList.GVType.IMAGES, RDImage.class),
                new GvToRd<>(GVReviewDataList.GVType.LOCATIONS, RDLocation.class),
                new GvToRd<>(GVReviewDataList.GVType.URLS, RDUrl.class)
        };
    }

    private static GVTypeRDataMap get() {
        if (sMap == null) sMap = new GVTypeRDataMap();
        return sMap;
    }

    public static Class<? extends RData> get(GVReviewDataList.GVType dataType) {
        return get().mGvToRdMap.get(dataType);
    }

    public static <T extends RData> GVReviewDataList.GVType get(Class<T> c) {
        return get().mGvToRdMap.inverse().get(c);
    }

    private class GvToRd<T extends RData> {
        private GVReviewDataList.GVType mGv;
        private Class<T>                mRdClass;

        private GvToRd(GVReviewDataList.GVType gv, Class<T> rdClass) {
            mGv = gv;
            mRdClass = rdClass;
        }
    }

    public class NullRData implements RData {
        @Override
        public Review getHoldingReview() {
            return null;
        }

        @Override
        public void setHoldingReview(Review review) {

        }

        @Override
        public boolean hasData() {
            return false;
        }
    }
}
