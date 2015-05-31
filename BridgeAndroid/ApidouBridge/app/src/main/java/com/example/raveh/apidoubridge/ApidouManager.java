package com.example.raveh.apidoubridge;

import android.content.Context;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanManager;

import java.util.Collection;

/**
 * Created by Raveh on 30/05/2015.
 */
public class ApidouManager
{
    private BeanManager _beanManager;
    private ApidouDiscoveryListener _discoveryListener;
    private Collection<ApidouListener> _apidous;
    private MainActivity _context;

    public void init(MainActivity context)
    {
        _discoveryListener = new ApidouDiscoveryListener();
        _discoveryListener.init(this);
        _beanManager = BeanManager.getInstance();
        _context = context;
    }

    public void startDiscovery()
    {
        _beanManager.startDiscovery(_discoveryListener);
    }

    public void onBeanDiscovered(Bean bean, int rssi)
    {
        //TODO: check on network if we need to connect to this bean
        _context.debugMessage("New bean discovered : " + bean.getDevice().getName() + " / RSSI : " + rssi);
        ApidouListener apidouListener = new ApidouListener();
        apidouListener.init(this, _context, bean);
        _apidous.add(apidouListener);
        bean.connect(_context, apidouListener);
    }

    public void onBeanDiscoveryComplete()
    {
        _context.debugMessage("Discovery complete");
    }

    private class ApidouDiscoveryListener implements BeanDiscoveryListener
    {
        ApidouManager _manager;
        public void init(ApidouManager manager) {
            _manager = manager;
        }
        public void onBeanDiscovered(Bean bean, int rssi) {
            _manager.onBeanDiscovered(bean, rssi);
        }
        public void onDiscoveryComplete() {
            _manager.onBeanDiscoveryComplete();
        }
    }
}
