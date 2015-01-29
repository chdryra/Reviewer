/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 29 January, 2015
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 29/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CoverManagerImageList implements CoverManager {
    private final GvImageList mImages;

    public CoverManagerImageList(GvImageList images) {
        mImages = images;
    }

    @Override
    public void updateCover(FragmentViewReview fragment) {
        GvImageList.GvImage cover = mImages.getRandomCover();
        if (!cover.isValidForDisplay() && mImages.size() > 0) {
            cover = mImages.getItem(0);
            cover.setIsCover(true);
        }

        fragment.setCover(cover);
    }

    @Override
    public void proposeCover(GvImageList.GvImage image) {
        GvImageList covers = mImages.getCovers();
        if (covers.size() == 1 && mImages.contains(image)) {
            covers.getItem(0).setIsCover(false);
            image.setIsCover(true);
        }
    }
}
