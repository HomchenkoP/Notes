package ru.geekbrains.androidOne.lesson11;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.androidOne.lesson6.NotesModel;
import ru.geekbrains.androidOne.lesson6.R;
import ru.geekbrains.androidOne.lesson9.CardFragment;

// 1. Создайте диалоговое окно с предупреждением перед удалением данных.

public class DeleteDialogFragment extends DialogFragment {

    private static final String ARG_NOTE_ID = "noteId";

    private String noteId;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static DeleteDialogFragment newInstance(String noteId) {
        // создание
        DeleteDialogFragment dialogFragment = new DeleteDialogFragment();
        // Передача параметра
        Bundle args = new Bundle();
        args.putString(ARG_NOTE_ID, noteId);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteId = getArguments().getString(ARG_NOTE_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // макет диалога
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.fragment_delete_dialog, null);
        TextView message = contentView.findViewById(R.id.delete_dialog_message);
        message.setText("Удалить \"" + noteId + "\"?");
        // билдер диалога
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.delete_dialog_title)
                //.setMessage("Удалить заметку?")
                .setView(contentView)
                .setPositiveButton(R.string.delete_dialog_pos_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // дополнительные данные из диалога могут быть предены через Intent/Bundle
                        Intent intent = new Intent();
                        // пара методов setTargetFragment()/getTargetFragment() "переживает" пересоздание активити
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.delete_dialog_neg_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // дополнительные данные из диалога могут быть предены через Intent/Bundle
                        Intent intent = new Intent();
                        // пара методов setTargetFragment()/getTargetFragment() "переживает" пересоздание активити
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                        dismiss();
                    }
                });
        return builder.create();
    }
}
