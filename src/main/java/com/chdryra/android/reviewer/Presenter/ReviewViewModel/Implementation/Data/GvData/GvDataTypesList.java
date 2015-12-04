package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataTypesList {
    public static final ArrayList<GvDataType<? extends GvData>> BUILD_TYPES = new ArrayList<>();
    public static final ArrayList<GvDataType<? extends GvData>> ALL_TYPES = new ArrayList<>();
    static {
        BUILD_TYPES.add(GvComment.TYPE);
        BUILD_TYPES.add(GvFact.TYPE);
        BUILD_TYPES.add(GvLocation.TYPE);
        BUILD_TYPES.add(GvImage.TYPE);
        BUILD_TYPES.add(GvUrl.TYPE);
        BUILD_TYPES.add(GvTag.TYPE);
        BUILD_TYPES.add(GvCriterion.TYPE);

        ALL_TYPES.addAll(BUILD_TYPES);
        ALL_TYPES.add(GvSubject.TYPE);
        ALL_TYPES.add(GvDate.TYPE);
    }
}
