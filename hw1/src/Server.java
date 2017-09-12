import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static List<Socket> activeSockets;

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        activeSockets = new ArrayList<>();

        try{
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                activeSockets.add(clientSocket);

                ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
                objOut.flush();

                Scanner in = new Scanner(new BufferedInputStream(clientSocket.getInputStream()));
                String userName = in.nextLine();

                System.out.println("Server connected to " + userName);

                Thread t = new Thread(new ClientHandler(clientSocket, userName));
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Socket> getActiveSockets(){
        return activeSockets;
    }
}

class ClientHandler extends Thread implements Runnable{
    private Socket socket;
    private String userName;
    private File file;
    private FileWriter fw;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    ClientHandler(Socket socket, String userName) throws IOException {
        this.socket = socket;
        this.userName = userName;
        in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        out = new ObjectOutputStream((new BufferedOutputStream(socket.getOutputStream())));
    }

    public void run(){
        while(true){
            try {
                Message m = (Message) in.readObject();

                if(m.getMessageCode() == 0){
                    break;
                }
                else if(m .getMessageCode() == 1){
                    String message = (Helper.getLatestMessageID() + 1)
                            + " " + userName
                            + ": " + m.getTextContent() + "\n";

                    System.out.print(message);

                    file = new File("." + File.separator + "chat.txt");
                    fw = new FileWriter(file, true);
                    fw.write(message);
                    fw.flush();

                    Helper.sendMessageToActiveClients(socket, message, out);
                }
                else{
                    String filename = m.getTextContent().split(" ")[0]
                            + "_" + userName
                            + "_" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                            + "_" + Calendar.getInstance().get(Calendar.MINUTE)
                            + "." + m.getTextContent().split(" ")[1];

                    System.out.println(filename);

                    new File("." + File.separator + "image").mkdir();

                    FileOutputStream fos = new FileOutputStream("." + File.separator + "image" + File.separator + filename);
                    fos.write(m.getFileContent());

                    //Write to chat.txt
                    file = new File("." + File.separator + "chat.txt");
                    fw = new FileWriter(file, true);
                    fw.write((Helper.getLatestMessageID() + 1) + " " + userName + ": " + filename + "\n");
                    fw.flush();

                    Helper.sendImageToActiveClients(socket,
                            m.getTextContent().split(" ")[0] + " " + m.getTextContent().split(" ")[1],
                            m.getFileContent(),
                            out);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}