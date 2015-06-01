package com.example.raveh.apidoubridge;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Raveh on 30/05/2015.
 */
public class ApidouManager
{
    private List<ApidouListener> _apidous = new ArrayList<ApidouListener>();

    private BeanManager _beanManager;
    private ApidouDiscoveryListener _discoveryListener;
    private MainActivity _context;

    private static ApidouManager _instance;

    private ApidouManager() { }

    public synchronized static ApidouManager getInstance()
    {
        if (_instance == null)
        {
            _instance = new ApidouManager();
        }
        return _instance;
    }

    //getters setters

    public List<ApidouListener> getApidous() { return _apidous; }
    public ApidouListener getApidouWithUUID(UUID uuid)
    {
        for (int i = 0; i < _apidous.size(); i++)
        {
            if (_apidous.get(i).getUUID().equals(uuid))
                return _apidous.get(i);
        }
        return null;
    }

    //public functions

    public void init(MainActivity context)
    {
        _discoveryListener = new ApidouDiscoveryListener();
        _discoveryListener.init(this);
        _beanManager = BeanManager.getInstance();
        _context = context;
    }

    public void debugPopulate(int count)
    {
        //debug
        for (int i = 0; i < count; ++i)
        {
            ApidouListener debugApidou = new ApidouListener();
            debugApidou.setOverrideName("DebugApidou" + i);
            _apidous.add(debugApidou);
        }
    }

    public void startDiscovery()
    {
        _context.debugMessage("Start discovery");
        _beanManager.startDiscovery(_discoveryListener);
    }

    //events

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

    //BeanDiscoveryListener

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

    // private functions


}
