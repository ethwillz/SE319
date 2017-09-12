public class Message {

    private int messageCode;
    private String textContent;
    private byte[] fileContent;

    public Message(int messageCode, String textContent, byte[] fileContent){
        this.messageCode = messageCode;
        this.textContent = textContent;
        this.fileContent = fileContent;
    }

    public int getMessageCode(){
        return messageCode;
    }

    public String getTextContent(){
        return textContent;
    }

    public byte[] getFileContent(){
        return fileContent;
    }
}
