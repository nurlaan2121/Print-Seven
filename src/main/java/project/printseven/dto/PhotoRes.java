package project.printseven.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoRes {
    private Long id;
    private ZonedDateTime createdAd;
    private String size;
    private String imageUrl;
    private String success;

}
