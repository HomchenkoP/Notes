package ru.geekbrains.androidOne.lesson6;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.textview.MaterialTextView;

// 2. Создайте фрагмент для вывода этих данных.

public class MasterFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private int currentPosition = 0; // Текущая позиция (выбранная заметка)
    private boolean isLandscape;

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить содержимое заметки рядом справа в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentPosition = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        // Если можно, то сделаем это
        if (isLandscape) {
            showNoteLand(0);
        }
    }

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
            // добавляем обработчик касания
            final int fi = i;
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = fi;
                    showNote(currentPosition);
                }
            });
        }
    }

    private void showNote(int index) {
        if (isLandscape) {
            showNoteLand(index);
        } else {
            showNotePort(index);
        }
    }

    // Показать заметку в ландшафтной ориентации
    private void showNoteLand(int index) {
        // Создаём новый фрагмент с текущей позицией
        DetailFragment detailFragment = DetailFragment.newInstance(index);
        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail, detailFragment);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    // Показать заметку в портретной ориентации.
    private void showNotePort(int index) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        // и передадим туда параметры
        intent.putExtra(DetailFragment.ARG_INDEX, index);
        startActivity(intent);
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentPosition);
        super.onSaveInstanceState(outState);
    }
}