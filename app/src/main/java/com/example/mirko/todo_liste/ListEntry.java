package com.example.mirko.todo_liste;

import java.util.UUID;

/**
 * Created by mirko on 10.03.18.
 */

public class ListEntry {

    private String id;
    private String text;


    public ListEntry(String text) {
        this();
        this.setText(text);
    }

    public ListEntry() {
        this.setId(UUID.randomUUID().toString());
        this.setText("");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id != null){
            this.id = id;
        }else {
            if(this.getId() == null){
                this.id = UUID.randomUUID().toString();
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(text != null){
            this.text = text;
        }else {
            if(this.getText() == null){
                this.text = "";
            }
        }
    }

    @Override
    public String toString() {
        return this.getText();
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListEntry listEntry = (ListEntry) o;

        return id.equals(listEntry.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }*/
}
