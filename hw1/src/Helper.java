import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

class Helper {

     static int getChoice(Scanner in, boolean isAdmin){
        int choice;

        System.out.println("Press (1) to send a text message to the server\nOr (2) to send an image to the server");
        if(isAdmin){
            System.out.println("Or (3) to print chat history\nOr (4) to delete a message from the history");
        }
        System.out.println("Or (0) to exit");

        while(true){
            try {
                choice = Integer.parseInt(in.nextLine());
                if(choice == 1 || choice == 2 || choice == 0){
                    return choice;
                }
                else if(isAdmin && (choice == 3 || choice == 4)){
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

     static void sendTextMessage(Scanner in, ObjectOutputStream out) throws IOException{
        System.out.println("Please type in a message, then press Enter");

        String message = in.nextLine();

        out.writeObject(new Message(1, message, null));
        out.flush();
    }

    static void sendImage(Socket socket, Scanner in, ObjectOutputStream out) throws IOException{
        System.out.println("Specify the filepath of the image, then press Enter");

        String path = in.nextLine();
        File file = new File(path);

        out.writeObject(new Message(2,
                Helper.getImageName(file) + ", " + Helper.getImageExtension(file),
                Files.readAllBytes(file.toPath())));
        out.flush();
    }

    static void printChatHistory() throws FileNotFoundException {
        File file = new File("." + File.separator + "chat.txt");
        Scanner in = new Scanner(file);
        while(in.hasNextLine()){
            System.out.println(in.nextLine());
        }
    }

    static void deleteMessageFromHistory() throws IOException {
        System.out.println("Please type in a message ID to delete and press Enter");

        Scanner in = new Scanner(System.in);
        int messageIDToDelete = Integer.parseInt(in.nextLine());

        File file = new File("." + File.separator + "chat.txt");
        File tempFile = new File("." + File.separator + "temp.txt");
        in = new Scanner(file);
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile, true));

        while(in.hasNextLine()){
            String temp = in.nextLine();
            if (Integer.parseInt(temp.split(" ")[0]) != messageIDToDelete) {
                bw.write(temp + "\n");
            }
        }
        bw.flush();
        tempFile.renameTo(file);
    }

    static int getLatestMessageID() throws FileNotFoundException {
        File file = new File("." + File.separator + "chat.txt");
        if(!file.exists()){
            return 0;
        }
        Scanner in = new Scanner(file);
        String lastScanned = "";
        while(in.hasNextLine()){
            lastScanned = in.nextLine();
        }
        try {
            return Integer.parseInt(lastScanned.split(" ")[0]);
        }
        catch(NumberFormatException e){
            return 0;
        }
    }

    static String getImageName(File file){
        String[] pathSeparated = file.getAbsolutePath().split("/");
        String lastPiece = pathSeparated[pathSeparated.length - 1];
        return lastPiece.substring(0, lastPiece.indexOf('.'));
    }

    static String getImageExtension(File file){
        String[] pathSeparated = file.getAbsolutePath().split("/");
        String lastPiece = pathSeparated[pathSeparated.length - 1];
        return lastPiece.substring(lastPiece.indexOf('.') + 1, lastPiece.length());
    }

    static void sendMessageToActiveClients(Socket socket, String message) throws IOException {
        ArrayList<Pair<Socket, ObjectOutputStream>> activeSockets = Server.getActiveSockets();
        for (Pair<Socket, ObjectOutputStream> socketOutput : activeSockets) {
            if (!socketOutput.getKey().equals(socket)) {
                socketOutput.getValue().writeObject(new Message(1, message, null));
                socketOutput.getValue().flush();
            }
        }
    }

    static void sendImageToActiveClients(Socket socket, String nameAndExt, byte[] file) throws IOException {
        ArrayList<Pair<Socket, ObjectOutputStream>> activeSockets = Server.getActiveSockets();
        for (Pair<Socket, ObjectOutputStream> socketOutput : activeSockets) {
            if (!socketOutput.getKey().equals(socket)) {
                socketOutput.getValue().writeObject(new Message(2, nameAndExt, file));
                socketOutput.getValue().flush();
            }
        }
    }
}