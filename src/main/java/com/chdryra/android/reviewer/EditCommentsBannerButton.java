/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 March, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 03/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditCommentsBannerButton extends EditScreenBannerButton {

    public EditCommentsBannerButton(String title) {
        super(ConfigGvDataUi.getConfig(GvCommentList.TYPE).getAdderConfig(), title);
    }

    @Override
    protected boolean addData(GvDataList.GvData data) {
        boolean added = super.addData(data);
        if (getGridData().size() == 1) {
            GvCommentList.GvComment comment = (GvCommentList.GvComment) data;
            comment.setIsHeadline(true);
            getReviewView().updateUi();
        }
        return added;
    }
}
