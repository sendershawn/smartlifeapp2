package com.example.smartlifeapp2;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

public class BlueToothService extends Service {
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;
    private ListView mDevicesListView;
    private Handler mHandler;
    // Our main handler that will receive callback notifications
    public static ConnectedThread mConnectedThread;
    // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null;
    // bi-directional client-to-client data path
    private static final UUID BTMODULEUUID = UUID.fromString
            ("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1;
    // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2;
    // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3;
    // used in bluetooth handler to identify message status
    private  String _recieveData = "";
    public static String ConnectAddress="";
    public static String name;
    public static boolean BTSisOn=false;
    public static String[] data={"0","no data","no data","0"};
    public static  int nowMode=1;

    public class LocalBinder extends Binder{
        BlueToothService getservice(){
            return  BlueToothService.this;
        }
    }
    private LocalBinder mLocBin = new LocalBinder();



    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return mLocBin;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO Auto-generated method stub
        Log.d("service connect address",""+ConnectAddress);
        if (ConnectAddress == null) {
            onDestroy();
        } else {

            mHandler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MESSAGE_READ) { //收到MESSAGE_READ 開始接收資料
                        String readMessage = null;
                        try {
                            readMessage = new String((byte[]) msg.obj, "UTF-8");
                            readMessage = readMessage.substring(0, 15);
                            //取得傳過來字串的第一個字元，其餘為雜訊
                            _recieveData += readMessage; //拼湊每次收到的字元成字串
                            Log.d("reciveData",_recieveData);
                            if(_recieveData.length()==15){
                                data = _recieveData.split(" ");
                                for (int i=0;i<4;i++){Log.d("Data"+"["+i+"]=======","/"+data[i]+"/");}
                                _recieveData="";
                                nowMode=Integer.parseInt(data[3]);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Toast.makeText(getBaseContext(),ConnectAddress, Toast.LENGTH_SHORT).show();
        if (ConnectAddress==null)
        {
            onDestroy();
        }
        else {
            Toast.makeText(getBaseContext(), "Service is on", Toast.LENGTH_SHORT).show();
            new Thread() {
                public void run() {
                    boolean fail = false;
                    mBTAdapter = BluetoothAdapter.getDefaultAdapter();
                    Log.d("creatSoketAddress",ConnectAddress);
                    BluetoothDevice device = mBTAdapter.getRemoteDevice(ConnectAddress);
                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        mBTSocket.connect(); //建立藍芽連線
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close(); //關閉socket
                            //開啟執行緒 顯示訊息
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            //insert code to deal with this
                            Toast.makeText(getBaseContext(), "Socket creation failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (fail == false) {
                        //開啟執行緒用於傳輸及接收資料
                        BTSisOn = true;
                        mConnectedThread = new ConnectedThread(mBTSocket);
                        mConnectedThread.start();
                        //開啟新執行緒顯示連接裝置名稱
                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name).sendToTarget();
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mConnectedThread.write("5");

                    }
                }
            }.start();

        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public boolean onUnbind(Intent intent)
    {
        // TODO Auto-generated method stub
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy()
    {
        BTSisOn=false;
        super.onDestroy();
        // TODO Auto-generated method stub
    }





    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws
            IOException {
        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }


    public String getMacAddress(String address,String na) {//獲取連接MAC的資訊
        this.ConnectAddress=address;
        this.name=na;
        return address;
    }

    public Integer retrunMode(){
        return nowMode;
    }
    public String returnWater(){
        return data[0];}
    public String returnHumidity(){
        return data[1];}
    public String returnTemperature(){
        return data[2];}



    public class ConnectedThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();//converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }
        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        SystemClock.sleep(100);
                        //pause and wait for rest of data
                        bytes = mmInStream.available();
                        // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes);
                        // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }
        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }
}
