package com.example.raveh.apidoubridge;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Raveh on 30/05/2015.
 */
public class ApidouManager
{
    public List<String> discoveredNamesList = new ArrayList<String>();

    private Collection<ApidouListener> _apidous = new ArrayList<ApidouListener>();

    private BeanManager _beanManager;
    private ApidouDiscoveryListener _discoveryListener;
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
        _context.debugMessage("Start discovery");
        _beanManager.startDiscovery(_discoveryListener);
        discoveredNamesList.add("THIS IS A TEST");
    }

    public void onBeanDiscovered(Bean bean, int rssi)
    {
        //TODO: check on network if we need to connect to this bean
        _context.debugMessage("New bean discovered : " + bean.getDevice().getName() + " / RSSI : " + rssi);
        ApidouListener apidouListener = new ApidouListener();
        apidouListener.init(this, _context, bean);
        _apidous.add(apidouListener);
        bean.connect(_context, apidouListener);
        discoveredNamesList.add(bean.getDevice().getName());
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
