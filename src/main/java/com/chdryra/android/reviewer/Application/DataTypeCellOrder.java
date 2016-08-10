/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 04/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataTypeCellOrder {
    public static class ReviewOrder {
        public static final List<GvDataType<?>> ORDER = new ArrayList<>();
        static {
            ORDER.add(GvTag.TYPE);
            ORDER.add(GvCriterion.TYPE);
            ORDER.add(GvImage.TYPE);
            ORDER.add(GvComment.TYPE);
            ORDER.add(GvLocation.TYPE);
            ORDER.add(GvFact.TYPE);
        }
    }

    public static class MetaOrder {
        public static final List<GvDataType<?>> ORDER = new ArrayList<>();
        static {
            ORDER.add(GvReviewRef.TYPE);
            ORDER.add(GvAuthor.TYPE);
            ORDER.add(GvSubject.TYPE);
            ORDER.add(GvDate.TYPE);
            ORDER.add(GvTag.TYPE);
            ORDER.add(GvCriterion.TYPE);
            ORDER.add(GvImage.TYPE);
            ORDER.add(GvComment.TYPE);
            ORDER.add(GvLocation.TYPE);
            ORDER.add(GvFact.TYPE);
        }
    }
}
