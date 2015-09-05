package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformSorters extends SorterCollection<GvSocialPlatformList
        .GvSocialPlatform> {
    private static SocialPlatformSorters sSorters = new SocialPlatformSorters();

    private SocialPlatformSorters() {
        super(new FollowersComparator());
    }

    public static SocialPlatformSorters getSorters() {
        return sSorters;
    }

    private static class FollowersComparator implements Comparator<GvSocialPlatformList
            .GvSocialPlatform> {

        @Override
        public int compare(GvSocialPlatformList.GvSocialPlatform lhs, GvSocialPlatformList
                .GvSocialPlatform rhs) {
            return rhs.getFollowers() - lhs.getFollowers();
        }
    }
}
