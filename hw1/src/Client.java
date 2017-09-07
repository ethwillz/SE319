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

        while(true) {
            choice = getChoice(in);
            if (choice == 1) {
                sendTextMessage(in, out);
            } else if (choice == 2) {
                sendImage(socket, in, out);
            } else {
                out.println(0); //Exit code for server to quit thread
                out.flush();
                return; //ServerListener will be garbage collected
            }
        }
    }

    public static int getChoice(Scanner in){
        int choice;

        System.out.println("Press (1) to send a text message to the server\nOr (2) to send an image to the server\nOr (0) to exit");

        while(true){
            try {
                choice = Integer.parseInt(in.nextLine());
                if(choice == 1 || choice == 2 || choice == 0){
                    return choice;
                }
                else{
                    System.out.println("The number entered is not 1 or 2.... is this really that hard?");
                }
            }
            catch(NumberFormatException e){
                System.out.println("Please enter in a valid number");
            }
        }
    }

    public static void sendTextMessage(Scanner in, PrintWriter out) throws IOException{
        System.out.println("Please type in a message, then press Enter");

        out.println("1");
        out.println(in.nextLine());
        out.flush();
    }

    public static void sendImage(Socket socket, Scanner in, PrintWriter out) throws IOException{
        System.out.println("Specify the filepath of the image, then press Enter");

        out.println("2");
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        objOut.writeObject(new File(in.nextLine()));
        objOut.flush();
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
                        File image = (File) objIn.readObject();
                        String name = Helper.getImageName(image.getAbsoluteFile());
                        System.out.println(name);
                        name += "_" + userName;
                        FileWriter fw = new FileWriter(image);
                        fw.write("." + File.separator + name);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}