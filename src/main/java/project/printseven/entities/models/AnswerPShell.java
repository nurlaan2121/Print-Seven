package project.printseven.entities.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Timer;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AnswerPShell {
    private Long id;
    private String computerName;
    private String printerName;
    private String user;
    private String namePhoto;
    private String dateType;
    private int priority;
    private int position;
    private LocalDateTime submittedTime;
    private Long size;
    private int secondJobTIme;
    private int pagesPrinted;
    private int totalPages;
    private String jobStatus;
}
