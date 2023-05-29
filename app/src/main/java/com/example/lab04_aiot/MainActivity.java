package com.example.lab04_aiot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "tutor_channel";
    private static final String CHANNEL_ID2 = "trabajador_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int NOTIFICATION_ID2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Crear canales de Notificacion*/
        createNotificationChannel("Tutor Channel", "Channel for tutor notifications", CHANNEL_ID);
        createNotificationChannel("Trabajador Channel", "Channel for trabajador notifications", CHANNEL_ID2);

        Button tutorButton = findViewById(R.id.tutorButton);
        Button trabajadorButton = findViewById(R.id.b_trabajador);
        tutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTutorNotification();
                Intent intent = new Intent(MainActivity.this, FlujoTutor.class);
                startActivity(intent);
            }
        });
        //*Boton Trabajador*/
        trabajadorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTrabajadorNotification();
                Intent intent = new Intent(MainActivity.this, FlujoTrabajador.class);
                startActivity(intent);
            }
        });


    }


    public void trabajador(View view) {
        Intent i = new Intent(this, FlujoTrabajador.class);
        startActivity(i);
    }

    private void createNotificationChannel(CharSequence name, String description, String channel_id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            askPermission();
        }
    }

    private void showTutorNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Está entrando en modo Tutor")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void showTrabajadorNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID2)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Está entrando en modo Empleado")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID2, builder.build());
        }
    }

    public void askPermission(){
        //android.os.Build.VERSION_CODES.TIRAMISU == 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }
}