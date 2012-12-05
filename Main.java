public class Main extends Thread
{
    public void run()
    {
        StatisticsServer.main(); 
    }

    public static void main ( String[] args )
    {
        Thread t = new Main();
        t.start();
        ServerApp.main( args );
    }
}
