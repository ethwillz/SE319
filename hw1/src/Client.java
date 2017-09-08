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

        int choice;
        boolean isAdmin = userName.toLowerCase().equals("admin");

        while(true) {
            choice = Helper.getChoice(in, isAdmin);
            if (choice == 1) {
                Helper.sendTextMessage(in, out);
            } else if (choice == 2) {
                Helper.sendImage(socket, in, out);
            } else if(isAdmin && choice == 3){
                Helper.printChatHistory();
            } else if(isAdmin && choice == 4){
                Helper.deleteMessageFromHistory();
            }else {
                out.println(0); //Exit code for server to quit thread
                out.flush();
                return; //ServerListener will be garbage collected
            }
        }
    }
}

class ServerListener implements Runnable{
    Socket socket;
    Scanner in;
    ObjectInputStream objIn;
    String userName;

    public ServerListener(Socket socket, String userName) throws IOException {
        this.socket = socket;
        this.userName = userName;
        in = new Scanner(new BufferedInputStream(socket.getInputStream()));
        objIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public void run() {
        while(true){
            if(in.hasNextLine()) {
                int messageType = Integer.parseInt(in.nextLine());
                if (messageType == 1) {
                    System.out.println(in.nextLine());
                } else {
                    try {
                        //Gets image from server, appends username, stores in local directory
                        String filename = in.nextLine() + "_" + userName + "." + in.nextLine();
                        System.out.println(filename);
                        FileOutputStream fos = new FileOutputStream("." + File.separator + filename);
                        fos.write((byte[]) objIn.readObject());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}