package project.printseven.service;

import project.printseven.controllers.UserPage;

import java.nio.file.*;

public class DirectoryWatcher {
    public UserPage userPage = new UserPage();
    public void watcherStarter() {
        System.out.println("START watcherStarter METHOD");
        Path directory = Paths.get("C:\\Windows\\System32\\spool\\PRINTERS");

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("EVENT");
                    Path createdFile = (Path) event.context();
                    String filename = createdFile.toString();
                    if (filename.contains("0")) {
                        System.out.println("SHD || SPL");
                        userPage.start();
                    }
                }
                key.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
