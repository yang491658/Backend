package project.blobus.Backend.youth.house.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseAttachment {
    private String fileName;
    private int ord;

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
