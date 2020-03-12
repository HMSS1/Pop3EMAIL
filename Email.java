import java.util.*;
public class Email {

        private String nro;
        private String name;
        private String from;
        private String date;
        private LinkedList<String> msg;

        public Email(String nro,String name, String from, String date, LinkedList<String> msg) {
            this.nro = nro;
            this.name = name;
            this.from = from;
            this.date = date;
            this.msg = msg;
        }

        public String getNro() {
            return nro;
        }
        public String getName() {
            return name;
        }
        public String getFrom() {
            return from;
        }
        public String getDate() {
            return date;
        }
        public LinkedList<String> getMsg() {
            return msg;
        }

        public void setName(String name) {
            this.name = name;
        }
        public void setFrom(String from) {
            this.from = from;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public void setMsg(LinkedList<String> msg) {
            this.msg = msg;
        }
    


}