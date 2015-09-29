package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultParameters {
    public static void setParams(ReviewView view, GvDataType dataType) {
        if (dataType == GvImageList.GvImage.TYPE) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            view.getParams().setCellHeight(half).setCellWidth(half);
        }
    }
}
