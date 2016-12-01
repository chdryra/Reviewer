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
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchOptionsCommand extends Command implements OptionSelectListener{
    public static final String OPTIONS
            = TagKeyGenerator.getKey(LaunchOptionsCommand.class, "Options");
    public static final String CURRENTLY_SELECTED
            = TagKeyGenerator.getKey(LaunchOptionsCommand.class, "Selected");

    private final LaunchableConfig mOptionsConfig;
    private int mCode;
    private List<NamedCommand> mOptions;
    private String mCurrentlySelected;

    public LaunchOptionsCommand(LaunchableConfig optionsConfig) {
        mOptionsConfig = optionsConfig;
        mCode = mOptionsConfig.getDefaultRequestCode();
    }

    public void execute(int requestCode, List<NamedCommand> options, @Nullable String currentlySelected) {
        mCode= requestCode;
        mOptions = options;
        mCurrentlySelected = currentlySelected;
        execute();
    }

    @Override
    public void execute() {
        if(mOptions == null) return;

        Bundle args = new Bundle();
        args.putStringArrayList(OPTIONS, getOptionNames());
        args.putString(CURRENTLY_SELECTED, mCurrentlySelected);
        mOptionsConfig.launch(new UiLauncherArgs(mCode).setBundle(args));
        onExecutionComplete();
    }

    private ArrayList<String> getOptionNames() {
        ArrayList<String> names = new ArrayList<>();
        for(NamedCommand option : mOptions) {
            names.add(option.getName());
        }

        return names;
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        if(requestCode == mCode) {
            for (NamedCommand command : mOptions) {
                if(command.getName().equals(option)) {
                    command.execute();
                    break;
                }
            }

            return true;
        }

        return false;
    }
}
