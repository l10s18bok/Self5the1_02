package l10s18bok.naver.com.self5the1_0.MyMenu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import l10s18bok.naver.com.self5the1_0.R;

/**
 * Created by pp on 2017-12-22.
 */

public class MyInputDialog extends DialogFragment {

    DialogCommunicator dialogCommunicator;

    private String title, message, hint;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dialogCommunicator = (DialogCommunicator) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.input_dialog,null);
        final EditText dialogEdit = view.findViewById(R.id.input_edit);
        dialogEdit.setHint(hint);


        builder.setView(view).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        })
        .setPositiveButton("학인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogCommunicator.onDialogMessage(dialogEdit.getText().toString().trim());
                dismiss();

            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

        interface DialogCommunicator {
        public void onDialogMessage(String message);
        }
}
