package project.printseven.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRes {
    private Long id;
    private String email;
    private Long successA4;
    private Long successA5;
    private Long successA6;
    private Long errorA4;
    private Long errorA5;
    private Long errorA6;
}
