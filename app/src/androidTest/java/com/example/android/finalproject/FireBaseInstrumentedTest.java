package com.example.android.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class FireBaseInstrumentedTest {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference parentNodeReference = firebaseDatabase.getReference();

    int startingChildNum = 0;

    // Check for expected number of nodes
    @Test
    public void testNumNodes() throws Exception {
        final CountDownLatch writeSignal = new CountDownLatch(1);

        parentNodeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                writeSignal.countDown();
                startingChildNum++;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(10, TimeUnit.SECONDS);
        assertEquals(0, writeSignal.getCount());
    }

    int addedChildNum = 0;

    // Check that value can be updated at test node
    @Test
    public void testWriteData() throws InterruptedException {
        DatabaseReference testWriteNodeReference = firebaseDatabase.getReference("testWriteNodeKey");
        final CountDownLatch writeSignal = new CountDownLatch(1);

        testWriteNodeReference.setValue("testUpdateWrite").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                writeSignal.countDown();
            }
        });

        writeSignal.await(10, TimeUnit.SECONDS);
        assertEquals(0, writeSignal.getCount());

        parentNodeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("CHILD", dataSnapshot.toString());
                addedChildNum++;
                writeSignal.countDown();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        writeSignal.await(10, TimeUnit.SECONDS);
        assertEquals(0, writeSignal.getCount());

        int EXPECTED = startingChildNum++;
        assertEquals(EXPECTED, addedChildNum);
    }

    @Test
    public void testDeleteData() throws InterruptedException {
        DatabaseReference testDeleteNodeReference = firebaseDatabase.getReference("testWriteNodeKey");
        final CountDownLatch writeSignal = new CountDownLatch(1);

        testDeleteNodeReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                writeSignal.countDown();
            }
        });

        writeSignal.await(10, TimeUnit.SECONDS);
        assertEquals(0, writeSignal.getCount());

        int EXPECTED = startingChildNum--;
        assertEquals(EXPECTED, addedChildNum);

    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.android.finalproject", appContext.getPackageName());
    }
}
