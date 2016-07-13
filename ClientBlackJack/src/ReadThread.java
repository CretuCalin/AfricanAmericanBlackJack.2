import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import deck.Card;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


/**
 * Created by Cretu Calinn on 7/9/2016.
 */

public class ReadThread implements Runnable {


    private Socket socket;
    private ObjectInputStream input;
    private ClientClass client;

    private volatile Object messageReceived;

    ReadThread(Socket socket, ClientClass client) {
        this.client = client;
        this.socket = socket;
            try {
            input = new ObjectInputStream(this.socket.getInputStream()); // aici se opreste
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getMessageReceived() {
        return messageReceived;
    }


    public void close() {
        try {
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            messageReceived = input.readObject();
            label:
            while (messageReceived != null) {
                if (messageReceived instanceof Card) {
                    System.out.println((Card) messageReceived);
                }
                if (messageReceived instanceof Integer) {
                    System.out.println((Integer) messageReceived);
                }
                if (messageReceived instanceof String) {
                    String message = messageReceived.toString();
                    switch (message) {
                        case "BUSTED":
                            System.out.println("Bust! You Lost");
                            break label;
                        case "You Win":
                        case "You Lost":
                        case "Draw":
                        case "Dealer BUSTED! You Win!":
                            System.out.println(message);
                            break label;
                        default:
                            System.out.println(message);
                            break;
                    }
                }
                messageReceived = input.readObject();

            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            close();
        }
        close();

    }
}

