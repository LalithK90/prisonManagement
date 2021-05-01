package lk.prison_management.asset.performance_evaluation_request.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Task {

    CRP(" Coordinating Religious Programs"),
    CHP(" Coordinating Health Programs"),
    CMHP(" Coordinating Mental Health Programs"),
    CEP(" Coordinating Education Programs"),
    CVP(" Coordinating Vocational Programs"),
    CLAP(" Coordinating Legal Aids Programs");

    private final String task;


}
