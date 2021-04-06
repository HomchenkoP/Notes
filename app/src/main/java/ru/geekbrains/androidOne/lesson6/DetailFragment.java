package ru.geekbrains.androidOne.lesson6;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// 4. Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока:
//    если нажать на элемент списка в портретной ориентации — открывается новое окно,
//    если нажать в ландшафтной — окно открывается рядом.

public class DetailFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String ARG_NOTE = "note";
    private NotesModel note;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static DetailFragment newInstance(NotesModel note) {
        // создание
        DetailFragment fragment = new DetailFragment();
        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
        // Для создания меню во фрагменте
        setHasOptionsMenu(true);
        // Меняем заголовок
        getActivity().setTitle(R.string.notes);
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Получаем головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        if (note != null) {

            // Находим в контейнере элемент заголовка
            MaterialTextView title = view.findViewById(R.id.title);
            title.setText(note.getTitle());

            // Находим в контейнере элемент содержимого
            MaterialTextView content = view.findViewById(R.id.content);
            content.setText(note.getContent());

            if (note.getMemoDate() != null) {
                // Находим в контейнере элемент memoDate
                MaterialTextView memoDate = view.findViewById(R.id.memoDate);
                memoDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getMemoDate()));
            }
            if (note.getCreateDate() != null) {
                // Находим в контейнере элемент createDate
                MaterialTextView createDate = view.findViewById(R.id.createDate);
                createDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getCreateDate()));
            }
        } else {
            Toast.makeText(getActivity(), "Вызов onCreateView с note == null.", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // В портретной ориентации скрываем меню поиска
            menu.findItem(R.id.action_search).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Обрабатываем выбор меню "Переслать"
        if (item.getItemId() == R.id.action_forward){
            Toast.makeText(getContext(), "TODO Открыть окно пересылки заметки", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}