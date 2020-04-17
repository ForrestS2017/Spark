package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Course;
import model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class DataController {
    private static String PATH_COURSES = "src/data/Courses.JSON";
    private static String PATH_USERS = "src/data/Users.JSON";

    /**
     * Update all save data with new data from session
     */
    public static boolean saveAll() {
        return false;
    }

    /**
     * Update course in save data
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
     * Update user in save data
     */
    public static boolean saveUser(User newUser) {
        boolean added = false;
        ArrayList<User> users = readUsers();
        if (users == null){
            users = new ArrayList<User>();
        }
        added = users.add(newUser);
        Collections.sort(users);
        boolean saved = false;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
     * Update user in save data
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
     * Load User saved data
     */
    public static ArrayList<User>  readUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonReader reader = new JsonReader(new FileReader(PATH_USERS));
            users = gson.fromJson(reader, new TypeToken<ArrayList<User>>(){}.getType());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    /**
     * Load Course saved data
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


}
