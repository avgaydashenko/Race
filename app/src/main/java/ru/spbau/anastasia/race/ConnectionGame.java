package ru.spbau.anastasia.race;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ConnectionGame extends Activity {
    private int numOfTheme;
    private boolean isServer;
    private boolean isSound;
    private final int REQUEST_CONNECT = 1;
    private final int REQUEST_ENABLE_BT = 2;
    private final int HANDLER_MESSAGE_GET = 1;

    private static final String stopMessage = "stop";
    private ArrayAdapter<String> arrayAdapter;
    private EditText editText;

    private BluetoothService btService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateActivity();
        setContentView(R.layout.activity_connection_game);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.message);
        ((ListView) findViewById(R.id.messages)).setAdapter(arrayAdapter);
        editText = (EditText) findViewById(R.id.edit_text);
        Button button = (Button) findViewById(R.id.send);
        button.setOnClickListener(onClickListener);

        Intent btServiceIntent = new Intent(this, BluetoothService.class);
        startService(btServiceIntent);
        bindService(btServiceIntent, connection, Context.BIND_AUTO_CREATE);
    }


    public void onClickButtonReconnection(View view) {
        toDeviceChooser();
        btService.write(stopMessage.getBytes());
    }

    private void toDeviceChooser(){
        startActivityForResult(new Intent(ConnectionGame.this, DeviceChooser.class),
                REQUEST_CONNECT);
    }

    public void onClickButtonBackTwoPlayerOption(View view) {
        finish();
    }

    private void onCreateActivity() {
        setContentView(R.layout.activity_two_players_option);
        ImageView fon = (ImageView) findViewById(R.id.imageTwoPlayersOption);
        numOfTheme = getIntent().getExtras().getInt("theme");
        isSound = getIntent().getExtras().getBoolean("sound");
        if (numOfTheme == GameMenu.IS_CHECKED) {
            fon.setImageResource(R.drawable.two_players_option2);
        } else {
            fon.setImageResource(R.drawable.two_players_option);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(btService.isBegin){
            btService.write(stopMessage.getBytes());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(btService.isBegin){
            btService.write(stopMessage.getBytes());
        }
        unbindService(connection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            btService = ((BluetoothService.BtBinder) service).getService();

            btService.setOnMessageReceived(new BluetoothService.OnMessageReceived() {
                @Override
                public void process(int bytes, byte[] buffer) {
                    handler.obtainMessage(HANDLER_MESSAGE_GET, bytes, -1, buffer)
                            .sendToTarget();
                }
            });

            try {
                btService.initBtAdapter();
            } catch (BluetoothService.BtUnavailableException e) {
                Toast.makeText(ConnectionGame.this,
                        R.string.bluetooth_absent, Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            if (!btService.getBluetoothAdapter().isEnabled()) {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                        REQUEST_ENABLE_BT);
            } else if (!btService.isConnected()) {
                startActivityForResult(new Intent(ConnectionGame.this, DeviceChooser.class),
                        REQUEST_CONNECT);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            btService = null;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CONNECT:
                switch (resultCode) {
                    case RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
            case REQUEST_ENABLE_BT:
                switch (resultCode) {
                    case RESULT_OK:
                        if (!btService.isConnected()) {
                            startActivityForResult(new Intent(ConnectionGame.this, DeviceChooser.class),
                                    REQUEST_CONNECT);
                        }
                        break;

                    default:
                        finish();
                        break;
                }
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btService.write(editText.getText().toString().getBytes());
            arrayAdapter.add("Me: " + editText.getText().toString());
            editText.getText().clear();
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            byte[] bytes = (byte[]) msg.obj;
            boolean flag = true;
            for (int i = 0; i < 4; i++){
                if (bytes[i] != stopMessage.getBytes()[i]){
                    flag = false;
                }
            }
            if (flag){
                toDeviceChooser();
                arrayAdapter.clear();
            }

            arrayAdapter.add(btService.getBluetoothSocket().getRemoteDevice().getName() + ": " +
                    new String((byte[]) msg.obj, 0, msg.arg1));
        }
    };
}
