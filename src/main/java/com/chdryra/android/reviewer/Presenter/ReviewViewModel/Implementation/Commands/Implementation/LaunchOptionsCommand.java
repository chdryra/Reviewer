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

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchOptionsCommand extends OptionsCommand {
    public static final String OPTIONS
            = TagKeyGenerator.getKey(LaunchOptionsCommand.class, "Options");
    public static final String CURRENTLY_SELECTED
            = TagKeyGenerator.getKey(LaunchOptionsCommand.class, "Selected");

    private final LaunchableConfig mOptionsConfig;
    private int mCode;
    private CommandsList mOptions;
    private String mCurrentlySelected;

    public LaunchOptionsCommand(LaunchableConfig optionsConfig) {
        super(Strings.Commands.OPTIONS);
        mOptionsConfig = optionsConfig;
        mCode = optionsConfig.getDefaultRequestCode();
    }

    public void execute(CommandsList options,
                        @Nullable String currentlySelected,
                        int requestCode,
                        @Nullable ExecutionListener listener) {
        mOptions = options;
        mCurrentlySelected = currentlySelected;
        execute(requestCode, listener);
    }

    public void execute(CommandsList options,
                        @Nullable String currentlySelected) {
        mOptions = options;
        mCurrentlySelected = currentlySelected;
        execute();
    }

    @Override
    public void execute() {
        if(mOptions == null) return;

        Bundle args = new Bundle();
        ArrayList<String> options = mOptions.getCommandNames();
        args.putStringArrayList(OPTIONS, options);
        args.putString(CURRENTLY_SELECTED, mCurrentlySelected);

        mCode = RequestCodeGenerator.getCode(LaunchOptionsCommand.class,
                TextUtils.commaDelimited(mOptions.getCommandNames()));
        mOptionsConfig.launch(new UiLauncherArgs(mCode).setBundle(args));

        onExecutionComplete();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        if(requestCode == mCode) {
            mOptions.execute(option);
            return true;
        }

        return false;
    }
}
