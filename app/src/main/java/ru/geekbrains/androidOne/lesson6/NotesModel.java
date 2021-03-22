package ru.geekbrains.androidOne.lesson6;

// 1. Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.TimeZone;

public class NotesModel implements Parcelable {

    private String title;
    private String content;
    private Calendar memoDate;
    private Calendar createDate;

    public NotesModel(String title, String content, Calendar memoDate, Calendar createDate) {
        this.title = title;
        this.content = content;
        this.memoDate = memoDate;
        this.createDate = createDate;
    }

    protected NotesModel(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        // значение memoDate собираем из миллисекунд и таймзоны
        this.memoDate = new Calendar(TimeZone.getTimeZone(in.readString()));
        this.memoDate.setTimeInMillis(in.readLong());
        // значение createDate собираем из миллисекунд и таймзоны
        this.createDate = new Calendar(TimeZone.getTimeZone(in.readString()));
        this.createDate.setTimeInMillis(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        // значение memoDate сохраняем по частям - миллисекунды и таймзону отдельно
        dest.writeString(memoDate.getTimeZone().getID());
        dest.writeLong(memoDate.getTimeInMillis());
        // значение createDate сохраняем по частям - миллисекунды и таймзону отдельно
        dest.writeString(createDate.getTimeZone().getID());
        dest.writeLong(createDate.getTimeInMillis());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotesModel> CREATOR = new Creator<NotesModel>() {
        @Override
        public NotesModel createFromParcel(Parcel in) {
            return new NotesModel(in);
        }

        @Override
        public NotesModel[] newArray(int size) {
            return new NotesModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Calendar getMemoDate() {
        return memoDate;
    }

    public Calendar getCreateDate() {
        return createDate;
    }
}
