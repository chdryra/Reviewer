/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
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
    public static class Review {
        public static final List<GvDataType<? extends GvDataParcelable>> FULL = new ArrayList<>();
        static {
            FULL.add(GvTag.TYPE);
            FULL.add(GvComment.TYPE);
            FULL.add(GvImage.TYPE);
            FULL.add(GvLocation.TYPE);
            FULL.add(GvCriterion.TYPE);
            FULL.add(GvFact.TYPE);
        }

        public static final List<GvDataType<? extends GvDataParcelable>> QUICK = new ArrayList<>();
        static {
            QUICK.add(GvTag.TYPE);
            QUICK.add(GvComment.TYPE);
            QUICK.add(GvImage.TYPE);
            QUICK.add(GvLocation.TYPE);
        }
    }

    public static class Meta {
        public static final List<GvDataType<? extends GvData>> ORDER = new ArrayList<>();
        static {
            ORDER.add(GvNode.TYPE);
            ORDER.add(GvAuthorId.TYPE);
            ORDER.add(GvSubject.TYPE);
            ORDER.add(GvDate.TYPE);
            ORDER.add(GvTag.TYPE);
            ORDER.add(GvComment.TYPE);
            ORDER.add(GvImage.TYPE);
            ORDER.add(GvLocation.TYPE);
            ORDER.add(GvCriterion.TYPE);
            ORDER.add(GvFact.TYPE);
        }
    }
}
