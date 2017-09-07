import java.io.File;

public class Helper {
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
}
