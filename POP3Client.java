import java.util.*;
import java.io.*;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;

public class POP3Client{
    private SSLSocket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private MyViews observer; 
    

    public POP3Client (String host, Integer port) throws IOException {
            
        SocketFactory factory = SSLSocketFactory.getDefault();
        socket = (SSLSocket) factory.createSocket(host, port);
     
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        String retorno = reader.readLine();
        System.out.println(retorno);

    }

    public void login(String username, String password) throws IOException {
        String retorno;
        writer.write("USER " + username + "\n");
        writer.flush();
        retorno = reader.readLine();

        writer.write("PASS " + password + "\n");
        writer.flush();
        retorno = reader.readLine();
        System.out.println(retorno);

        if(retorno.equals("+OK Welcome."))
            this.notifyObservers();
       
    }

    public LinkedList<String> list() throws IOException{
        LinkedList<String> retorno = new LinkedList<>();
        writer.write("LIST" + "\n");
        writer.flush();

        System.out.println(reader.readLine());

        String line;
        while( (line = reader.readLine()) != null ){
            if (line.equals(".")) break;
            retorno.add(line);
        }

        System.out.println(retorno);

        return retorno;


    }

    public Map<String,LinkedList<String>> retr(int i) throws IOException{
        Map<String, LinkedList<String>> retorno = new HashMap<>();
        writer.write("RETR " + i + "\n");
        writer.flush();

        String[] arrLine;
        String line;

        retorno.put("Date", new LinkedList<>());
        retorno.put("Name", new LinkedList<>());
        retorno.put("From", new LinkedList<>());
        retorno.put("Msg", new LinkedList<>());

        StringBuffer msg=null;
        boolean base = false;
        boolean readmsg = false;

        while( (line = reader.readLine()) != null ){

            arrLine = line.split(" ");
            if(arrLine[0].equals("Date:")) retorno.get("Date").add((String)line.subSequence(6,line.length()));
            if(arrLine[0].equals("Subject:")) retorno.get("Name").add((String)line.subSequence(9,line.length()));
            if(arrLine[0].equals("From:")) retorno.get("From").add((String)line.subSequence(6,line.length()));
            if (line.equals(".")) break;
            
            if(line.contains("Content-Type:")) {
                if(arrLine[1].equals("multipart/mixed;") || arrLine[1].equals("multipart/alternative;")
                    || arrLine[1].equals("text/html;")) continue;
                msg = new StringBuffer();
                msg.append(arrLine[1] + "\n");
                while((line = reader.readLine())!=null){
                    if(line.contains("base64")){
                        base = true;            
                        msg.append("base64" + "\n");  
                    } 
                    if(line.equals("")){
                        readmsg = true;
                        break;
                    } 
                }
            }
            
            if(readmsg && line.contains("--")){
                readmsg = false;
                base = false;
                retorno.get("Msg").add(msg.toString());
                msg = null;
            } 

            if(readmsg){
                if(base) msg.append(line);
                else msg.append(line+"\n");
            }
        
        }

        return retorno;
    }

    public void dele(int i) throws IOException{
        writer.write("DELE " + i + "\n");
        writer.flush();
        System.out.println(reader.readLine());

    }

    public void rset() throws IOException{
        writer.write("RSET " + "\n");
        writer.flush();
        System.out.println(reader.readLine());
    }
    
    public void quit() throws IOException{
        writer.write("QUIT" + "\n");
        writer.flush();
        System.out.println(reader.readLine());

        
        writer.close();
        reader.close();
        socket.close();

    }

    public void stat() throws IOException{
        writer.write("STAT" + "\n");
        writer.flush();
        System.out.println(reader.readLine());
    }
    
    
    public void addObserver(MyViews main){
        this.observer = main;
    }

    public void notifyObservers(){
        observer.update();
    }

    

}