package ru.geekbrains.androidOne.lesson11;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.androidOne.lesson6.R;

// 1. Создайте диалоговое окно с предупреждением перед удалением данных.

public class DeleteDialogFragment extends DialogFragment {

    public static interface DeleteDialogCaller {
        void onDialogResult(String result);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // макет диалога
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.fragment_delete_dialog, null);
        TextView message = contentView.findViewById(R.id.delete_dialog_message);
        message.setText("Удалить заметку?");
        // пара методов setTargetFragment()/getTargetFragment() "переживает" пересоздание активити
        DeleteDialogCaller dialogCaller = (DeleteDialogCaller) getTargetFragment();
        // билдер диалога
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.delete_dialog_title)
                //.setMessage("Удалить заметку?")
                .setView(contentView)
                .setPositiveButton(R.string.delete_dialog_pos_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        if (dialogCaller != null) {
                            dialogCaller.onDialogResult("DELETE");
                        }
                    }
                })
                .setNegativeButton(R.string.delete_dialog_neg_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        if (dialogCaller != null) {
                            dialogCaller.onDialogResult("CANCEL");
                        }
                    }
                });
        return builder.create();
    }
}
