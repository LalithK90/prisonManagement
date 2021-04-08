package lk.prison_management.asset.process_management.mode;

import lk.prison_management.asset.employee.entity.Employee;
import lk.prison_management.asset.offence.entity.Offence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffenceEmployee {
private Offence offence;
private List< Employee > employees;
}
