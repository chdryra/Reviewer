/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;



import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchOptionsCommand extends Command {
    public static final String OPTIONS
            = TagKeyGenerator.getKey(LaunchOptionsCommand.class, "Options");
    public static final String CURRENTLY_SELECTED
            = TagKeyGenerator.getKey(LaunchOptionsCommand.class, "Selected");

    private final LaunchableConfig mOptionsConfig;
    private int mCode;
    private ArrayList<String> mOptions;
    private String mSelected;

    public LaunchOptionsCommand(LaunchableConfig optionsConfig) {
        mOptionsConfig = optionsConfig;
        mCode = mOptionsConfig.getDefaultRequestCode();
    }

    public void execute(int requestCode, ArrayList<String> options, @Nullable String selected) {
        mCode= requestCode;
        mOptions = options;
        mSelected = selected;
        execute();
    }

    @Override
    public void execute() {
        if(mOptions == null) return;

        Bundle args = new Bundle();
        args.putStringArrayList(OPTIONS, mOptions);
        args.putString(CURRENTLY_SELECTED, mSelected);
        mOptionsConfig.launch(new UiLauncherArgs(mCode).setBundle(args));
        onExecutionComplete();
    }
}
