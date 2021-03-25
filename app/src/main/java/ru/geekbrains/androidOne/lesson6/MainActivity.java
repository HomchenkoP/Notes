package ru.geekbrains.androidOne.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

// 1. ... Используйте подход Single Activity для отображения экранов.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // в ландшафтной ориентации
            return;
        } else {
            // в портретной ориентации
            if (savedInstanceState == null) {
                // Если эта activity запускается первый раз
                MasterFragment masterFragment = new MasterFragment();
                // Добавим фрагмент на activity
                // Чтобы программно вставить фрагмент, надо получить «Менеджер фрагментов», затем открыть транзакцию, вставить макет и закрыть транзакцию.
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, masterFragment)
                        //.addToBackStack(null)
                        .commit();
            }
        }
    }
}