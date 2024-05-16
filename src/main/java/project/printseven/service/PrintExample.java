package project.printseven.service;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Wbemcli;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.ArrayList;
import java.util.List;

public class PrintExample {
    public List<String> getAllConnectionPrinters() {
        List<String> printers = new ArrayList<>();
//        String filePath = "C:\\Users\\user\\Downloads\\imgonline-com-ua-Resize-YhKXNQXhV6.jpg  DDDDDDDDDDDD";
//
//        File fileToPrint = new File(filePath);
//
//        if (java.awt.Desktop.isDesktopSupported()) {
//            // Получаем экземпляр Desktop
//            Desktop desktop = java.awt.Desktop.getDesktop();
//
//            // Проверяем, поддерживает ли десктоп операцию печати
//            if (desktop.isSupported(java.awt.Desktop.Action.PRINT)) {
//                // Отправляем файл на печать
//                try {
//                    desktop.print(fileToPrint);
//                    System.out.println("Document sent to printer.");
//                } catch (IOException e) {
//                    System.out.println("Error printing document: " + e.getMessage());
//                }
//            } else {
//                System.out.println("Printing is not supported on this platform.");
//            }
//        } else {
//            System.out.println("Desktop operations are not supported on this platform.");
//        }
        
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService print : printServices) {
            printers.add(print.getName());
        }
        return printers;
    }
}
