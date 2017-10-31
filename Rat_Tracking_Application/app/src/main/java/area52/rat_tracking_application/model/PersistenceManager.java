package area52.rat_tracking_application.model;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 2016d on 10/31/2017.
 */

public class PersistenceManager {
    public static final String RAT_REPORT_DATA_FILENAME = "ratReports.bin";
    public static final String USERS_DATA_FILENAME = "users.bin";

    /**
     * Singleton pattern, because I am an original human being
     */
    private static PersistenceManager instance = new PersistenceManager();

    public PersistenceManager() {}
    public static PersistenceManager getInstance() { return instance; }

    /**
     * Saves the given object inside the specified file using BinarySerialization. Code largely
     * based on Prof. Robert Water's example code
     * @param file The file the object will be saved to
     * @param objToBeSaved The object to be saved; must implement Serializable
     * @return true/false of if the file was saved successfully
     */
    public boolean saveBinary(File file, Object objToBeSaved) {
        boolean success = true;
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            // We basically can save our entire data model with one write, since this will follow
            // all the links and pointers to save everything.  Just save the top level object.
            out.writeObject(objToBeSaved);
            out.close();

        } catch (IOException e) {
            Log.e("PersistenceManager", "Error writing an entry from binary file",e);
            success = false;
        }
        return success;
    }

    public Object loadBinary(File file) {
        Object objFromFile = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            objFromFile = in.readObject();
            in.close();
        } catch (IOException e) {
            Log.e("PersistenceManager", "Error reading an entry from binary file",e);
        } catch (ClassNotFoundException e) {
            Log.e("PersistenceManager", "Error casting a class from the binary file",e);
        }

        return objFromFile;
    }
}
