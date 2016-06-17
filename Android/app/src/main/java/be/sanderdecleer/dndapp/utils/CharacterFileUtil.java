package be.sanderdecleer.dndapp.utils;

import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import be.sanderdecleer.dndapp.model.CharacterModel;

/**
 * Save Characters to the system
 * Load characters from the system
 */
public class CharacterFileUtil {
    private static String LOG_TOKEN = "CFU";

//  ref:  https://developer.android.com/training/basics/data-storage/files.html

    private CharacterFileUtil() {

    }

    public static void saveCharacter(Context context, CharacterModel character) {

        // TODO: 30/05/2016 Do this async

        boolean writable = isExternalStorageWritable();

        Log.i(LOG_TOKEN, "writable: " + writable);

        if (!writable) {
            // TODO: Error handling
            return;
        }

        File file = getFile(context, characterNameToFileName(character.name));

        if (file.getParentFile().mkdirs() || file.getParentFile().isDirectory()) {
            Log.e(LOG_TOKEN, "Directory exists: " + file.getParentFile().getPath());

        } else {
            // TODO: Error handling
            Log.e(LOG_TOKEN, "Directory not created: " + file.getPath());
        }

        // TODO: Query free space


        // Create the Json
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonString = gson.toJson(character);

        // Write the file
        Log.i(LOG_TOKEN, "Trying to write to " + file);

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(jsonString.getBytes());

        } catch (FileNotFoundException e) {
            Log.w(LOG_TOKEN, "Could not find file to create stream");
        } catch (IOException e) {
            Log.w(LOG_TOKEN, "Could not write to stream");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Log.w(LOG_TOKEN, "Could not close stream?");
                }
            }
        }
    }

    public static CharacterModel loadCharacter(Context context, String characterName) {

        File file = getFile(context, characterName);

        // Reading the file

        int length = (int) file.length();
        byte[] bytes = new byte[length];

        try {

            FileInputStream in = new FileInputStream(file);
            try {
                in.read(bytes);
            } catch (IOException e) {
                // TODO: 30/05/2016
            } finally {
                in.close();
            }
        } catch (IOException e) {
            // TODO: 30/05/2016
        }

        String contents = new String(bytes);

        Log.i(LOG_TOKEN, contents);

        Gson gson = new Gson();
        CharacterModel character = gson.fromJson(contents, CharacterModel.class);

        return character;
    }

    // TODO: 30/05/2016 Replace with character preview data?
    public static List<String> getAvailableCharacters(Context context) {

        File fileDir = getFilesDir(context);

        List<String> fileNames = new ArrayList<>();

        for (File file : fileDir.listFiles()) {
            fileNames.add(file.getName());
        }

        return fileNames;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


    @NonNull
    private static File getFile(Context context, String fileName) {
        return new File(
                getFilesDir(context),
                fileName);

    }

    private static File getFilesDir(Context context) {
        return context.getExternalFilesDir(null);
    }

    private static String characterNameToFileName(String characterName) {

        // does what it says on the tin

        char fileSep = '/'; // ... or do this portably.
        char escape = '%'; // ... or some other legal char.

        int len = characterName.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char ch = characterName.charAt(i);
            if (ch < ' ' || ch >= 0x7F || ch == fileSep// add other illegal chars
                    || ch == ' '
                    || (ch == '.' && i == 0) // we don't want to collide with "." or ".."!
                    || ch == escape) {
                sb.append(escape);
                if (ch < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(ch));
            } else {
                sb.append(ch);
            }
        }


        return sb.toString() + ".dnd";
    }
}
