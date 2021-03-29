package ru.geekbrains.androidOne.lesson8;

import ru.geekbrains.androidOne.lesson6.NotesModel;

// 3. Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка.

public interface CardsSource {
    NotesModel getCardData(int position);
    int size();
}
