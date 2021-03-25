package ru.geekbrains.androidOne.lesson6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

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

            Calendar calendar;

            calendar = note.getMemoDate();
            if (calendar != null) {
                // Находим в контейнере элемент memoDate
                MaterialTextView memoDate = view.findViewById(R.id.memoDate);
                memoDate.setText(new StringBuilder()
                        // Месяц отсчитывается с 0, поэтому добавляем 1
                        .append(calendar.get(Calendar.DAY_OF_MONTH)).append(".")
                        .append(calendar.get(Calendar.MONTH) + 1).append(".")
                        .append(calendar.get(Calendar.YEAR)));
            }

            calendar = note.getCreateDate();
            if (calendar != null) {
                // Находим в контейнере элемент createDate
                MaterialTextView createDate = view.findViewById(R.id.createDate);
                createDate.setText(new StringBuilder()
                        // Месяц отсчитывается с 0, поэтому добавляем 1
                        .append(calendar.get(Calendar.DAY_OF_MONTH)).append(".")
                        .append(calendar.get(Calendar.MONTH) + 1).append(".")
                        .append(calendar.get(Calendar.YEAR)));
            }
        } else {
            Toast.makeText(getActivity(), "Вызов onCreateView с note == null.", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}