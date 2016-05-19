package com.singh.harsukh.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ReceiverActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    private BluetoothAdapter mBluetoothAdapter = null;
    private static final UUID MY_UUID =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private byte[] buffer = new byte[8192];
    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        image = (ImageView) findViewById(R.id.imageView1);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,
                    "Bluetooth is not available.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this,
                    "Please enable your BT and re-run this program.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        AcceptData acceptData = new AcceptData();
        acceptData.start();
        Bitmap bm1 = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        image.setImageBitmap(bm1);
    }

    class AcceptData extends Thread{
        private final BluetoothServerSocket mmServerSocket;
        private BluetoothSocket socket = null;
        private InputStream mmInStream;
        private String device;
        public AcceptData() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Bluetooth", MY_UUID);
            } catch (IOException e) {
                //
            }
            mmServerSocket = tmp;
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                //
            }
            device = socket.getRemoteDevice().getName();
            Toast.makeText(getBaseContext(), "Connected to " + device, Toast.LENGTH_SHORT).show();
            InputStream tmpIn = null;
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                //
            }
            mmInStream = tmpIn;
            int byteNo;
            try {
                byteNo = mmInStream.read(buffer);
                if (byteNo != -1) {
                    //ensure DATAMAXSIZE Byte is read.
                    int byteNo2 = byteNo;
                    int bufferSize = 7340;
                    while(byteNo2 != bufferSize){
                        bufferSize = bufferSize - byteNo2;
                        byteNo2 = mmInStream.read(buffer,byteNo,bufferSize);
                        if(byteNo2 == -1){
                            break;
                        }
                        byteNo = byteNo+byteNo2;
                    }
                }
                if (socket != null) {
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        //
                    }
                }
            }
            catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
