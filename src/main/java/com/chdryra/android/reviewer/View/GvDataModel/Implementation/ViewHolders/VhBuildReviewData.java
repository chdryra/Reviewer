package com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhBuildReviewData extends VhDataCollection {
    //Overridden
    @Override
    protected String getUpperString(int number) {
        return number == 0 ? "+" : super.getUpperString(number);
    }
}
