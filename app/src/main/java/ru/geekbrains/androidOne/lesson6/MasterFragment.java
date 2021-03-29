package ru.geekbrains.androidOne.lesson6;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.geekbrains.androidOne.lesson8.CardsSource;
import ru.geekbrains.androidOne.lesson8.CardsSourceImpl;
import ru.geekbrains.androidOne.lesson8.NotesAdapter;

// 2. Создайте фрагмент для вывода этих данных.
// 1. ... Используйте подход Single Activity для отображения экранов.

public class MasterFragment extends Fragment {

    public static final String KEY_CURRENT_NOTE = "CurrentNote";
    private NotesModel currentNote; // Текущая позиция (выбранная заметка)
    private boolean isLandscape;

    NotesAdapter adapter;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static MasterFragment newInstance() {
        return new MasterFragment();
    }

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Получаем головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        // Находим в контейнере элемент RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        // Получим источник данных для списка
        CardsSource data = new CardsSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardsSource data){
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);
        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Установим адаптер
        adapter = new NotesAdapter(data);
        recyclerView.setAdapter(adapter);
        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        // Установим слушателя
        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
                showNote(adapter.getDataSource().getCardData(position));
            }
        });
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFloatingActionButton(view);
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

    private void initFloatingActionButton(View view) {
        view.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "TODO Открыть окно добавления новой заметки", Toast.LENGTH_SHORT).show();
            }
        });
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