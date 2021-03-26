package ru.geekbrains.androidOne.lesson6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

// 1. ... Используйте подход Single Activity для отображения экранов.

public class MainActivity extends AppCompatActivity {

    // навигационное меню
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        // обработчик
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "onNavigationItemSelected", Toast.LENGTH_SHORT).show();
                int id = item.getItemId();
                if (navigateFragment(id)){
                    // закрываем шторку
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_notes:
                //addFragment(new NotesFragment());
                Toast.makeText(MainActivity.this, "TODO Открыть окно заметок", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                //addFragment(new AboutFragment());
                Toast.makeText(MainActivity.this, "TODO Открыть окно о приложении", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                //addFragment(new SettingsFragment());
                Toast.makeText(MainActivity.this, "TODO Открыть окно настроек", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    // обрабатываем нажатие системной кнопки "Назад"
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // если открыта шторка drawer, закрываем шторку
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Toolbar меню

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.activity_main_toolbar_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        // окно поиска
        SearchView searchView = (SearchView) search.getActionView();
        // обработчик
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "TODO Открыть окно заметок, содержащих '" + query + "'", Toast.LENGTH_SHORT).show();
                return false; // скрыть экранную клавиатуру
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}