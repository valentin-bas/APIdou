package com.example.raveh.apidoubridge;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.UUID;

/**
 * Created by Raveh on 31/05/2015.
 */
public class ApidouListener implements BeanListener
{
    private MainActivity _context;
    private Bean _bean;
    private ApidouManager _manager;
    private ApidouListener _eventsRedirectionListener;
    private String _nameOverride;
    private UUID _uuid;
    private ApidouGestureDetector _gestureDetector;
    //ctor dtor

    ApidouListener()
    {
        _nameOverride = null;
        _uuid = UUID.randomUUID();
        _gestureDetector = new ApidouGestureDetector();
    }

    //getters setters

    public Bean getBean() { return _bean; }
    public String getName() { return _nameOverride == null ? _bean.getDevice().getName() : _nameOverride; }
    public UUID getUUID() { return _uuid; }
    public void setRedirectionListener(ApidouListener listner) { _eventsRedirectionListener = listner; }
    public void setOverrideName(String name) { _nameOverride = name; }

    //public functions

    public void init(ApidouManager manager, MainActivity context, Bean bean)
    {
        _manager = manager;
        _context = context;
        _bean = bean;
    }

    //callbacks events

    public void onAccelerationData(Acceleration acceleration)
    {
        _context.debugMessage(getName() +  " / Accel : " + acceleration.toString());
        _gestureDetector.addAccel(acceleration);
    }

    //events

    public void onConnected()
    {
        _context.debugMessage("Bean connected !");
        _bean.readAcceleration(_accelCallback);
    }

    public void onConnectionFailed()
    {
        _context.debugMessage("Failed to connect on the bean !");
    }

    public void onDisconnected()
    {
        _context.debugMessage("Bean disconnected !");
    }

    public void onSerialMessageReceived(byte[] data)
    {
        _context.debugMessage("Bean message received !" + new String(data));
        if (_eventsRedirectionListener != null)
            _eventsRedirectionListener.getBean().sendSerialMessage(data);
    }

    public void onScratchValueChanged(ScratchBank bank, byte[] value)
    {
        _context.debugMessage("Bean scratch value changed !" + new String(value));
    }

    public void onError(BeanError error)
    {
        _context.debugMessage("Bean error !");
    }

    //callbacks

    public Callback<Acceleration> _accelCallback = new Callback<Acceleration>() {
        @Override
        public void onResult(Acceleration acceleration)
        {
            onAccelerationData(acceleration);
            _bean.readAcceleration(_accelCallback);
        }
    };
}
