import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Cretu Calinn on 7/9/2016.
 */
public class WriteThread implements Runnable {

    private ObjectOutputStream output;
    private Socket socket;
    private ClientClass client;
    ReadThread readThread;
 //   Object messageReceived;
    String messageSent;

    WriteThread(Socket socket, ClientClass client, ReadThread readThread) {
        this.client = client;
        this.socket = socket;
        this.readThread = readThread;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        Scanner scan = new Scanner(System.in);
        while (true) {
            if (readThread.getMessageReceived() instanceof String) {
                String message =readThread.getMessageReceived().toString();
                if (message.equals("Enter option: HIT/STAND")) {
                    messageSent = scan.next();
                    try {
                        output.writeObject(messageSent);
                    } catch (IOException e) {
                        e.printStackTrace();
                        close();
                    }
                }
            }
        }
    }
}