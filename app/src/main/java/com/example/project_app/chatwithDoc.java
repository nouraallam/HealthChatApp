package com.example.project_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class chatwithDoc extends AppCompatActivity {
    private DatabaseReference mMessagesRef;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private EditText mMessageEditText;
    private Button mSendButton;
    private String patientTelephone;
    private final String doctorEmail = "khaoula.itro@ump.ac.ma";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_doc);

        patientTelephone = getIntent().getStringExtra("patientTelephone");

        mMessagesRef = FirebaseDatabase.getInstance().getReference().child("messages");

        mRecyclerView = findViewById(R.id.activity_mentor_chat_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageAdapter(new ArrayList<>(), patientTelephone);
        mRecyclerView.setAdapter(mAdapter);

        mMessageEditText = findViewById(R.id.activity_mentor_chat_message_edit_text);
        mSendButton = findViewById(R.id.activity_mentor_chat_send_button);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        loadMessagesFromFirebase();
    }

    private void sendMessage() {
        String messageContent = mMessageEditText.getText().toString().trim();
        if (!messageContent.isEmpty()) {
            Message message = new Message(messageContent, patientTelephone, doctorEmail, System.currentTimeMillis(), true, 1);
            String messageId = mMessagesRef.push().getKey();
            if (messageId != null) {
                mMessagesRef.child(messageId).setValue(message);
            }
            mMessageEditText.setText("");
        }
    }

    private void loadMessagesFromFirebase() {
        mMessagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> messageList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null &&
                            ((message.getSenderId().equals(doctorEmail) && message.getRecipientId().equals(patientTelephone)) ||
                                    (message.getSenderId().equals(patientTelephone) && message.getRecipientId().equals(doctorEmail)))) {
                        message.setMessageType(message.getSenderId().equals(patientTelephone) ? 1 : 0);
                        messageList.add(message);
                    }
                }
                mAdapter.setMessageList(messageList);
                mRecyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}