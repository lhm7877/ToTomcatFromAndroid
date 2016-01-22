package support.design.android.hoomin.co.kr.totomcatfromandroid;

/**
 * Created by HooMin on 2016-01-20.
 * 여러클래스에서 공통으로 사용하는 ValueObject객체(리스트, 이동)
 * 안드로이드에선 서버와는 다르게 반드시 get/set를 두지 않고 공용(public)변수로 등록한다
 */
public class BloodValueObject {
    public String patientName;
    public String bloodType;
    public String statusText;
    public String donationType;
    public String bloodValue;
    public String hospital;
    public String hospitalPhone;
    public String relationText;
    public String careName;
    public String carePhone;
    public int bloodId;
    public String insertDate;

    public BloodValueObject(){}

    public BloodValueObject(String patientName, String bloodType,
                            String statusText, String donationType,
                            String bloodValue, String hospital,
                            String hospitalPhone, String relationText,
                            String careName, String carePhone,
                            int bloodId, String insertDate) {
        this.patientName = patientName;
        this.bloodType = bloodType;
        this.statusText = statusText;
        this.donationType = donationType;
        this.bloodValue = bloodValue;
        this.hospital = hospital;
        this.hospitalPhone = hospitalPhone;
        this.relationText = relationText;
        this.careName = careName;
        this.carePhone = carePhone;
        this.bloodId = bloodId;
        this.insertDate = insertDate;
    }
}