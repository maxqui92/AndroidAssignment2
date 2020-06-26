package com.example.assignment.Library;

import com.example.assignment.Entities.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class MyHash {
    private ArrayList<Contact> hashTable[];

    public MyHash() {

        hashTable = new ArrayList[27];
        for(int i = 0; i < hashTable.length; i++){
            hashTable[i] = new ArrayList<>();
        }
    }

    // re-link all the elements to a linked list.
    // note 1: elements are not copied
    // note 2: the hashtable links are still there.
    //
    // Example:
    // Hash table view
    //      A -> Alan -> Alex -> Amahli
    //      B -> Bob
    //      C -> Cali -> Cindy
    // Linked list view
    //      Alan -> Alex -> Amahli -> Bob -> Cali -> Cindy
    public ArrayList<Contact> toList(boolean reverse) {
        ArrayList<Contact> c = new ArrayList ();
        for(int i = 0; i < hashTable.length; i++){
            for(int j = 0; j < hashTable[i].size(); j++){
                c.add(hashTable[i].get(j));
            }
        }
        if(reverse == true) {
            Collections.reverse(c);
        }
        return c;
    }

    // for search
    public ArrayList<Contact> shortList(String wantedStr) {
        ArrayList<Contact> c = new ArrayList ();
        for(int i = 0; i < hashTable.length; i++){
            for(int j = 0; j < hashTable[i].size(); j++){
                if (Pattern.compile(Pattern.quote(wantedStr), Pattern.CASE_INSENSITIVE).matcher(hashTable[i].get(j).getfName()).find()) {
                    c.add(hashTable[i].get(j));
                }
            }
        }
        return c;
    }

    // for a specific key, this function calculate the offset of the element
    // who's first letter start with "key" in the arraylist.
    // for example. in the following list, if key == c/C, offset = 3 + 1 = 4.
    // meaning that the first name in C list has an index of 4.
    // A -> Alan -> Alex -> Amahli
    // B -> Bob
    // C -> Cali -> Cindy
    public int calcOffsetByKey(int k) {
        int offset = 0;
        if (k < 0 || k >= hashTable.length) {
            offset = 0;
        } else {
            for(int i = 0; i < k; i++){
                // offset is the sum of the size of all previous list.
                offset = offset + hashTable[i].size();
            }
        }
        return offset;
    }

    // build hash table. use arraylist to resolve collision.
    // sort all arraylists
    public void buildHashTable(ArrayList<Contact> list) {
        if(list == null) {
            return;
        }
        for(int i = 0; i < list.size(); i++){
            Contact c = list.get(i);
            int hashTableIndex = hash(c.getfName());
            hashTable[hashTableIndex].add(c);
        }

        for(int i = 0; i < hashTable.length; i++){
            Collections.sort(hashTable[i]);
        }
        return;
    }

    // hash the first char of a string
    // first char: # A B C D E F G H I J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z
    // hash key:   0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
    // input:  string
    // output: hash key
    private int hash(String s) {
        // get the first char and convert to uppercase to make it case insensitive.
        char c = s.toUpperCase().charAt(0);
        // get ascii value of the first char
        int asciiValue = (int)c;
        if(asciiValue >= 65 && asciiValue <= 90) {
            asciiValue = asciiValue - 64;
        } else {
            asciiValue = 0;
        }
        return asciiValue;
    }



    public void dump(){
        for(int i = 0; i < hashTable.length; i++){

            System.out.print("[" + i + "] ");
            for(int j = 0; j < hashTable[i].size(); j++){
                System.out.print("->(" + hashTable[i].get(j).getfName() + " / " + hashTable[i].get(j).getPhone() + ")");
            }
            System.out.print("\n");
        }
    }
}
