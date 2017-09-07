import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Helper {

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

        String path = in.nextLine();
        File file = new File(path);
        ;
        out.println("2");
        out.println(Helper.getImageName(file));
        out.println(Helper.getImageExtension(file));
        out.flush();

        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        objOut.writeObject(Files.readAllBytes(file.toPath()));
        objOut.flush();
    }

    public static String getImageName(File file){
        String[] pathSeparated = file.getAbsolutePath().split("/");
        String lastPiece = pathSeparated[pathSeparated.length - 1];
        return lastPiece.substring(0, lastPiece.indexOf('.'));
    }

    public static String getImageExtension(File file){
        String[] pathSeparated = file.getAbsolutePath().split("/");
        String lastPiece = pathSeparated[pathSeparated.length - 1];
        return lastPiece.substring(lastPiece.indexOf('.') + 1, lastPiece.length());
    }

    public static void sendMessageToActiveClients(Socket socket, String message) throws IOException {
        ArrayList<Socket> activeSockets = (ArrayList<Socket>) Server.getActiveSockets();
        for (Socket s : activeSockets) {
            if (!s.equals(socket)) {
                PrintWriter out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
                out.println(1);
                out.print(message);
                out.flush();
            }
        }
    }

    public static void sendImageToActiveClients(Socket socket, String filename) throws IOException {
        ArrayList<Socket> activeSockets = (ArrayList<Socket>) Server.getActiveSockets();
        for (Socket s : activeSockets) {
            if (!s.equals(socket)) {
                PrintWriter out = new PrintWriter(new BufferedOutputStream(s.getOutputStream()));
                out.println(2);
                out.println(filename.substring(0, filename.indexOf(".")));
                out.println(filename.substring(filename.indexOf(".") + 1, filename.length()));
                out.flush();

                ObjectOutputStream objOut = new ObjectOutputStream(s.getOutputStream());
                objOut.writeObject(Files.readAllBytes(new File("." + File.separator + "image" + File.separator + filename).toPath()));
                objOut.flush();
            }
        }
    }
}
