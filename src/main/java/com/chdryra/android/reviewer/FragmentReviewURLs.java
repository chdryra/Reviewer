/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

/**
 * UI Fragment: URLs (currently disabled). Each grid cell shows a URL.
 * <p/>
 * <p>
 * Base class functionality details:
 * <ul>
 * <li>Banner button: launches {@link ActivityReviewURLBrowser} with Google home</li>
 * <li>Grid cell click: launches {@link ActivityReviewURLBrowser} with selected link</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 */
public class FragmentReviewURLs extends FragmentReviewGridAddEdit<GVUrl> {
    public FragmentReviewURLs() {
        super(GVType.URLS);
        setActivityResultCode(Action.ADD, ActivityResultCode.DONE);
    }
}
