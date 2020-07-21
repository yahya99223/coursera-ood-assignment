package com.example.sharingapp;

import android.content.Context;
import android.telecom.StatusHints;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME;
    public ContactList()
    {
        contacts= new ArrayList<Contact>();
    }
    public void setContacts(ArrayList<Contact> contacts)
    {
        this.contacts=contacts;
    }
    public ArrayList<Contact> getContacts()
    {
        return this.contacts;
    }
    public ArrayList<String> getAllUsernames(){
        ArrayList<String> usernames= new ArrayList<String>();
        for (Contact contact:this.contacts) {
            usernames.add(contact.getUsername());
        }
        return usernames;
    }
    public void addContact(Contact contact)
    {
        this.contacts.add(contact);
    }
    public void deleteContact(Contact contact)
    {
        this.contacts.remove(contact);
    }
    public Contact getContact(Integer index)
    {
       return this.contacts.get(index);
    }
    public Integer getSize()
    {
        return this.contacts.size();
    }
    public Integer getIndex(Contact contact)
    {
        return this.contacts.indexOf(contact);
    }
    public Boolean hasContact(Contact contact){
        return this.contacts.contains(contact);
    }
    public Contact getContactByUsername(String username)
    {
        for (Contact contact:this.contacts) {
           if(contact.getUsername()==username)
               return contact;
        }
        return null;
    }
    public void loadContacts(Context context)
    {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username) {
        for (Contact u : contacts) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
