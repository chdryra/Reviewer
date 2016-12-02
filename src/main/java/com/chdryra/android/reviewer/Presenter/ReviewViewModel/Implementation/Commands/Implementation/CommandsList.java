/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class CommandsList {
    private final List<Command> mCommands;

    public CommandsList() {
        mCommands = new ArrayList<>();
    }

    public void add(Command command) {
        mCommands.add(command);
    }

    public int size() {
        return mCommands.size();
    }

    public ArrayList<String> getCommandNames() {
        ArrayList<String> names = new ArrayList<>();
        for(Command command : mCommands) {
            names.add(command.getName());
        }

        return names;
    }

    public void execute(String name) {
        for (Command command : mCommands) {
            if(command.getName().equals(name)) {
                command.execute();
                break;
            }
        }
    }
}
