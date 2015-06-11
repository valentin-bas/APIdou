package com.example.raveh.apidoubridge;

import android.os.AsyncTask;
import android.widget.CheckBox;

/**
 * Created by Raveh on 11/06/2015.
 */
public class TcpConnectTask extends AsyncTask
{
    private TcpClient mTcpClient;
    private String mIp;
    private CheckBox mCheckbox;

    public TcpConnectTask(String ip, CheckBox box)
    {
        mIp = ip;
        mCheckbox = box;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                if (message == "connected")
                    publishProgress(message);
            }
        });
        mTcpClient.run(mIp);
        return null;
    }

    @Override
    protected void onProgressUpdate (Object... values)
    {
        mCheckbox.setChecked(true);
    }
}
