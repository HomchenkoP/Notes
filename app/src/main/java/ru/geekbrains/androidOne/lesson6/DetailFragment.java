package ru.geekbrains.androidOne.lesson6;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

// 4. Добавьте фрагмент, в котором открывается заметка. По аналогии с примером из урока:
//    если нажать на элемент списка в портретной ориентации — открывается новое окно,
//    если нажать в ландшафтной — окно открывается рядом.

public class DetailFragment extends Fragment {

    // the fragment initialization parameters
    protected static final String ARG_INDEX = "index";
    private int index;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static DetailFragment newInstance(int index) {
        // создание
        DetailFragment fragment = new DetailFragment();
        // Передача параметра
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Получаем головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Находим в контейнере элемент заголовка
        MaterialTextView title = view.findViewById(R.id.title);
        // Получаем из ресурсов массив заголовков
        String[] titles = getResources().getStringArray(R.array.titles);
        // Выбрать по индексу подходящий
        title.setText(titles[index]);

        // Находим в контейнере элемент содержимого
        MaterialTextView content = view.findViewById(R.id.content);
        // Получаем из ресурсов массив содержимого
        String[] contents = getResources().getStringArray(R.array.contents);
        // Выбрать по индексу подходящий
        content.setText(contents[index]);

        return view;
    }
}