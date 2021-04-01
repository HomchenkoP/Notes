package ru.geekbrains.androidOne.lesson9;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.androidOne.lesson6.NotesModel;

public class Publisher {
    // Все обозреватели
    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(NotesModel cardData) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
            unsubscribe(observer);
        }
    }

}
