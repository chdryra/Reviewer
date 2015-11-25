package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFactList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImageList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface TreeDataGetter {
    void setRoot(ReviewNode root);

    MdCriterionList getCriteria();

    MdCommentList getComments();

    MdImageList getImages();

    MdFactList getFacts();

    MdLocationList getLocations();
}
