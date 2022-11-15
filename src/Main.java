import java.io.*;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        File zipFile = new File("C:/Games/savegames/zip.zip");
        File directories = new File("C:/Games/savegames");
        openZip(zipFile, directories);

        File[] files = directories.listFiles();
        int countElementDirectories = files.length;
        Random random = new Random();
        File fileRandom = !files[random.nextInt(countElementDirectories)].equals(zipFile) ? files[random.nextInt(countElementDirectories)] : files[random.nextInt(countElementDirectories-1)];
        GameProgress gpRandom = openProgress(fileRandom);
        System.out.println(gpRandom);
    }

    public static void openZip(File file, File dirName) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(file))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = dirName.toString() + "/" + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}