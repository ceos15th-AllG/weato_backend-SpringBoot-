package allG.weato.domains.member.entities;

import allG.weato.domains.enums.ManagementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class Management {

   List<ManagementType> managementTypes = new ArrayList<>();

}
