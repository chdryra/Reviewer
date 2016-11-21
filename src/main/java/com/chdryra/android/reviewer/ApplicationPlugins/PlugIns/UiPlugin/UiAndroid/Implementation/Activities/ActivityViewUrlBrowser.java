/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentEditUrlBrowser;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentOAuthUrlBrowser;
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * UI Activity holding {@link FragmentEditUrlBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityViewUrlBrowser extends ActivityEditUrlBrowser {
    private static final String TAG = TagKeyGenerator.getTag(ActivityViewUrlBrowser.class);
    private static final String URL = TagKeyGenerator.getKey(ActivityViewUrlBrowser.class, "Url");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), URL);
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        return FragmentOAuthUrlBrowser.newInstance(getBundledRequest());
    }

    @Nullable
    private OAuthRequest getBundledRequest() {
        ParcelablePacker<OAuthRequest> packer = new ParcelablePacker<>();
        return packer.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, getIntent());
    }
}
