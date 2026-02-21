package org.lpt.betterrcon;

import org.lpt.util.Logger;

public class UtilLogger implements Logger {
    @Override
    public void info(String s) {
        BetterRcon.LOGGER.info(s);
    }

    @Override
    public void error(String s) {
        BetterRcon.LOGGER.error(s);
    }
}
