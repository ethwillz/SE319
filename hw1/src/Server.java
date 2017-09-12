import javafx.util.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

public class Server {
    private static ArrayList<Pair<Socket, ObjectOutputStream>> activeSockets;

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

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                activeSockets.add(new Pair(clientSocket, out));

                ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                String userName = in.readUTF();
                System.out.println("Server connected to " + userName);

                Thread handler = new Thread(new ClientHandler(clientSocket, userName, in));
                handler.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static ArrayList<Pair<Socket, ObjectOutputStream>> getActiveSockets(){
        return activeSockets;
    }
}

class ClientHandler extends Thread implements Runnable{
    private Socket socket;
    private String userName;
    private ObjectInputStream in;

    ClientHandler(Socket socket, String userName, ObjectInputStream in) throws IOException {
        this.socket = socket;
        this.userName = userName;
        this.in = in;
    }

    public void run(){
        while(true){
            try {
                Message m = (Message) in.readObject();

                File file;
                FileWriter fw;
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

                    Helper.sendMessageToActiveClients(socket, message);
                }
                else{
                    String filename = m.getTextContent().split(", ")[0]
                            + "_" + userName
                            + "_" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                            + "_" + Calendar.getInstance().get(Calendar.MINUTE)
                            + "." + m.getTextContent().split(", ")[1];

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
                            Helper.getImageName(new File(filename)) + ", " + Helper.getImageExtension(new File(filename)),
                            m.getFileContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}