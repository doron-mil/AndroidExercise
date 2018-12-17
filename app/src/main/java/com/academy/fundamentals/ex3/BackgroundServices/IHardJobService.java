package com.academy.fundamentals.ex3.BackgroundServices;

public interface IHardJobService {

    public void broadcastMessage(String aMessage);
    public void broadcastProgress(int aProgress);
    public boolean isDestroyed();
    public void stopService(int startId);
}
