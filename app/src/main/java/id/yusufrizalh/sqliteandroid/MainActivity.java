package id.yusufrizalh.sqliteandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SqliteDatabase myDatabase;
    private RecyclerView contactView;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactView = findViewById(R.id.contact_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);

        myDatabase = new SqliteDatabase(this);
        ArrayList<Contacts> allContacts = myDatabase.listContacts();
        // melihat semua isi tabel


        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perintah memunculkan form add contact
                addContactDialog();
            }
        });
    }

    private void addContactDialog() {
        // memanggil add_contact.xml
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_contacts, null);
        final EditText nameField = subView.findViewById(R.id.edit_contact_name);
        final EditText noField = subView.findViewById(R.id.edit_contact_phone);

        // masukkan add_contact.xml kedalam Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Contact");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // perintah untuk insert ke tabel
                final String name = nameField.getText().toString();
                final String phone = noField.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(MainActivity.this,
                            "Tidak boleh ada yang kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    Contacts newContact = new Contacts(name, phone);
                    myDatabase.addContacts(newContact);
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDatabase != null) {
            myDatabase.close();
        }
    }
}