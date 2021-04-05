package ru.geekbrains.androidOne.lesson10;

import ru.geekbrains.androidOne.lesson8.CardsSource;

// 1. Обеспечьте хранение данных приложения через Firestore.

public interface CardsSourceResponse {

    // Метод initialized() будет вызываться, когда данные проинициализируются и будут готовы.
    void initialized(CardsSource cardsData);

}
