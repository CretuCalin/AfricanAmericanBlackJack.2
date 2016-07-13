import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientClass extends Thread
{

    private volatile boolean endGame;
    private Socket socket; 
    private int port ;
    private String ip;

    private int total;

    private ReadThread readThread ;
    private WriteThread writeThread ;

    private ExecutorService executorService;



    ClientClass(String ipToGet, int portToGet)
    {
        port = portToGet;
        ip = ipToGet;
        endGame=false;
        executorService = Executors.newCachedThreadPool();
    }


    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }



    
    public void connect()
    {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setUpStreams()
    {

        readThread = new ReadThread(socket, this);
        executorService.execute(readThread);
        writeThread = new WriteThread(socket, this,readThread);
        executorService.execute(writeThread);

    }

    public void playGame()
    {
        connect();
        System.out.println("Connected");
        setUpStreams();
        System.out.println("SetUpStreams");

    }

}
