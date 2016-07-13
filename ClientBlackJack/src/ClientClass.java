import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientClass extends Thread
{

    private volatile boolean endGame;
    private Socket socket; // pentru conectare la server
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



    /*   while((message = (String)input.readObject())!= "You Lost!")
       {
             String message;
           Character c ;
           total = Integer.parseInt(message);
           System.out.printf("Totalul tau este %d ", total);
           System.out.println("Continue ? (y/n)");
           Scanner scan = new Scanner (System.in);
           c = (char) scan.nextInt();
           if (c == 'y')
               output.writeObject(c);
           else
               break;

       }
       message = (String)input.readObject();
       System.out.println(message);
   }*/
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
    //public void run ()
   // {
    //    try {
            // int card1, card2;
            //System.out.println("Server connection done");
            //input = (ObjectInputStream) socket.getInputStream();
            // sus si jos sunt echivalente?

            //card1 = (int)input.readObject();
            //card2 = (int)input.readObject();
            //System.out.printf("Your total is %d ", card1+card2);
            // Se disting doua cazuri :
            // 1. Jucatorul are sub 21 punctaj -> poate continua sau nu
            // 2. Jucatorul are peste 21 -> se opreste din joc


    //    }catch (Exception e)
    //    {

    //    }
   // }

}
