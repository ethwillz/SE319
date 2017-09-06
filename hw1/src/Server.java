import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

public class Server {

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Scanner in = null;

        try{
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }

        while(true){
            Socket clientSocket = null;
            String userName;

            try {
                clientSocket = serverSocket.accept();
                in = new Scanner(new BufferedInputStream(clientSocket.getInputStream()));
                userName = in.nextLine();

                System.out.println("Server got connected to " + userName);

                Thread t = new Thread(new ClientHandler(clientSocket, userName, in));
                t.start();

            } catch (IOException e) {
                System.out.println("Accept failed: 4444");
                System.exit(-1);
            }
        }
    }
}

class ClientHandler implements Runnable{
    private Socket socket;
    private String userName;
    private Scanner in;
    private File file;
    private FileWriter fw;

    ClientHandler(Socket socket, String userName, Scanner in){
        this.socket = socket;
        this.userName = userName;
        this.in = in;
    }

    public void run(){
        while(true){
            int type = Integer.parseInt(in.nextLine());
            if(type == 0){
                break;
            }
            else if(type == 1){
                try{
                    String message = in.nextLine();

                    //Add to chat.txt
                    file = new File("." + File.separator + "chat.txt");
                    fw = new FileWriter(file);
                    fw.append(userName + ": " + message + "\n");

                    //Send message to all other clients
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                try {
                    ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
                    file = (File) objIn.readObject();

                    //Add to image folder
                    fw = new FileWriter(file);
                    fw.write("." + File.separator + getImageName(file)
                            + "_" + userName
                            + "_" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                            + "_" + Calendar.getInstance().get(Calendar.MINUTE));

                    //Send image to all other connected clients
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getImageName(File file){
        String[] pathSeperated = file.getAbsolutePath().split("/");
        String lastPiece = pathSeperated[pathSeperated.length - 1];
        return lastPiece.substring(0, lastPiece.indexOf('.'));
    }
}
