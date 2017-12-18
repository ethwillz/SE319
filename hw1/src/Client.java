import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException{
        Scanner consoleIn = new Scanner(System.in);

        System.out.println("Enter your name: (Type in your name, then press Enter):");
        String userName = consoleIn.nextLine();

        Socket socket = new Socket("localhost", 4444);
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        Thread listener = new Thread(new ServerListener(userName, in));
        listener.start();

        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        out.writeUTF(userName);
        out.flush();

        boolean isAdmin = userName.toLowerCase().equals("admin");

        Thread sender = new Thread(new ServerSender(socket, consoleIn, out, isAdmin));
        sender.start();
    }
}

class ServerSender implements Runnable{
    private Socket socket;
    private Scanner in;
    private boolean isAdmin;
    private ObjectOutputStream out;

    public ServerSender(Socket socket, Scanner in, ObjectOutputStream out, boolean isAdmin) throws IOException {
        this.socket = socket;
        this.in = in;
        this.isAdmin = isAdmin;
        this.out = out;
    }

    public void run(){
        while(true) {
            try {
                int choice = Helper.getChoice(in, isAdmin);
                if (choice == 1) {
                    Helper.sendTextMessage(in, out);
                } else if (choice == 2) {
                    Helper.sendImage(socket, in, out);
                } else if (isAdmin && choice == 3) {
                    Helper.printChatHistory();
                } else if (isAdmin && choice == 4) {
                    Helper.deleteMessageFromHistory();
                } else {
                    out.writeObject(new Message(0, null, null));
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
    private ObjectInputStream in;
    private String userName;

    ServerListener(String userName, ObjectInputStream in) throws IOException {
        this.userName = userName;
        this.in = in;
    }

    public void run() {
        while(true){
            try {
                Message m = (Message) in.readObject();
                if(m.getMessageCode() == 1){
                    System.out.print(m.getTextContent());
                }
                else{
                    //Gets image from server, appends username stores in local directory
                    String filename = m.getTextContent().split(", ")[0]
                            + "_" + userName
                            + "." + m.getTextContent().split(", ")[1];
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