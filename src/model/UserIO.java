package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;

public class UserIO implements Serializable {	
	/**
	 * Write the user data to the disk.
	 * @param fileName Name of file, without any extension
	 * @param userData Object you would like to save (Organization Object)
	 * @throws IOException
	 */
	public static void writeUserData(String fileName, Object userData)
	throws IOException{
		String pathname = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\data\\" + fileName + ".dat";
		File f = new File(pathname);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(userData);
	}
	
	/**
	 * Read the user data from the disk
	 * @param fileName Name of file, without any extension
	 * @return User data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object readUserData(String fileName)
	throws IOException, ClassNotFoundException{
		String pathname = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\data\\" + fileName + ".dat";
		System.out.println("USERIO: pathname = " + pathname);
		File f = new File(pathname);
		if(!f.exists()) {
			f.createNewFile();
			return new Organization(fileName.substring(0, fileName.length()-4));
		}
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		return (Object)(ois.readObject());
	}
}
