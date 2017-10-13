package hu.webandmore.shutter_mvp.interactor.events;

public class RegisterEvent {

    private int code;
    private Throwable throwable;
    private String errorMessage;

    public RegisterEvent(){

    }

    public RegisterEvent(int code, Throwable t){
        this.code = code;
        this.throwable = t;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

}