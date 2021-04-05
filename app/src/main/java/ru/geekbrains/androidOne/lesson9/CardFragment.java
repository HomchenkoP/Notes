package ru.geekbrains.androidOne.lesson9;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import ru.geekbrains.androidOne.lesson6.MainActivity;
import ru.geekbrains.androidOne.lesson6.NotesModel;
import ru.geekbrains.androidOne.lesson6.R;

// 1. Сделайте фрагмент добавления и редактирования данных, если вы ещё не сделали его.

public class CardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "cardData";

    private NotesModel cardData;
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText content;
    private DatePicker datePicker;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static CardFragment newInstance(NotesModel cardData) {
        // создание
        CardFragment fragment = new CardFragment();
        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
        // Меняем заголовок
        getActivity().setTitle(getString(R.string.cardData));
    }

    // фрагмент прикреплен к активити
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views
    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(cardData);
    }

    private NotesModel collectCardData() {
        String title = this.title.getText().toString();
        String content = this.content.getText().toString();
        Date MemoDate = getDateFromDatePicker();
        if (cardData != null){
            NotesModel answer;
            answer = new NotesModel(title, content, MemoDate, Calendar.getInstance().getTime());
            answer.setId(cardData.getId());
            return answer;
        } else {
            return new NotesModel(title, content, null, null);
        }

    }

    // Получение даты из DatePicker
    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        calendar.set(Calendar.MONTH, this.datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return calendar.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        content = view.findViewById(R.id.inputContent);
        datePicker = view.findViewById(R.id.inputMemoDate);
    }

    private void populateView() {
        title.setText(cardData.getTitle());
        content.setText(cardData.getContent());
        initDatePicker(cardData.getMemoDate());
    }

    // Установка даты в DatePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}
