package lk.prison_management.asset.process_management.mode;

import lk.prison_management.asset.institute.entity.Institute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteCommendation {
  private Institute institute;
  private long commendationCount;
}
