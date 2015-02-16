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
public class CoverManagerAdapter implements CoverManager {
    private final GvAdapter mAdapter;

    public CoverManagerAdapter(GvAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void updateCover(FragmentViewReview fragment) {
        GvImageList images = (GvImageList) mAdapter.getData(GvDataList.GvType.IMAGES);
        GvImageList covers = images.getCovers();
        fragment.setCover(covers.getRandomCover());
    }

    @Override
    public void proposeCover(GvImageList.GvImage image) {
        GvImageList images = (GvImageList) mAdapter.getData(GvDataList.GvType.IMAGES);
        GvImageList covers = images.getCovers();
        if (covers.size() == 1 && images.contains(image)) {
            covers.getItem(0).setIsCover(false);
            image.setIsCover(true);
        }
    }
}
