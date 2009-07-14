package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RemoteControlPoller implements Runnable {
    private final long pollingIntervalInMilliseconds;
    private final DynamicRemoteControlPool pool;
    private boolean active;

    private static final Log LOGGER = LogFactory.getLog(RemoteControlPoller.class);

    public RemoteControlPoller(double pollingIntervalInSeconds, DynamicRemoteControlPool pool) {
        this.pollingIntervalInMilliseconds = (long) (pollingIntervalInSeconds * 1000);
        this.pool = pool;
        this.active = true;
    }

    public boolean active() {
        return this.active;
    }

    public void stop() {
        this.active = false;
    }

    public void run() {
        while (active) {
            pollAllRegisteredRemoteControls();
        }
    }

    public void pollAllRegisteredRemoteControls() {
        sleepForALittleWhile();
        pool.unregisterAllUnresponsiveRemoteControls();
    }

    protected void sleepForALittleWhile() {
        try {
            Thread.sleep(pollingIntervalInMilliseconds());
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted!");
        }
    }

    public long pollingIntervalInMilliseconds() {
        return pollingIntervalInMilliseconds;
    }


}