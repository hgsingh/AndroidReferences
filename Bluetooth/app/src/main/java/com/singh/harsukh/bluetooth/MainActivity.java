package com.singh.harsukh.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter = null;
    static final UUID MY_UUID =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    static String address = "50:C3:00:00:00:00";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        final SendData sendData = new SendData();
        Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendData.sendMessage();
            }
        });
    }

    class SendData extends Thread {
        private BluetoothDevice device = null;
        private BluetoothSocket btSocket = null;
        private OutputStream outStream = null;

        public SendData(){
            device = mBluetoothAdapter.getRemoteDevice(address);
            try
            {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            }
            catch (Exception e) {
                // TODO: handle exception
            }
            mBluetoothAdapter.cancelDiscovery();
            try {
                btSocket.connect();
            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                }
            }
            Toast.makeText(getBaseContext(), "Connected to " + device.getName(), Toast.LENGTH_SHORT).show();
            try {
                outStream = btSocket.getOutputStream();
            } catch (IOException e) {
            }
        }

        public void sendMessage()
        {
            try {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100,baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                Toast.makeText(getBaseContext(), String.valueOf(b.length), Toast.LENGTH_SHORT).show();
                outStream.write(b);
                outStream.flush();
            } catch (IOException e) {
            }
        }
    }
}
