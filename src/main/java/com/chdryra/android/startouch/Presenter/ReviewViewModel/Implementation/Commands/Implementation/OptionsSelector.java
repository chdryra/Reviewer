/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class OptionsSelector extends Command {
    public static final String OPTIONS
            = TagKeyGenerator.getKey(OptionsSelector.class, "Options");
    public static final String CURRENTLY_SELECTED
            = TagKeyGenerator.getKey(OptionsSelector.class, "Selected");

    private final LaunchableConfig mOptionsConfig;
    private int mCode;
    private ArrayList<String> mOptions;
    private String mCurrentlySelected;

    public OptionsSelector(LaunchableConfig optionsConfig) {
        super(Strings.Commands.OPTIONS);
        mOptionsConfig = optionsConfig;
        mCode = optionsConfig.getDefaultRequestCode();
    }

    public void execute(Collection<String> options, @Nullable String currentlySelected,
                        int requestCode, @Nullable ExecutionListener listener) {
        mOptions = new ArrayList<>(options);
        mCurrentlySelected = currentlySelected;
        mCode = requestCode;
        execute(mCode, listener);
    }

    public void execute(Collection<String> options, @Nullable String currentlySelected) {
        mOptions = new ArrayList<>(options);
        mCurrentlySelected = currentlySelected;
        mCode = RequestCodeGenerator.getCode(OptionsSelector.class, TextUtils.commaDelimited
                (mOptions));
        execute();
    }

    @Override
    public void execute() {
        if (mOptions == null) return;

        Bundle args = new Bundle();
        args.putStringArrayList(OPTIONS, mOptions);
        args.putString(CURRENTLY_SELECTED, mCurrentlySelected);

        mOptionsConfig.launch(new UiLauncherArgs(mCode).setBundle(args));

        onExecutionComplete();
    }
}
