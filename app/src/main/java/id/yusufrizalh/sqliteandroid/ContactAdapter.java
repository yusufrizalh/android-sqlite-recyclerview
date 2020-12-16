package id.yusufrizalh.sqliteandroid;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private Context context;
    private ArrayList<Contacts> listContacts;
    private ArrayList<Contacts> myArrayList;
    private SqliteDatabase myDatabase;

    public ContactAdapter(Context context, ArrayList<Contacts> listContacts) {
        this.context = context;
        this.listContacts = listContacts;
        this.myArrayList = listContacts;
        myDatabase = new SqliteDatabase(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final Contacts contacts = listContacts.get(position);
        holder.txt_contact_name.setText(contacts.getName());
        holder.txt_contact_phone.setText(contacts.getPhoneNumber());

        holder.img_edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perintah untuk memunculkan form edit contact
                editContactDialog(contacts);
            }
        });

        holder.img_delete_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabase.deleteContact(contacts.getId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    private void editContactDialog(final Contacts contacts) {
        // memanggil add_contact.xml
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_contacts, null);
        final EditText nameField = subView.findViewById(R.id.edit_contact_name);
        final EditText noField = subView.findViewById(R.id.edit_contact_phone);

        if (contacts != null) {
            nameField.setText(contacts.getName());
            noField.setText(String.valueOf(contacts.getPhoneNumber()));
        }

        // masukkan add_contact.xml kedalam Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Contact");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // perintah untuk insert ke tabel
                final String name = nameField.getText().toString();
                final String phone = noField.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(context,
                            "Tidak boleh ada yang kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    myDatabase.updateContacts(
                            new Contacts(Objects.requireNonNull(contacts).getId(), name, phone));
                    ((Activity) context).finish();
                    context.startActivity(((Activity) context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }
}
