import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static List<Socket> activeSockets;

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Scanner in = null;
        ObjectOutputStream objOut;
        Socket clientSocket = null;
        String userName;
        activeSockets = new ArrayList<>();

        try{
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }

        while(true){
            try {
                clientSocket = serverSocket.accept();
                activeSockets.add(clientSocket);

                objOut = new ObjectOutputStream(clientSocket.getOutputStream());
                objOut.flush();

                in = new Scanner(new BufferedInputStream(clientSocket.getInputStream()));
                userName = in.nextLine();

                System.out.println("Server connected to " + userName);

                Thread t = new Thread(new ClientHandler(clientSocket, userName, in, objOut));
                t.start();

            } catch (IOException e) {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
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
    private ObjectOutputStream objOut;
    private File file;
    private FileWriter fw;
    private ArrayList<Socket> activeSockets;

    ClientHandler(Socket socket, String userName, Scanner in, ObjectOutputStream objOut){
        this.socket = socket;
        this.userName = userName;
        this.in = in;
        this.objOut = objOut;
    }

    public void run(){
        PrintWriter out;

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
                        fw = new FileWriter(file);
                        fw.append(message);
                        fw.flush();

                        //Send message to all other connected clients
                        activeSockets = (ArrayList<Socket>) Server.getActiveSockets();
                        for (Socket s : activeSockets) {
                            if (!s.equals(socket)) {
                                out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
                                out.println(1);
                                out.print(message);
                                out.flush();
                            }
                        }
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
                        file = new File("." + File.separator + filename);
                        System.out.println(filename);

                        FileOutputStream fos = new FileOutputStream(filename);
                        fos.write((byte[]) objIn.readObject());

                        //Write filename to chat.txt
                        StringBuilder sb = new StringBuilder();
                        sb.append(userName);
                        sb.append(": ");
                        sb.append(filename);
                        sb.append("\n");
                        String message = sb.toString();

                        file = new File("." + File.separator + "chat.txt");
                        fw = new FileWriter(file);
                        fw.append(message);

                        //Send image to all other connected clients
                        activeSockets = (ArrayList<Socket>) Server.getActiveSockets();
                        for (Socket s : activeSockets) {
                            if (!s.equals(socket)) {
                                out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
                                out.println(2);
                                out.flush();

                                objOut = new ObjectOutputStream(s.getOutputStream());
                                objOut.writeObject(Files.readAllBytes(file.toPath()));
                                objOut.flush();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
