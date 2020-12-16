package id.yusufrizalh.sqliteandroid;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactViewHolder extends RecyclerView.ViewHolder {
    // pola 1: inisialisasi komponen
    TextView txt_contact_name, txt_contact_phone;
    ImageView img_edit_contact, img_delete_contact;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        // pola 2: mengenali komponen
        txt_contact_name = itemView.findViewById(R.id.txt_contact_name);
        txt_contact_phone = itemView.findViewById(R.id.txt_contact_phone);
        img_edit_contact = itemView.findViewById(R.id.img_edit_contact);
        img_delete_contact = itemView.findViewById(R.id.img_delete_contact);
    }
}
