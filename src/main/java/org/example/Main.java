package org.example;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.HashMap.*;
import java.nio.file.*;
import java.nio.file.Path.*;
import java.util.stream.*;

public class Main {

    private static void baueHashmap(Map<String, String> regeln) {
// Dokumente
        regeln.put("pdf", "Dokumente");
        regeln.put("doc", "Dokumente");
        regeln.put("docx", "Dokumente");
        regeln.put("odt", "Dokumente");
        regeln.put("rtf", "Dokumente");
        regeln.put("txt", "Dokumente");
        regeln.put("md", "Dokumente");
        regeln.put("xls", "Dokumente");
        regeln.put("xlsx", "Dokumente");
        regeln.put("ods", "Dokumente");
        regeln.put("csv", "Dokumente");
        regeln.put("ppt", "Dokumente");
        regeln.put("pptx", "Dokumente");
        regeln.put("odp", "Dokumente");
        regeln.put("epub", "Dokumente");
        regeln.put("mobi", "Dokumente");
        regeln.put("pages", "Dokumente");
        regeln.put("numbers", "Dokumente");
        regeln.put("key", "Dokumente");
        regeln.put("tex", "Dokumente");

// Bilder
        regeln.put("jpg", "Bilder");
        regeln.put("jpeg", "Bilder");
        regeln.put("png", "Bilder");
        regeln.put("gif", "Bilder");
        regeln.put("bmp", "Bilder");
        regeln.put("webp", "Bilder");
        regeln.put("svg", "Bilder");
        regeln.put("tif", "Bilder");
        regeln.put("tiff", "Bilder");
        regeln.put("ico", "Bilder");
        regeln.put("heic", "Bilder");
        regeln.put("heif", "Bilder");
        regeln.put("avif", "Bilder");
        regeln.put("raw", "Bilder");
        regeln.put("cr2", "Bilder");
        regeln.put("cr3", "Bilder");
        regeln.put("nef", "Bilder");
        regeln.put("arw", "Bilder");
        regeln.put("dng", "Bilder");
        regeln.put("psd", "Bilder");
        regeln.put("xcf", "Bilder");

// Archive
        regeln.put("zip", "Archive");
        regeln.put("rar", "Archive");
        regeln.put("7z", "Archive");
        regeln.put("tar", "Archive");
        regeln.put("gz", "Archive");
        regeln.put("tgz", "Archive");
        regeln.put("bz2", "Archive");
        regeln.put("xz", "Archive");
        regeln.put("zst", "Archive");
        regeln.put("cab", "Archive");
        regeln.put("iso", "Archive");
        regeln.put("img", "Archive");
        regeln.put("dmg", "Archive");

// Videos
        regeln.put("mp4", "Videos");
        regeln.put("mkv", "Videos");
        regeln.put("avi", "Videos");
        regeln.put("mov", "Videos");
        regeln.put("wmv", "Videos");
        regeln.put("flv", "Videos");
        regeln.put("webm", "Videos");
        regeln.put("m4v", "Videos");
        regeln.put("mpg", "Videos");
        regeln.put("mpeg", "Videos");
        regeln.put("3gp", "Videos");
        regeln.put("ts", "Videos");
        regeln.put("m2ts", "Videos");
        regeln.put("vob", "Videos");
        regeln.put("ogv", "Videos");
        regeln.put("mp3", "Videos");
        regeln.put("wav", "Videos");
//Unfertig
        regeln.put("crdownload", "DontTouch");
        regeln.put("part", "DontTouch");
        regeln.put("partial", "DontTouch");
        regeln.put("download", "DontTouch");
        regeln.put("tmp", "DontTouch");
        regeln.put("opdownload", "DontTouch");
//Programme
        regeln.put("exe", "Installer");
        regeln.put("msi", "Installer");
        regeln.put("msix", "Installer");
        regeln.put("appx", "Installer");
        regeln.put("appxbundle", "Installer");
    }

    private static String holeDateiEndungen(String pfad) {

        String endung = "";
        char[] dateiName = pfad.toLowerCase().toCharArray();
        for (int i = dateiName.length - 1; i >= 0; i--) {
            if (dateiName[i] == (char) 46) {
                try {
                    for (int j = i + 1; j < dateiName.length; j++) {
                        endung = endung + dateiName[j];
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println(". is not a valid format for a file");
                } finally {
                    break;
                }

            }
        }
        return endung;
    }

    private static Path searchDestinationFolder(String endung, Map<String, String> regeln) throws IOException {
        String opposite = regeln.get(endung);
        Path home = Paths.get(System.getProperty("user.home"));
        Path plate = Paths.get(System.getProperty("user.home"));
        plate = plate.getRoot();
        if (regeln.containsKey(endung)) {
            if (opposite.equals("Dokumente")) {
                return home.resolve("Documents").resolve("DL-Cleaner");
            } else if (opposite.equals("Bilder")) {
                return home.resolve("Pictures").resolve("DL-Cleaner");
            } else if (opposite.equals("Archive")) {
                return plate.resolve("Archive").resolve("DL-Cleaner");
            } else if (opposite.equals("Videos")) {
                return home.resolve("Videos").resolve("DL-Cleaner");
            } else if (opposite.equals("Installer")) {
                return plate.resolve("Installer").resolve("DL-Cleaner");
            }
            return home.resolve("Downloads", "Junk").resolve("DL-Cleaner");
        } else {
            return home.resolve("Downloads", "Junk").resolve("DL-Cleaner");
        }
    }

    private static List<Path> holeAlleDateien(String adresse) throws IOException {
        Path path = Path.of(adresse);
        List<Path> dateien = new ArrayList<>();
        try (Stream<Path> inhalt = Files.list(path)) {
            inhalt.forEach(eintrag -> {
                if (Files.isRegularFile(eintrag)) {
                    try {
                        if (!Files.isHidden(eintrag)) {
                            dateien.add(eintrag);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // System.out.println("Folgende Dateien wurden gefunden:");
       /* for (int i = 0; i < dateien.size(); i++) {
            System.out.println(dateien.get(i).getFileName());
        }*/

        return dateien;
    }

    private static List<Path> filtereDateien(String pfad, List<Path> listeVonDateien, Map<String, String> regeln) throws IOException {
        Path path = Path.of(pfad);
        List<Path> gefilterteDateien = new ArrayList<>();
        System.out.println("Found files:");
        for (int i = 0; i < listeVonDateien.size(); i++) {
            String endung = holeDateiEndungen(listeVonDateien.get(i).toString());
            if (regeln.containsKey(endung)) {
                if (!regeln.get(endung).equals("DontTouch")) {
                    gefilterteDateien.add(listeVonDateien.get(i));
                    System.out.println(listeVonDateien.get(i).getFileName());
                }
            } else {
                gefilterteDateien.add(listeVonDateien.get(i));
                System.out.println(listeVonDateien.get(i).getFileName());
            }
        }
        System.out.println("\n\n\n\n");
        return gefilterteDateien;
    }

    private static String setzeZusammen(String dateiName, String endung) {
        char[] neueEndung = endung.toCharArray();
        for (int i = 0; i < neueEndung.length; i++) {
            dateiName = dateiName + neueEndung[i];
        }
        return dateiName;
    }

    private static String fuegeZusammen(String dateiName, String endung) {
        char[] neueEndung = endung.toCharArray();
        if (!endung.isEmpty()) {
            dateiName = dateiName + (char) 46;
        }
        for (int i = 0; i < neueEndung.length; i++) {
            dateiName = dateiName + neueEndung[i];
        }
        // dateiName = dateiName + endung would end up in the same answer
        return dateiName;
    }

    private static Path buildNewDestinationPath(Path zielpfad, String dateiName, String endung) {
        int counter = 1;
        String ursprünglicherName = dateiName;
        String neuerDateiName = "";
        Path neuerPfad = zielpfad;


        while (true) {
            neuerDateiName = fuegeZusammen(dateiName, endung);
            neuerPfad = zielpfad.resolve(neuerDateiName);
            if (!(new File(neuerPfad.toString()).exists())) {
                return neuerPfad;
            } else {
                dateiName = baueNeuenNamen(ursprünglicherName, counter);
                counter++;
            }
        }
    }

    private static String baueNeuenNamen(String dateiName, int counter) {
        String neuerDateiName;
        try {
            neuerDateiName = setzeZusammen(dateiName, " (" + counter + ")");
        } catch (Exception e) {
            neuerDateiName = dateiName;
        }
        return neuerDateiName;
    }

    private static String removeEndung(String name) {
        char[] neueEndung = name.toCharArray();
        String temp = "";
        for (int i = neueEndung.length - 1; i >= 0; i--) {
            if (neueEndung[i] == (char) 46) {
                for (int j = 0; j < i; j++) {
                    temp = temp + neueEndung[j];
                }
                return temp;
            }
        }
        return name;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("It can be necessary to start the application with admin rights");
        try {
            boolean intro = true;
            System.out.println("Skip intro-guide?");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while (true) {
                System.out.println("Yes (Y)   No (N)");
                input = br.readLine();
                if ((input.equals("Y")) || (input.equals("y"))) {
                    intro = false;
                    System.out.println("Skipped");
                    Thread.sleep(500);
                    break;
                } else if ((input.equals("N")) || (input.equals("n"))) {
                    System.out.println("Not skipped");
                    intro = true;
                    break;
                } else {
                    System.out.println("input not accepted.");
                }
            }
            if (intro) {
                System.out.println("Welcome to Download-Cleaner\nThis Programm will clear your Download Folder and transfer all Files to specific locations");
                System.out.println("You can view the destination-folders in the 'README' file on gitHub");
                System.out.println("Press enter once you are ready to start. :)");
                br.read();
            }
            Map<String, String> regeln = new HashMap<>();
            String endung;
            baueHashmap(regeln);
            boolean dryRun = false;
            String ordnerPfad = null;
            for (String arg : args) {
                if (arg.equals("--dry-run")) {
                    dryRun = true;
                } else if (arg.startsWith("--")) {
                    System.out.println("Unbekannte operation: " + arg);
                } else {
                    ordnerPfad = arg;
                }
            }
            try {
                if (ordnerPfad == null) {
                    ordnerPfad = System.getProperty("user.home") + "//Downloads";
                } else {
                    Path folder = Path.of(ordnerPfad);
                    if (!Files.exists(folder)) {
                        System.out.println("The system could not find the specified path");
                        System.out.println("Setting path to Downloads...\n\n");
                        ordnerPfad = System.getProperty("user.home") + "//Downloads";
                        Thread.sleep(1000);
                    }  else {
                        System.out.println("Path found");
                        if(!Files.isDirectory(folder)) {
                            System.out.println("Found a file instead of a path.\nTerminating Session");
                            System.exit(0);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Something went wrong. "+e.getMessage());
                System.exit(0);
            }

            try {

            } catch (Exception e) {
                System.out.println("The --dry-run command was either spelled wrong or simply not entered.\nThe Programm will continue in standart-mode");
                Thread.sleep(1000);
            }


            List<Path> listeAllerDateien = new ArrayList<>();
            listeAllerDateien = holeAlleDateien(ordnerPfad);
            List<Path> filteredListofFiles = new ArrayList<>();

            filteredListofFiles = filtereDateien(ordnerPfad, listeAllerDateien, regeln);
            Thread.sleep(1000);
            System.out.println("Press enter to continue.");
            br.read();
            if (dryRun == true) {
                System.out.println("Dryrun enabled");
            } else {
                System.out.println("Dryrun disabled");
                if (intro == true) {
                    System.out.println("Do you want to enable Dryrun?");
                    System.out.println("Dryrun will not move files, it will only list the destination Paths where the files would've been moved to.");
                    System.out.println("Activate Dryrun ? Yes(Y)   No (N)");
                    while (true) {
                        System.out.println("Yes (Y)   No (N)");
                        input = br.readLine();
                        if ((input.equals("Y")) || (input.equals("y"))) {
                            dryRun = true;
                            System.out.println("Dryrun enabled");
                            break;
                        } else if ((input.equals("N")) || (input.equals("n"))) {
                            System.out.println("Continue without Dryrun");
                            dryRun = false;
                            break;
                        } else {
                            System.out.println("input not accepted.");
                        }
                    }
                    Thread.sleep(2000);
                    System.out.println("Listing files...");
                    Thread.sleep(2000);
                }
            }
            //User abfrage


            System.out.println("Are you sure you want to move following files to the folders listed beneath?\nThis action can only be reversed by the user himself.");
            for (int i = 0; i < filteredListofFiles.size(); i++) {
                endung = holeDateiEndungen(filteredListofFiles.get(i).getFileName().toString());
                Path destinationFolderPath = (searchDestinationFolder(endung, regeln));
                Path destinationPath = buildNewDestinationPath(destinationFolderPath, removeEndung(filteredListofFiles.get(i).getFileName().toString()), endung);
                System.out.println(filteredListofFiles.get(i).getFileName().toString() + "\n-> " + destinationPath.toString() + "\n");
            }
            while (true) {
                Thread.sleep(1000);
                System.out.println("\n\nMove all files to the folders");
                if(dryRun == true) {
                    System.out.println("You are currently in dryRun-mode\nno matter if you press y or n no files will be moved :)");
                }
                System.out.println("Yes (Y)   No (N)");
                input = br.readLine();
                if ((input.equals("Y")) || (input.equals("y"))) {
                    break;
                } else if ((input.equals("N")) || (input.equals("n"))) {
                    System.out.println("Ending Programm...");
                    System.out.println("Goodbye :)");
                    Thread.sleep(2000);
                    System.exit(0);
                } else {
                    System.out.println("input not accepted.");
                }
            }
            if (dryRun == false) {
                for (int i = 0; i < filteredListofFiles.size(); i++) {
                    endung = holeDateiEndungen(filteredListofFiles.get(i).getFileName().toString());
                    Path destinationFolderPath = (searchDestinationFolder(endung, regeln));
                    Path destinationPath = buildNewDestinationPath(destinationFolderPath, removeEndung(filteredListofFiles.get(i).getFileName().toString()), endung);
                    //System.out.println(filteredListofFiles.get(i).getFileName().toString()+"\n-> "+destinationPath.toString());
                    try {
                        if (!Files.exists(destinationFolderPath)) {
                            Files.createDirectories(destinationFolderPath);
                        }
                        Files.move(filteredListofFiles.get(i), destinationPath);
                        System.out.println(filteredListofFiles.get(i).getFileName().toString() + " moved");
                    } catch (Exception e) {
                        System.out.println("Malfunktion: " + filteredListofFiles.get(i).getFileName().toString() + e.getMessage());
                    }

                }
            } else {
                System.out.println("Simulation terminated");
                System.out.println("Goodbye :)");
                System.exit(0);
            }
            System.out.println("All Files moved.");
            System.out.println("Goodbye :)");

            // System.out.println(holeDateiEndungen("bild.jpg").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
