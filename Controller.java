import java.io.IOException;
import java.util.*;

public class Controller{
    private POP3Client model;

	public Controller() {}

    public void connect(String server, String port){
        try{
            model = new POP3Client(server, Integer.parseInt(port));
        }catch(IOException ioe){System.out.println("connect");}
    }

    public void login(String username, String password){
        try{
            model.login(username, password);
        }catch(IOException ioe){System.out.println("login");}
    }

    public LinkedList<String> list(){
        try{
            return model.list();
        }catch(IOException e ){System.out.println("list");}
        return null;

    }

    public Map<String,LinkedList<String>> retr(int i){
        try{
           return model.retr(i);
        }catch(IOException e ){System.out.println("retr");}
        return null;
    }

    public void dele(int i){
        try{
            model.dele(i);
        }catch(IOException e ){}
    }

    public void quit(){
        try{
            if(model!=null) model.quit();
            model = null;
        }catch(IOException e ){System.out.println("quit");}
    }

    public void stat(){
        try{
            model.stat();
        }catch(IOException e ){System.out.println("stat");}
    }

    public void rset(){
        try{
            model.rset();
        }catch(IOException e ){System.out.println("rset");}
    }


    public void setObserver(MyViews main) {
            model.addObserver(main);
    }

}