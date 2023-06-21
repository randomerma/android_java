package com.example.practice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.practice2.DatabaseHelper;
import com.example.practice2.MainActivity3;
import com.example.practice2.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        ArrayList<HashMap<String, Object>> notes = new ArrayList<HashMap<String, Object>>();

        // Список параметров конкретного клиента
        HashMap<String, Object> note;

        // Отправляем запрос в БД
        Cursor cursor = db.rawQuery("SELECT * FROM notes", null);
        boolean temp = cursor.moveToNext();

        // Пробегаем по всем клиентам
        while (temp) {
            note = new HashMap<String, Object>();

            // Заполняем клиента
            note.put("id",  cursor.getString(0));
            note.put("Record",  cursor.getString(1));

            // Закидываем клиента в список клиентов
            notes.add(note);

            // Переходим к следующему клиенту
            temp = cursor.moveToNext();
        }
        cursor.close();


        // определяем, какие столбцы из курсора будут выводиться в ListView

        String[] from = { "id", "Record"};
        int[] to = { R.id.textView, R.id.textView2};
        // создаем адаптер, передаем в него курсор
        SimpleAdapter adapter = new SimpleAdapter(this, notes,
                R.layout.adapter_item, from, to);
        userList.setAdapter(adapter);
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
    }
}