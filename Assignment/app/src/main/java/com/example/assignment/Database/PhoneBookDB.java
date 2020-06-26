package com.example.assignment.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignment.Dao.ContactDao;
import com.example.assignment.Entities.Contact;

@Database(
        entities = {Contact.class},
        version = 1,
        exportSchema = false
)

public abstract class PhoneBookDB extends RoomDatabase {

    public abstract ContactDao contactDao();

    // static variable single_instance of type Singleton
    private static PhoneBookDB phoneBookDB;

    public static PhoneBookDB getInstance(final Context context) {
        if(phoneBookDB == null) {
            phoneBookDB = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PhoneBookDB.class, "contact.db").allowMainThreadQueries().build();
        }

        return phoneBookDB;
    }

    public static void initializeDb(final Context context) {
        PhoneBookDB db = getInstance(context);

        // add data to table contact
        if (db.contactDao().getAllContacts().size() == 0) {
            phoneBookDB.contactDao().insertContact(
                    new Contact("Will Smith", "wsmith@gmail.com", "0422570999", "12/08/1992"),
                    new Contact("Al Pacino", "alpacino@gmail.com", "0422624899", "13/01/1998"),
                    new Contact("Marlon Brando", "mbrando@gmail.com", "0422570762", "02/08/1999"),
                    new Contact("Dwayne Johnson", "djohnson@gmail.com", "0422376646", "19/07/1995"),
                    new Contact("Ben Stiller", "bstiller@gmail.com", "0422532249", "01/04/1982"),
                    new Contact("Leonardo DiCaprio", "ldicaprio@gmail.com", "0424321799", "24/08/1988"),
                    new Contact("Robert De Niro", "rdeniro@gmail.com", "0422332898", "12/11/1991"),
                    new Contact("Robert Downey, Jr", "rdowneyjr@gmail.com", "0422544323", "04/06/1984"),
                    new Contact("Tom Cruise", "tcruise@gmail.com", "0424977424", "27/09/2000"),
                    new Contact("Sylvester Stallone", "sstallone@gmail.com", "0244397828", "30/09/1994"),
                    new Contact("Arnold Schwarzenegger", "aschwarzenegger@gmail.com", "0422711392", "29/05/1999"),
                    new Contact("Robert Pattinson", "rpattinson@gmail.com", "0424999869", "09/03/2004"),
                    new Contact("Jake Gyllenhaal", "jgyllenhaal@gmail.com", "0423984564", "12/11/1972"),
                    new Contact("Vin Diesel", "vdiesel@gmail.com", "0422133000", "10/01/1984"),
                    new Contact("Morgan Freeman", "mfreeman@gmail.com", "0422744920", "09/04/2002"),
                    new Contact("Brad Pitt", "bpitt@gmail.com", "0420741140", "29/08/2001"),
                    new Contact("Matt Damon", "mdamon@gmail.com", "0421741856", "28/09/2000"),
                    new Contact("Tom Hanks", "thanks@gmail.com", "0422742852", "12/09/1999"),
                    new Contact("Johnny Depp", "jdepp@gmail.com", "0421410082", "09/02/1979"),
                    new Contact("Bruce Willis", "bwillis@gmail.com", "0422329949", "31/05/1998"),
                    new Contact("Samuel L. Jackson", "sljackson@gmail.com", "0421933631", "17/05/1989"),
                    new Contact("George Clooney", "gclooney@gmail.com", "0422512986", "04/04/1997"),
                    new Contact("Russell Crowe", "rcrowe@gmail.com", "0422570333", "12/10/1988"),
                    new Contact("Denzel Washington", "dwashington@gmail.com", "0422514787", "17/09/1982"),
                    new Contact("Joaquin Phoenix", "jphoenix@gmail.com", "0422414727", "13/11/1994")
            );
        }
    }
}
