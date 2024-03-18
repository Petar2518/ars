package rs.ac.fon.bg.ars.exception.specific;

public class MessageQueueException extends RuntimeException{
    public MessageQueueException(Exception e){
        super("Oops! Error on server occured",e);
    }
}
