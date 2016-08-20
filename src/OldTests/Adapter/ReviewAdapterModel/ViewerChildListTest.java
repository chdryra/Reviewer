/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.DataDefinitions.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation
        .MdIdableCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverviewList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewsRepository;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerChildListTest extends AndroidTestCase {

    @SmallTest
    public void testGetGridData() {
        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewsFeed repo = RandomReviewsRepository.nextRepository(node);
        ViewerChildList wrapper = new ViewerChildList(getContext(), node, repo);
        GvDataList data = wrapper.getGridData();
        assertNotNull(data);
        MdIdableCollection<ReviewNode> children = node.getChildren();
        assertEquals(children.size(), data.size());
        GvReviewOverviewList list = (GvReviewOverviewList) data;
        for (int i = 0; i < children.size(); ++i) {
            ReviewNode child = children.getItem(i);
            GvReviewOverview item = list.getItem(i);
            assertEquals(child.getSubject().getSubject(), item.getSubject());
            assertEquals(child.getRating().getRating(), item.getRating());
            assertEquals(child.getAuthor(), item.getAuthor());
            assertEquals(child.getPublishDate().getDate(), item.getPublishDate());
            MdLocationList locs = child.getLocations();
            String location = locs.getItem(0).getName();
            if (locs.size() > 1) {
                String loc = locs.size() == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(locs.size() - 1) + loc;
            }
            assertEquals(location, item.getLocationString());
            GvCommentList headlines = MdGvConverter.toGvDataList(child.getComments()).getHeadlines();
            String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() : null;
            assertEquals(headline, item.getHeadline());
            MdImageList covers = child.getImages().getCovers();
            if (covers.size() > 0) {
                boolean isCover = false;
                Bitmap cover = item.getCoverImage();
                for (MdImage image : covers) {
                    if (cover.sameAs(image.getBitmap())) {
                        isCover = true;
                        break;
                    }
                }
                assertTrue(isCover);
            }
        }
    }
}
