package ru.geekbrains.androidOne.lesson10;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

import ru.geekbrains.androidOne.lesson6.NotesModel;

// 1. Обеспечьте хранение данных приложения через Firestore.

public class CardDataMapping {

    public static class Fields {

        public final static String TITLE = "title";
        public final static String CONTENT = "content";
        public final static String MEMODATE = "memodate";
        public final static String CREATEDATE = "createdate";
    }

    public static NotesModel toCardData(String id, Map<String, Object> doc) {

        Timestamp memoDate = (Timestamp) doc.get(Fields.MEMODATE);
        Timestamp createDate = (Timestamp) doc.get(Fields.CREATEDATE);
        NotesModel answer = new NotesModel((String) doc.get(Fields.TITLE),
                                           (String) doc.get(Fields.CONTENT),
//                                           memoDate.toDate(),
//                                           createDate.toDate());
                                            null, null);
        return answer;
    }

    public static Map<String, Object> toDocument(NotesModel cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.CONTENT, cardData.getContent());
//        answer.put(Fields.MEMODATE, cardData.getMemoDate());
//        answer.put(Fields.CREATEDATE, cardData.getCreateDate());
        return answer;
    }
}
