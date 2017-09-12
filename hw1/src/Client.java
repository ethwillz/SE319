import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your name: (Type in your name, then press Enter):");
        String userName = in.nextLine();

        Socket socket = new Socket("localhost", 4444);
        Thread t = new Thread(new ServerListener(socket, userName));
        t.start();

        PrintWriter out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
        out.println(userName);
        out.flush();

        boolean isAdmin = userName.toLowerCase().equals("admin");

        Thread t2 = new Thread(new ServerInteraction(socket, in, isAdmin));
    }
}

class ServerInteraction implements Runnable{
    private int choice;
    private Socket socket;
    private Scanner in;
    private boolean isAdmin;
    private ObjectOutputStream out;

    public ServerInteraction(Socket socket, Scanner in, boolean isAdmin) throws IOException {
        this.socket = socket;
        this.in = in;
        this.isAdmin = isAdmin;
        out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void run(){
        while(true) {
            try {
                choice = Helper.getChoice(in, isAdmin);
                if (choice == 1) {
                    Helper.sendTextMessage(in, out);
                } else if (choice == 2) {
                    Helper.sendImage(socket, in, out);
                } else if (isAdmin && choice == 3) {
                    Helper.printChatHistory();
                } else if (isAdmin && choice == 4) {
                    Helper.deleteMessageFromHistory();
                } else {
                    out.writeObject(new Message(0, null, null)); //Exit code for server to quit thread
                    out.flush();
                    return; //ServerListener will be garbage collected
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

class ServerListener implements Runnable{
    Socket socket;
    ObjectInputStream in;
    String userName;

    public ServerListener(Socket socket, String userName) throws IOException {
        this.socket = socket;
        this.userName = userName;
        in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }
    
    public void run() {
        while(true){
            try {
                Message m = (Message) in.readObject();
                if(m.getMessageCode() == 1){
                    System.out.println(m.getTextContent());
                }
                else{
                    //Gets image from server, appends username stores in local directory
                    String filename = m.getTextContent().split(" ")[0]
                            + "_" + userName
                            + "." + m.getTextContent().split(" ")[1];
                    System.out.println(filename);
                    FileOutputStream fos = new FileOutputStream("." + File.separator + filename);
                    fos.write(m.getFileContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}