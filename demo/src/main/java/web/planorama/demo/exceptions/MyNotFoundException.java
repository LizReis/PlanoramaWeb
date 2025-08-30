package web.planorama.demo.exceptions;

public class MyNotFoundException extends RuntimeException{
    public MyNotFoundException(String message){
        super(message);
    }
}
