package com.chdryra.android.reviewer.View.GvDataModel;

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
        BUILD_TYPES.add(GvCommentList.GvComment.TYPE);
        BUILD_TYPES.add(GvFactList.GvFact.TYPE);
        BUILD_TYPES.add(GvLocationList.GvLocation.TYPE);
        BUILD_TYPES.add(GvImageList.GvImage.TYPE);
        BUILD_TYPES.add(GvUrlList.GvUrl.TYPE);
        BUILD_TYPES.add(GvTagList.GvTag.TYPE);
        BUILD_TYPES.add(GvCriterionList.GvCriterion.TYPE);

        ALL_TYPES.addAll(BUILD_TYPES);
        ALL_TYPES.add(GvSubjectList.GvSubject.TYPE);
        ALL_TYPES.add(GvDateList.GvDate.TYPE);
    }
}
