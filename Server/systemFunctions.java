import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map; 
import java.util.HashMap;

public interface systemFunctions extends Remote {
    String sayHello() throws RemoteException;
    int setDescription(String description) throws RemoteException;
    String getDescriptionFromKey(int key) throws RemoteException;
    Map getContents() throws RemoteException;
    Map getContentsValue(String match) throws RemoteException;
    Map getContentsDescription(String match) throws RemoteException;
    void addCallback(CallbackClientInterface CallbackObject) throws RemoteException;
}
