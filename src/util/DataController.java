package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import model.Course;
import model.Professor;
import model.Student;
import model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            Gson gson = getTypedGson();
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
    public static boolean saveUser(Student newUser) {
        boolean added = false;
        List<User> users = readUsers();
        if (users == null || users.size() == 0){
            users = new ArrayList<User>();
        }
        added = users.add(newUser);
        //Collections.sort(users);
        boolean saved = false;
        try {
            Gson gson = getTypedGson();
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
    public static boolean saveUser(Professor newUser) {
        boolean added = false;
        List<User> users = readUsers();
        if (users == null || users.size() == 0){
            users = new ArrayList<User>();
        }
        added = users.add(newUser);
        //Collections.sort(users);
        boolean saved = false;
        try {
            Gson gson = getTypedGson();
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
            Gson gson = getTypedGson();
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
            Gson gson = getTypedGson();
            JsonReader reader = new JsonReader(new FileReader(PATH_USERS));
            users = gson.fromJson(reader, new TypeToken<ArrayList<User>>(){}.getType());
            if(users != null){
                for(int i = 0; i < users.size(); i++) {
                    if(users.get(i).getClass().equals(Student.class)) {
                        users.get(i).setType("student");
                    } else {
                        users.get(i).setType("professor");
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

    /**
     * Helper Function
     */
    private static Gson getTypedGson() {
        RuntimeTypeAdapterFactory<User> adapter = RuntimeTypeAdapterFactory
                .of(User.class, "type")
                .registerSubtype(Student.class, User.TYPE_STUDENT)
                .registerSubtype(Professor.class, User.TYPE_PROFESSOR);
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(adapter)
                .create();
    }


}
