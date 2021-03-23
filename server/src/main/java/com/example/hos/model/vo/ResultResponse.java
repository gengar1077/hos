package com.example.hos.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author heweiwei
 * @date 2018/2/5
 */
public class ResultResponse implements Serializable {


    private static final long serialVersionUID = 7235084927681911824L;
    private Boolean success;

    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private String returnCode;

    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
    private Object returnData;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    public ResultResponse success(String message){
        this.setSuccess(true);
        this.setReturnCode(Code.SUCCESS);
        this.setMessage(message);
        return this;
    }

    public ResultResponse miniCodeError(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.MINI_CODE_ERROR);
        this.setMessage(message);
        return this;
    }

    public ResultResponse sessionKeyFail(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.SESSION_KEY_FAIL);
        this.setMessage(message);
        return this;
    }

    public ResultResponse openIdBlank(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.OPEN_ID_BLANK);
        this.setMessage(message);
        return this;
    }

    public ResultResponse miniSessionKeyNotFound(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.SESSION_KEY_NOT_FOUND);
        this.setMessage(message);
        return this;
    }

    public ResultResponse decryptUserInfoFailed(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.DECRYPT_USER_INFO_FAILED);
        this.setMessage(message);
        return this;
    }

    public ResultResponse accountNotFound(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.ACCOUNT_NOT_FOUND);
        this.setMessage(message);
        return this;
    }

    public ResultResponse requireLogin(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.REQUIRE_LOGIN);
        this.setMessage(message);
        return this;
    }

    public ResultResponse uploadImageFail(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.UPLOAD_IMAGE_FAIL);
        this.setMessage(message);
        return this;
    }

    public ResultResponse idANotUpload(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.ID_A_NOT_UPLOAD);
        this.setMessage(message);
        return this;
    }

    public ResultResponse idBNotUpload(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.ID_B_NOT_UPLOAD);
        this.setMessage(message);
        return this;
    }

    public ResultResponse requireApplicantId(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.REQUIRE_APPLICANT_ID);
        this.setMessage(message);
        return this;
    }

    public ResultResponse idANotContainFace(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.ID_A_NOT_CONTAIN_FACE);
        this.setMessage(message);
        return this;
    }

    public ResultResponse nciicCheckFail(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.NCIIC_CHECK_FAIL);
        this.setMessage(message);
        return this;
    }

    public ResultResponse idCompareFail(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.ID_COMPARE_FAIL);
        this.setMessage(message);
        return this;
    }

    public ResultResponse idNotSamePerson(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.ID_NOT_SAME_PERSON);
        this.setMessage(message);
        return this;
    }

    /**
     * @description
     * @author Qingcheng Wang
     * @date 2018/12/28 0028
     * @param
     * @return
     */
    public ResultResponse excelValidateFail(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.EXCEL_VALIDATE_ERROR);
        this.setMessage(message);
        return this;
    }

    public ResultResponse fail(String code,String message){
        this.setSuccess(false);
        this.setReturnCode(code);
        this.setMessage(message);
        return this;
    }

    public ResultResponse makeFail(String message){
        this.setSuccess(false);
        this.setReturnCode(Code.SYSTEM_ERROR);
        this.setMessage(message);
        return this;
    }

    public void success() {
    }

    public static class Code{

        public static final String HTTP_METHOD_NOT_SUPPORT="101";

        public static final String CONTENT_TYPE_NOT_SUPPORT="102";

        public static final String SUCCESS="200";

        public static final String REQUIRE_APPLICANT_ID="301";


        public static final String REQUIRE_PHONE_NUMBER="302";

        public static final String REQUIRE_ADDRESS="303";

        public static final String REQUIRE_FULLNAME="304";

        public static final String REQUIRE_ID_NUMBER="305";

        public static final String REQUIRE_GENDER="306";

        public static final String REQUIRE_START_DATE="307";

        public static final String REQUIRE_EXPIRE_DATE="308";

        public static final String REQUIRE_IMAGE ="309";

        public static final String REQUIRE_SIGNING_IMAGE="310";

        public static final String ERROR_GENDER="311";

        public static final String REQUIRE_MARITAL="312";

        public static final String REQUIRE_RESIDENCE_CODE="313";

        public static final String REQUIRE_EMPLOYER_NAME="314";

        public static final String REQUIRE_WORK_PHONE="315";

        public static final String REQUIRE_EMPLOYMENT_SINCE="316";

        public static final String REQUIRE_INCOME="317";

        public static final String REQUIRE_SPOUSE_NAME="318";

        public static final String REQUIRE_SPOUSE_MOBILE_PHONE="319";

        public static final String REQUIRE_RESIDENCE_ADDRESS="320";

        public static final String REQUIRE_RESIDENCE_PROVINCE="321";

        public static final String REQUIRE_RESIDENCE_CITY="322";

        public static final String REQUIRE_LOAN_ID="323";

        public static final String REQUIRE_FOL_ID="324";

        public static final String REQUIRE_FOL_STATUS="325";

        public static final String REQUIRE_DATA="326";

        public static final String REQUIRE_VIDEO="327";

        public static final String REQUIRE_PROVINCE="328";

        public static final String RESIDENCE_ADDRESS_LENGTH_LIMIT="329";

        public static final String REQUIRE_HOUSE_TITLE_STATUS="330";

        public static final String REQUIRE_VALIDATE_CODE="331";

        public static final String REQUIRE_SYSTEM="332";

        public static final String REQUIRE_FAFCID="333";

        public static final String OAUTH2_CODE_REUQIRE="334";

        public static final String APPKEY_REQUIRE="335";

        public static final String SYSTEM_ERROR="500";

        public static final String MINI_CODE_ERROR="501";

        public static final String SESSION_KEY_FAIL="502";

        public static final String UNION_ID_BLANK="503";

        public static final String OPEN_ID_BLANK="504";

        public static final String SESSION_KEY_NOT_FOUND="505";

        public static final String DECRYPT_USER_INFO_FAILED="506";

        public static final String ACCOUNT_NOT_FOUND="507";

        public static final String REQUIRE_LOGIN="508";

        public static final String ID_B_NOT_UPLOAD="509";

        public static final String UPLOAD_IMAGE_FAIL="510";

        public static final String ID_A_NOT_UPLOAD="511";

        public static final String ID_A_DUPLICATE="512";

        public static final String ID_B_DUPLICATE="513";

        public static final String ID_A_NOT_CONTAIN_FACE="514";

        public static final String NCIIC_CHECK_FAIL="515";

        public static final String ID_COMPARE_FAIL="516";

        public static final String ID_NOT_SAME_PERSON="517";

        public static final String PHONE_NUMBER_LENGTH_ERROR="518";

        public static final String PHONE_ONLY_NUMNERIC_ALLOW="519";

        public static final String ID_CARD_EXPIRE="520";

        public static final String ERROR_ID_NUMBER="521";

        public static final String AGE_MUST_REACH_20="522";

        public static final String APPLICATION_NOT_FOUND="523";

        public static final String ERROR_VALIDATE_CODE="524";

        public static final String PHONE_NUMBER_USED="525";

        public static final String NCIIC_TIMES_LIMIT="526";

        public static final String IDMATERIAL_ALREADY_SUCCESS="527";

        public static final String ID_MATERIAL_NOT_PERMISSION="528";

        public static final String READ_ID_DATE_FAIL="529";

        public static final String PHONE_ALREADY_CHECK="530";

        public static final String SESSION_KEY_EXPIRE="531";

        public static final String FACE_NOT_EXIST="532";

        public static final String ALREADY_SIGNING="533";

        public static final String LIVENESS_ACTION_ERROR="534";

        public static final String LIVENESS_EYE_ACTION="535";

        public static final String LIVENESS_MOUSE_ACTION="536";

        public static final String LIVENESS_FAIL="537";

        public static final String LIVENESS_DIFFERENT="538";

        public static final String FACE_IMAGE_NOT_FOUND="539";

        public static final String NCIIC_NOT_SUCCESS="540";

        public static final String LIVENESS_ALREADY_SUCCESS="541";

        public static final String LIVENESS_NOT_SUCCESS="542";

        public static final String LIVENESS_FACE_NEAR="543";

        public static final String LIVENESS_FACE_FAR="544";

        public static final String LIVENESS_FACE_LEFT="545";

        public static final String LIVENESS_FACE_RIGHT="546";

        public static final String LIVENESS_DARK="547";

        public static final String LIVENESS_LIGHT="548";

        public static final String ERROR_FORM_ID="549";

        public static final String LIVENESS_TIMES_LIMIT="550";

        public static final String INFO_ALREADY_SUPPLY="551";

        public static final String FORM_ID_NOT_SCOPE="552";

        public static final String ERROR_FORMAT_INCOME="553";

        public static final String ERROR_FORMAT_EMPLOYMENT_SINCE="554";

        public static final String ERROR_EMPLOYMENT_SINCE="555";

        public static final String ACCOUNT_NOT_BIND_PHONE="556";

        public static final String MATERIAL_NOT_FOUND="557";

        public static final String BIRTH_DAY_ERROR="558";

        public static final String RESIDENCE_PROVINCE_OR_CITY_ERROR ="559";

        public static final String SIGN_ALREADY_CONFIRM="560";

        public static final String SIGNING_NOT_FOUND="561";

        public static final String READ_ID_INFO_FAIL="562";

        public static final String PERSONAL_INFO_NOT_SUPPLY="563";

        public static final String OTHER_FORM_NOT_COMPLETE="564";

        public static final String ERROR_FOL_ID="565";

        public static final String FOL_STATUS_ERROR="566";

        public static final String FORM_ALREADY_CONFIRM="567";

        public static final String OPEN_ID_FIRST_LOGIN="568";

        public static final String FORM_SERVER_ERROR="569";

        public static final String EXCEL_VALIDATE_ERROR = "570";

        public static final String UNKNOWN_EXTENDEDWARRANTY= "571";

        public static final String UNKNOWN_LOANTYPE = "572";

        public static final String CMS_FAIL="573";

        public static final String VALIDATE_NOT_SEND="574";

        public static final String VALIDATE_CODE_USED="575";

        public static final String VALIDATE_CODE_PHONE_NOT_EQUAL="576";

        public static final String VALIDATE_CODE_ERROR="577";

        public static final String ACCOUNT_PASS_ERROR = "578";

        public static final String IDCARD_GENDER_ERROR="579";

        public static final String EMPLOYMENT_TYPE_ERROR="580";

        public static final String INDUSTRY_GROUP_ERROR="581";

        public static final String EMPLOYMENT_LEVEL_ERROR="582";

        public static final String FOL_SUBMIT_ERROR="583";

        public static final String PHONE_NUMBER_NOT_SET="584";

        public static final String CODE_EXPIRE="585";

        public static final String DEALER_CODE_NULL="586";

        public static final String DEALER_CODE_ERROR="587";

        public static final String MODEL_CODE_NULL="588";

        public static final String MODEL_CODE_ERROR="589";

        public static final String PRODUCT_CODE_NULL="590";

        public static final String DEPOSIT_NULL="591";

        public static final String TERM_NULL="592";

        public static final String RATE_NULL="593";

        public static final String ERROR_QRCODE="594";

        public static final String QRCODE_EXPIRE="595";

        public static final String QRCODE_SYSTEM_ERROR="596";

        public static final String NEED_QRCODE_LOGIN="597";

        public static final String CASH_PRICE_MAX="598";

        public static final String CASH_PRICE_MIN="599";

        public static final String APPKEY_ERROR="600";

        public static final String OAUTH2_FAIL="601";

        public static final String SNS_USERINFO_FAIL="602";

        public static final String ERROR_MATERIAL_ID="603";

        public static final String MATERIAL_DENIED="604";

        public static final String MATERIAL_COUNT_LIMIT="605";

        public static final String ACCOUNT_IS_BLOCK="606";

        public static final String IMAGE_TYPE_ERROR="607";

        public static final String EMPLOYER_PROVINCE_OR_CITY_ERROR = "608";
        public static final String MAILING_PROVINCE_OR_CITY_ERROR = "609";

        public static final String ESIGN_ACCOUNT_INIT_ERROR="701";
        public static final String ESIGN_SEND_CODE_ERROR="702";
        public static final String ESIGN_SIGN_ERROR="703";

        public static final String JMC_ERROR="801";


    }
}
