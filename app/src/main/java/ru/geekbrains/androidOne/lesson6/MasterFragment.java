package ru.geekbrains.androidOne.lesson6;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

// 2. Создайте фрагмент для вывода этих данных.
// 1. ... Используйте подход Single Activity для отображения экранов.

public class MasterFragment extends Fragment {

    public static final String KEY_CURRENT_NOTE = "CurrentNote";
    private NotesModel currentNote; // Текущая позиция (выбранная заметка)
    private boolean isLandscape;

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список заголовков заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = view.findViewById(R.id.master_container);
        String[] titles = getResources().getStringArray(R.array.titles);

        // В цикле создаём элемент TextView, заполняем его значениями, и добавляем на экран.
        for(int i = 0; i < titles.length; i++) {
            MaterialTextView title = new MaterialTextView(getContext());
            title.setText(titles[i]);
            title.setTextSize(30);
            layoutView.addView(title);
            // добавляем обработчик касания
            final int fi = i;
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = new NotesModel(getResources().getStringArray(R.array.titles)[fi],
                            getResources().getStringArray(R.array.contents)[fi],
                            null, null);
                    showNote(currentNote);
                }
            });
        }
    }

    private void showNote(NotesModel currentNote) {
        if (isLandscape) {
            showNoteLand(currentNote);
        } else {
            showNotePort(currentNote);
        }
    }

    // Показать заметку в ландшафтной ориентации
    private void showNoteLand(NotesModel currentNote) {
        // Создаём новый фрагмент с текущей позицией
        DetailFragment detailFragment = DetailFragment.newInstance(currentNote);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail, detailFragment);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null); // отправить в стек обратного вызова
        fragmentTransaction.commit();
    }

    // Показать заметку в портретной ориентации.
    private void showNotePort(NotesModel currentNote) {
        // Создаём новый фрагмент с текущей позицией
        DetailFragment detailFragment = DetailFragment.newInstance(currentNote);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null); // отправить в стек обратного вызова
        fragmentTransaction.commit();
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(KEY_CURRENT_NOTE);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentNote = new NotesModel(getResources().getStringArray(R.array.titles)[0],
                    getResources().getStringArray(R.array.contents)[0],
                    null, null);
        }

        // Определение ориентации
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Если ландшафтная ориентация, то располагаем содержимое заметки рядом справа в другом фрагменте
        if (isLandscape) {
            showNoteLand(currentNote);
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(KEY_CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }
}