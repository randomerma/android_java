package com.example.practice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.geo.Projection;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;


import java.io.IOException;

import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity {
    private MapView mapview;
    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    private static final double DESIRED_ACCURACY = 0;
    private static final long MINIMAL_TIME = 1000;
    private static final double MINIMAL_DISTANCE = 1;
    private static final boolean USE_IN_BACKGROUND = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout rootCoordinatorLayout;
    private LocationManager locationManager;
    private LocationListener myLocationListener;
    private Point myLocation;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar ab = getActionBar();
//        ab.setBackgroundDrawable(new ColorDrawable(0xffFF00FF));
        MapKitFactory.setApiKey("f21b5a94-7944-4eec-a01d-a8cf9be04c16");
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(0xff9CC249));


        // Укажите имя Activity вместо map.
        setContentView(R.layout.activity_main);

        mapview = (MapView)

                findViewById(R.id.mapview);

        locationManager = MapKitFactory.getInstance().createLocationManager();

        mapview.getMap().

                move(
                        new CameraPosition(new Point(56.0184, 92.8672), 11.0f, 0.0f, 0.0f),
                        new

                                Animation(Animation.Type.SMOOTH, 0),
                        null);

        mapview.getMap().addInputListener(new InputListener() {
            @Override
            public void onMapTap(@NonNull Map map, @NonNull Point point) {
                mapview.getMap().getMapObjects().clear();
                Log.d("MAP_TAG", "point: " + point.getLatitude() + ", " +
                        point.getLongitude());

                mapview.getMap().getMapObjects().addPlacemark(new Point(point.getLatitude(),
                        point.getLongitude()));
            }

            @Override
            public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

            }
            public Bitmap drawSimpleBitmap(String number) {
                int picSize = 10;
                Bitmap bitmap = Bitmap.createBitmap(picSize, picSize*2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                // отрисовка плейсмарка
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(picSize / 2, picSize / 2, picSize / 2, paint);
                // отрисовка текста
                paint.setColor(Color.WHITE);
                paint.setAntiAlias(true);
                paint.setTextSize(10);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(number, picSize / 2,
                        picSize / 2 - ((paint.descent() + paint.ascent()) / 2), paint);
                return bitmap;
            }

        });

    }


    @Override
    protected void onStop() {
        locationManager.unsubscribe(myLocationListener);
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
        subscribeToLocationUpdate();
    }

    public void showDialogAnecdote(View v) {
        Anecdote dialog = new Anecdote();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void showDialogListTasks(View v) {
        Intent myIntent = new Intent(this, MainActivity2.class);
        startActivity(myIntent);
    }
    public void onFabCurrentLocationClick(View view) {
        if (myLocation == null) {
            return;
        }

        moveCamera(myLocation, 18);
    }

    private void subscribeToLocationUpdate() {
        if (locationManager != null && myLocationListener != null) {
            locationManager.subscribeForLocationUpdates(0, MINIMAL_TIME, MINIMAL_DISTANCE,
                    USE_IN_BACKGROUND, FilteringMode.OFF, myLocationListener);
        }
    }

    private void moveCamera(Point point, float zoom) {
        mapview.getMap().move(
                new CameraPosition(point, zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }

}