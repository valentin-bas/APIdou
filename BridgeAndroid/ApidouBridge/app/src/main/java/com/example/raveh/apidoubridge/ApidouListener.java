package com.example.raveh.apidoubridge;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.ScratchBank;

/**
 * Created by Raveh on 31/05/2015.
 */
public class ApidouListener implements BeanListener
{
    private MainActivity _context;
    private Bean _bean;
    private ApidouManager _manager;

    private Callback<Acceleration> _accelCallback = new Callback<Acceleration>() {
        @Override
        public void onResult(Acceleration acceleration) {
            _context.debugMessage(_bean.getDevice().getName() +  " / Accel : " + acceleration.toString());
            _bean.readAcceleration(_accelCallback);
        }
    };

    public void init(ApidouManager manager, MainActivity context, Bean bean)
    {
        _manager = manager;
        _context = context;
        _bean = bean;
    }

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
    }

    public void onScratchValueChanged(ScratchBank bank, byte[] value)
    {
        _context.debugMessage("Bean scratch value changed !" + new String(value));
    }

    public void onError(BeanError error)
    {
        _context.debugMessage("Bean error !");
    }
}