package ru.spbau.anastasia.race;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectionGame extends Activity {

    int numOfTheme;
    ImageView fon;
    private  boolean isSound;
    private CheckBox scanner;

    private BluetoothSocket btSocket;

    public static final String NAME = "ConnectionGame";

    private final int REQUEST_CONNECT = 1;
    private final int REQUEST_ENABLE_BT = 2;
    private final int HANDLER_MESSAGE_GET = 3;

    private ConnectionServer connectionServer;
    private ConnectionClient connectionClient;
    private ConnectedThread connectedThread;

    private ArrayAdapter<String> arrayAdapter;
    private EditText editText;

    private static final UUID MY_UUID = UUID.fromString("01feca47-53c9-4a79-ae37-015d0e521cfc");

    private Button findDevice;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_game);

        fon = (ImageView) findViewById(R.id.connectionGame);
        numOfTheme = getIntent().getExtras().getInt("theme");
        isSound = getIntent().getExtras().getBoolean("sound");
        if (numOfTheme == GameMenu.IS_CHECKED){
            fon.setImageResource(R.drawable.two_players);
        } else {
            fon.setImageResource(R.drawable.players_option);
        }
        scanner = (CheckBox) findViewById(R.id.checkBox);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        findDevice = (Button) findViewById(R.id.find_device);
        findDevice.setEnabled(false);
        isChecked();

        arrayAdapter = new ArrayAdapter<>(this, R.layout.message);
        ListView listView = (ListView) findViewById(R.id.device_choose);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onDeviceClick);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);// todo Не забудьте снять регистрацию в onDestroy
    }

    private void isChecked() {
        if (bluetoothAdapter == null) {
            finish();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            findDevice.setEnabled(true);
            connectionServer = new ConnectionServer();
            connectionServer.start();
        }
    }

    public void onClickButtonStartTwoPlayers(View view) {
        Intent intent = new Intent(ConnectionGame.this, RoadForTwo.class);
        intent.putExtra("isServer", scanner.isChecked());
        intent.putExtra("theme", numOfTheme);
        intent.putExtra("sound", isSound);
        startActivity(intent);
    }

    public BluetoothSocket getBluetoothSocket() {
        return btSocket;
    }

    @Override
    protected void onDestroy() {
        if (connectionClient != null) {
            connectionClient.cancel();
            connectionClient = null;
        }

        if (connectionServer != null) {
            connectionServer.cancel();
            connectionServer = null;
        }

        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException ignored) { }
            btSocket = null;
        }

        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

    public void onClickButtonFindDevice(View view) {
        bluetoothAdapter.startDiscovery();
        Log.d(NAME, "discovery started");
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d(NAME, "device found");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };

    private AdapterView.OnItemClickListener onDeviceClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            bluetoothAdapter.cancelDiscovery();

            String str = ((TextView) view).getText().toString();

            Log.d(NAME, str);

            connectionClient = new ConnectionClient(bluetoothAdapter.
                    getRemoteDevice(str.substring(str.length() - 17)));
            connectionClient.start();
        }
    };


    public void onClickButtonReader(View view) {
        connectedThread.write(new byte[]{2});
    }


    public void onClickButtonWrite(View view) {

    }

    public void onClickButtonVisibleBT(View view) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    private synchronized void  connected(BluetoothSocket socket) {
        Log.d(NAME, "here");
        Log.d(NAME, Boolean.toString(socket.isConnected()));
        btSocket = socket;

        if (connectionServer != null) {
            connectionServer.cancel();
            connectionServer = null;
        }

        if (connectionClient != null) {
            connectionClient.cancel();
            connectionClient = null;
        }

        Log.d(NAME, Boolean.toString(socket.isConnected()));

        connectedThread = new ConnectedThread();
        connectedThread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                switch (resultCode) {
                    case RESULT_OK:
                        findDevice.setEnabled(true);
                        connectionServer = new ConnectionServer();
                        connectionServer.start();
                        break;

                    default:
                        finish();
                        break;
                }
                break;
        }
    }

    private class ConnectionServer extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public ConnectionServer() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException ignored) {
            }
            mmServerSocket = tmp;
        }

        public void run() {
            Log.d(NAME, "server");
            BluetoothSocket socket;
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                Log.d(NAME, "server1");
                if (socket != null) {
                    connected(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException ignored) {
                    }
                    break;
                }
            }

        }

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private class ConnectionClient extends Thread {
        private BluetoothSocket socket;

        public ConnectionClient(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException ignored) {
            }
            socket = tmp;
        }

        public void run() {
            Log.d(NAME, "here1");
            try {
                socket.connect();
            } catch (IOException connectException) {
                Log.d(NAME, "error");
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
                return;
            }
            BluetoothSocket tmp = socket;
            socket = null;
            connected(tmp);
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread() {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = btSocket.getInputStream();
                tmpOut = btSocket.getOutputStream();
            } catch (IOException ignored) {
                Log.d(NAME, "error");
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    handler.obtainMessage(HANDLER_MESSAGE_GET, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
            Log.d(NAME, "qweqwe");
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException ignored) {
            }
        }

        public void cancel() {
            try {
                btSocket.close();
            } catch (IOException ignored) {
            }
        }
    }

    @SuppressWarnings("all")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            byte[] bytes = (byte[]) msg.obj;
            Log.d(NAME, Byte.toString(bytes[0]));
            ((Button) findViewById(R.id.read)).setText("123");
        }
    };
}