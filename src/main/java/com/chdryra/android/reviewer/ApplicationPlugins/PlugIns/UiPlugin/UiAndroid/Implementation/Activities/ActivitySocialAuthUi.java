/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FactoryFragmentSocialLogin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentOAuthLogin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Social.Factories.FactoryLoginResultHandler;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.Authentication.Interfaces.BinaryResultCallback;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivitySocialAuthUi extends ActivitySingleFragment
        implements BinaryResultCallback<Object, Object>, LaunchableUi {
    private static final String TAG = TagKeyGenerator.getTag(ActivitySocialAuthUi.class);
    private static final String PLATFORM = TagKeyGenerator.getKey(ActivitySocialAuthUi.class, "Platform");

    private Fragment mFragment;
    private FactoryFragmentSocialLogin mFragmentFactory;
    private FactoryLoginResultHandler mHandlerFactory;

    private BinaryResultCallback mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationInstance app = ApplicationInstance.getInstance(this);
        mFragmentFactory = new FactoryFragmentSocialLogin();
        mHandlerFactory = new FactoryLoginResultHandler(app.getSocialPlatformList());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        OAuthRequest request = getBundledRequest();
        if(request != null) return FragmentOAuthLogin.newInstance(request);

        String platform = getBundledPlatform();
        mFragment = mFragmentFactory.newFragment(platform);
        mHandler = mHandlerFactory.newSocialLoginHandler(platform);
        return mFragment;
    }

    //TODO make type safe
    @Override
    public void onSuccess(Object result) {
        mHandler.onSuccess(result);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailure(Object result) {
        mHandler.onFailure(result);
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), PLATFORM);
    }

    @Nullable
    private OAuthRequest getBundledRequest() {
        Bundle args = getIntent().getBundleExtra(PLATFORM);
        ParcelablePacker<OAuthRequest> unpacker = new ParcelablePacker<>();
        return unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, args);
    }

    private String getBundledPlatform() {
        Bundle args = getIntent().getBundleExtra(PLATFORM);
        if (args == null) throwError();
        String platform = args.getString(TAG);
        if (platform == null || platform.length() == 0) throwError();

        return platform;
    }

    private String throwError() {
        throw new RuntimeException("No platform specified!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }
}
