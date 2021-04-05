package ru.geekbrains.androidOne.lesson10;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.geekbrains.androidOne.lesson6.NotesModel;
import ru.geekbrains.androidOne.lesson8.CardsSource;

// 1. Обеспечьте хранение данных приложения через Firestore.

public class CardsSourceFirebaseImpl implements CardsSource {

    private static final String CARDS_COLLECTION = "notes";
    private static final String TAG = "[CardsSourceFirebaseImpl]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    // Загружаемый список карточек
    private List<NotesModel> cardsData = new ArrayList<NotesModel>();

    @Override
    public CardsSource init(final CardsSourceResponse cardsSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Заголовок»
        collection.orderBy(CardDataMapping.Fields.TITLE, Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // При удачном считывании данных загрузим список карточек
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<NotesModel>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                NotesModel cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }
                            cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
                        } else {
                            //Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public NotesModel getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null){
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void addCardData(final NotesModel cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void updateCardData(int position, NotesModel cardData) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void deleteCardData(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

}
