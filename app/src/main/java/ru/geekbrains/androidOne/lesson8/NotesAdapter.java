package ru.geekbrains.androidOne.lesson8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.geekbrains.androidOne.lesson6.NotesModel;
import ru.geekbrains.androidOne.lesson6.R;

// 1. Создайте список ваших заметок.

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private CardsSource dataSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;  // Слушатель будет устанавливаться извне
    private int menuPosition;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(CardsSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View view = LayoutInflater.from(viewGroup.getContext())
                   .inflate(R.layout.card_item, viewGroup, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(view);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        viewHolder.setData(dataSource.getCardData(i));
    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Интерфейс слушателя нажатий
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView title;
        private MaterialTextView content;
        private MaterialTextView memoDate;
        private MaterialTextView createDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            memoDate = itemView.findViewById(R.id.memoDate);
            createDate = itemView.findViewById(R.id.createDate);

            // контекстное меню
            registerContextMenu(itemView);

            // Обработчик нажатий на этом ViewHolder
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void setData(NotesModel note){
            title.setText(note.getTitle());
            content.setText(note.getContent());
//            if (note.getMemoDate() != null) {
//                memoDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getMemoDate()));
//            }
//            if (note.getCreateDate() != null) {
//                createDate.setText(new SimpleDateFormat("dd-MM-yy").format(note.getCreateDate()));
//            }
        }
    }

    public CardsSource getDataSource() {
        return dataSource;
    }

    public int getMenuPosition() {
        return menuPosition;
    }
}