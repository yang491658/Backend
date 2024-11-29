package project.blobus.Backend.youth.house.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PageRequestDTO {;
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 12;
    @Builder.Default
    private String searchTerm  = "";
    @Builder.Default
    private String filterType  = "polyBizSjnm";
}
