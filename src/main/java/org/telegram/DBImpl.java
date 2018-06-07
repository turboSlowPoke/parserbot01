package org.telegram;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.telegram.bot.kernel.database.DatabaseManager;
import org.telegram.bot.structure.Chat;
import org.telegram.bot.structure.IUser;

import java.util.Map;

public class DBImpl implements DatabaseManager {
    @Override
    public @Nullable Chat getChatById(int i) {
        return null;
    }

    @Override
    public @Nullable IUser getUserById(int i) {
        return null;
    }

    @Override
    public @NotNull Map<Integer, int[]> getDifferencesData() {
        return null;
    }

    @Override
    public boolean updateDifferencesData(int i, int i1, int i2, int i3) {
        return false;
    }
}
