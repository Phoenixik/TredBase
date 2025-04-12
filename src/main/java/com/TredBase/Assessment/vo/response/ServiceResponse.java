package com.TredBase.Assessment.vo.response;

//@Getter
//@Setter
//@NoArgsConstructor
public class ServiceResponse {

    private long status;
    private String description;

    public static int SUCCESS = 0;
    public static int ERROR = 10;
    public static String GENERAL_SUCCESS_MESSAGE = "Operation Successful";
    public static String GENERAL_ERROR_MESSAGE = "Operation processing failed";

    public ServiceResponse() {
    }

    public ServiceResponse(long status, String description) {
        this.status = status;
        this.description = description;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
