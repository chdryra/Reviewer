package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 04/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformComparators extends ComparatorCollection<GvSocialPlatformList
        .GvSocialPlatform> {
    private static SocialPlatformComparators sComparators = new SocialPlatformComparators();

    private SocialPlatformComparators() {
        super(new MostFollowersTheAlphabetical());
    }

    //Static methods
    public static SocialPlatformComparators getComparators() {
        return sComparators;
    }

    private static class MostFollowersTheAlphabetical implements Comparator<GvSocialPlatformList
            .GvSocialPlatform> {

        //Overridden
        @Override
        public int compare(GvSocialPlatformList.GvSocialPlatform lhs, GvSocialPlatformList
                .GvSocialPlatform rhs) {
            int comp;
            if (lhs.getFollowers() > rhs.getFollowers()) {
                comp = -1;
            } else if (rhs.getFollowers() > lhs.getFollowers()) {
                comp = 1;
            } else {
                comp = lhs.getName().compareToIgnoreCase(rhs.getName());
            }

            return comp;
        }
    }
}
