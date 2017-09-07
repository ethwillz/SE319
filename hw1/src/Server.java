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

                Thread t = new Thread(new ClientHandler(clientSocket, userName, in, objOut));
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
    private Scanner in;
    private File file;
    private FileWriter fw;

    ClientHandler(Socket socket, String userName, Scanner in, ObjectOutputStream objOut){
        this.socket = socket;
        this.userName = userName;
        this.in = in;
    }

    public void run(){
        while(true){
            if(in.hasNextLine()) {
                int type = Integer.parseInt(in.nextLine());
                if (type == 0) {
                    break;
                } else if (type == 1) {
                    try {
                        String message = in.nextLine();

                        //Add to chat.txt
                        StringBuilder sb = new StringBuilder();
                        sb.append(userName);
                        sb.append(": ");
                        sb.append(message);
                        sb.append("\n");
                        message = sb.toString();

                        System.out.println(message);

                        file = new File("." + File.separator + "chat.txt");
                        fw = new FileWriter(file, true);
                        fw.write(message);
                        fw.flush();

                        Helper.sendMessageToActiveClients(socket, message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
                        String name = in.nextLine();
                        String ext = in.nextLine();

                        //Add to image folder
                        String filename = name
                                + "_" + userName
                                + "_" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                                + "_" + Calendar.getInstance().get(Calendar.MINUTE)
                                + "." + ext;
                        System.out.println(filename);

                        new File("." + File.separator + "image").mkdir();

                        FileOutputStream fos = new FileOutputStream("." + File.separator + "image" + File.separator + filename);
                        fos.write((byte[]) objIn.readObject());

                        //Write to chat.txt
                        file = new File("." + File.separator + "chat.txt");
                        fw = new FileWriter(file, true);
                        fw.write(userName + ": " + filename + "\n");
                        fw.flush();

                        Helper.sendImageToActiveClients(socket, filename);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
