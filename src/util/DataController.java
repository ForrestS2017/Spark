/**
 * @author Forrest Smith
 */
package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains functionality for data storage
 */
public class DataController {
    // Save File Paths
    private static String PATH_COURSES = "src/data/Courses.JSON";
    private static String PATH_USERS = "src/data/Users.JSON";

    /**
     * Update course data in storage
     */
    public static boolean saveCourse(Course newCourse) {
        boolean added = false;
        ArrayList<Course> courses = readCourses();
        if (courses == null) {
            courses = new ArrayList<Course>();
        }
        int courseIndex = courses.indexOf(newCourse);
        if (courseIndex > -1) {
            added =  courses.set(courseIndex,newCourse) != null;
            Collections.sort(courses);
        }
        else
        {
            added =  courses.add(newCourse);
            Collections.sort(courses);
        }
        boolean saved = false;
        try {
            Gson gson = getUserTypedGson();
            String jsonString = gson.toJson(courses);
            FileWriter fw = new FileWriter(PATH_COURSES);
            fw.write(jsonString);
            fw.close();
            saved = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            saved = false;
        }

        return added && saved;
    }

    /**
     * Update and save a student's data in storage
     */
    public static boolean saveUser(Student newUser) {
        boolean added = false;
        List<User> users = readUsers();
        // Null check list
        if (users == null || users.size() == 0){
            users = new ArrayList<User>();
        }
        // Duplicate check
        if (users.contains(newUser)){
            users.set(users.indexOf(newUser), newUser);
        } else{
            added = users.add(newUser);
        }
        boolean saved = false;
        try {
            Gson gson = getUserTypedGson();
            String jsonString = gson.toJson(users);
            FileWriter fw = new FileWriter(PATH_USERS);
            fw.write(jsonString);
            fw.close();
            saved = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            saved = false;
        }
        return added && saved;
    }

    /**
     * Update and save a professor's data in storage
     */
    public static boolean saveUser(Professor newUser) {
        boolean added = false;
        List<User> users = readUsers();
        // Null check list
        if (users == null || users.size() == 0){
            users = new ArrayList<User>();
        }
        // Duplicate check
        if (users.contains(newUser)){
            users.set(users.indexOf(newUser), newUser);
        } else{
            added = users.add(newUser);
        }
        boolean saved = false;
        try {
            Gson gson = getUserTypedGson();
            String jsonString = gson.toJson(users);
            FileWriter fw = new FileWriter(PATH_USERS);
            fw.write(jsonString);
            fw.close();
            saved = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            saved = false;
        }
        return added && saved;
    }

    /**
     * Update and save an administrator's data in storage
     */
    public static boolean saveUser(Administrator newUser) {
        boolean added = false;
        List<User> users = readUsers();
        // Null check list
        if (users == null || users.size() == 0){
            users = new ArrayList<User>();
        }
        // Duplicate check
        if (users.contains(newUser)){
            users.set(users.indexOf(newUser), newUser);
        } else{
            added = users.add(newUser);
        }
        boolean saved = false;
        try {
            Gson gson = getUserTypedGson();
            String jsonString = gson.toJson(users);
            FileWriter fw = new FileWriter(PATH_USERS);
            fw.write(jsonString);
            fw.close();
            saved = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            saved = false;
        }
        return added && saved;
    }



    /**
     * Update and save a set of students data in storage
     */
    public static boolean saveUsers(ArrayList<User> newUsers) {
        ArrayList<User> users = readUsers();
        if (users == null){
            users = new ArrayList<User>();
        }
        boolean added = users.addAll(newUsers);
        Collections.sort(users);
        boolean saved = false;
        try {
            Gson gson = getUserTypedGson();
            String jsonString = gson.toJson(users);
            FileWriter fw = new FileWriter(PATH_USERS);
            fw.write(jsonString);
            fw.close();
            saved = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            saved = false;
        }

        return added && saved;
    }

    /**
     * Load all saved user data
     */
    public static ArrayList<User> readUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try
        {
            Gson gson = getUserTypedGson();
            JsonReader reader = new JsonReader(new FileReader(PATH_USERS));
            users = gson.fromJson(reader, new TypeToken<ArrayList<User>>(){}.getType());
            if(users != null){
                for(int i = 0; i < users.size(); i++) {
                    if(users.get(i).getClass().equals(Student.class)) {
                        users.get(i).setType(User.TYPE_STUDENT);
                    } else if(users.get(i).getClass().equals(Professor.class)) {
                        users.get(i).setType(User.TYPE_PROFESSOR);
                    } else if(users.get(i).getClass().equals(Administrator.class)) {
                        users.get(i).setType(User.TYPE_ADMINISTRATOR);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    /**
     * Load saved course data
     */
    public static ArrayList<Course>  readCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader(PATH_COURSES));
            courses = gson.fromJson(reader, new TypeToken<ArrayList<Course>>(){}.getType());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return courses;
    }

    /**
     * Returns customized Gson instance with registered User types
     */
    private static Gson getUserTypedGson() {
        RuntimeTypeAdapterFactory<User> adapter = RuntimeTypeAdapterFactory
                .of(User.class, "type")
                .registerSubtype(Student.class, User.TYPE_STUDENT)
                .registerSubtype(Professor.class, User.TYPE_PROFESSOR)
                .registerSubtype(Administrator.class, User.TYPE_ADMINISTRATOR);
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(adapter)
                .create();
    }

}
