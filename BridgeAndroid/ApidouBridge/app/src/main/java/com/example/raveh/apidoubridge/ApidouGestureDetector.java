package com.example.raveh.apidoubridge;

import com.punchthrough.bean.sdk.message.Acceleration;

import java.util.LinkedList;

/**
 * Created by Raveh on 02/06/2015.
 */
public class ApidouGestureDetector
{
    private  static int HISTORY_MAX_SIZE = 50;
    private  static int FRAME_BTWN_GEST_RECO = 10; //frame between gesture recognition

    private int _frameCounter;
    private LinkedList<AccelTime> _accelHistory = new LinkedList<>();

    private class AccelTime
    {
        public Acceleration acceleration;
        public long timestamp;

        public AccelTime(Acceleration accel)
        {
            acceleration = accel;
            timestamp = System.currentTimeMillis();
        }
    }

    public void addAccel(Acceleration acceleration)
    {
        AccelTime accel = new AccelTime(acceleration);
        _frameCounter++;
        if (_accelHistory.size() >= HISTORY_MAX_SIZE)
            _accelHistory.removeLast();
        _accelHistory.addFirst(accel);
        if (_frameCounter >= FRAME_BTWN_GEST_RECO)
        {
            _frameCounter = 0;
            _detectGesture();
        }
    }

    private void _detectGesture()
    {

    }
}
