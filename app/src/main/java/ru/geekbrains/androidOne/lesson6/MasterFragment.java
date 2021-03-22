package ru.geekbrains.androidOne.lesson6;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.textview.MaterialTextView;

// 2. Создайте фрагмент для вывода этих данных.

public class MasterFragment extends Fragment {

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
        //NestedScrollView layoutView = (NestedScrollView)view;
        LinearLayout layoutView = (LinearLayout)view;
        String[] titles = getResources().getStringArray(R.array.titles);

        // В цикле создаём элемент TextView, заполняем его значениями, и добавляем на экран.
        for(int i=0; i < titles.length; i++){
            MaterialTextView title = new MaterialTextView(getContext());
            title.setText(titles[i]);
            title.setTextSize(30);
            layoutView.addView(title);
        }
    }
}