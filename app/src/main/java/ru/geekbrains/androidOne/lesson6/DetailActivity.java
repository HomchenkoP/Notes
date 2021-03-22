package ru.geekbrains.androidOne.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

// 4. Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока:
//    если нажать на элемент списка в портретной ориентации — открывается новое окно,
//    если нажать в ландшафтной — окно открывается рядом.

// Эта activity для показа в портретной ориентации
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // Если эта activity запускается первый раз,
            // то перенаправим параметр фрагменту
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());
            // Добавим фрагмент на activity
            // Чтобы программно вставить фрагмент, надо получить «Менеджер фрагментов», затем открыть транзакцию, вставить макет и закрыть транзакцию.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailFragment).commit();
        }
    }
}