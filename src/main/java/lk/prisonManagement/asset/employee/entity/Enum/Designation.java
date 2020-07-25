package lk.prisonManagement.asset.employee.entity.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
    ASPR("	Assistant Superintendent of Prison(Rehabilitation)	"),
    CRO("	Chief  Rehabilitation Officer	"),
    CR("	Commissioner - Rehabilitation	"),
    ACSLAS("	Assistant Commissioner (S.L.A.S)	"),
    SSPR("	Senior  Superintendent of Prison(Rehabilitation)	"),
    SPR("	Superintendent of Prison(Rehabilitation)	"),
    CIRO("	Class  I Rehabilitation Officer	"),
    CIIRO("	Class  II Rehabilitation Officer	"),
    CIIIRO("	Class  III Rehabilitation Officer	");


    private final String designation;
}

