package com.uta.eprescription.activities.prescMgr.common;

import java.util.ArrayList;
import java.util.Map;

public interface PrescriptionListCallback <T> {
    void callback(ArrayList prescriptionList, Map patientDetails);
}
