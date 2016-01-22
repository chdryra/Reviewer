package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialMostFollowersThenAlphabetical implements Comparator<DataSocialPlatform> {
    @Override
    public int compare(DataSocialPlatform lhs, DataSocialPlatform rhs) {
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
