package ru.geekbrains.androidOne.lesson6;

// 1. Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NotesModel implements Parcelable {

    private String id;       // идентификатор
    private String title;    // заголовок заметки
    private String content;  // содержимое заметки
    private Date memoDate;   // дата напоминания
    private Date createDate; // дата создания/изменения заметки

    public NotesModel(String title, String content, Date memoDate, Date createDate) {
        this.title = title;
        this.content = content;
        this.memoDate = memoDate;
        this.createDate = createDate;
    }

    protected NotesModel(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
//        this.memoDate = new Date(in.readLong());
//        this.createDate = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
//        dest.writeLong(memoDate.getTime());
//        dest.writeLong(createDate.getTime());
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getMemoDate() {
        return memoDate;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
