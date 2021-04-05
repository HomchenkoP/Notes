package ru.geekbrains.androidOne.lesson8;

import android.content.res.Resources;

import java.util.ArrayList;

import ru.geekbrains.androidOne.lesson10.CardsSourceResponse;
import ru.geekbrains.androidOne.lesson6.NotesModel;
import ru.geekbrains.androidOne.lesson6.R;

// 3. Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка.

public class CardsSourceImpl implements CardsSource {
    private ArrayList<NotesModel> dataSource;
    private Resources resources;    // ресурсы приложения

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardsSource init(CardsSourceResponse cardsSourceResponse){
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.titles);
        // строки описаний из ресурсов
        String[] contents = resources.getStringArray(R.array.contents);
        // заполнение источника данных
        for (int i = 0; i < contents.length; i++) {
            dataSource.add(new NotesModel(titles[i], contents[i], null, null));
        }

        // информируем, что данные проинициализированы и готовы
        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }

        return this;
    }

    public NotesModel getCardData(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    @Override
    public void addCardData(NotesModel cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void updateCardData(int position, NotesModel cardData) {
        dataSource.set(position, cardData);
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }
}
