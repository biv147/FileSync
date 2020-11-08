import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class SyncServer {
	public static void main(String[] args) throws InterruptedException {
		
		ZMQ.Context context = ZMQ.context(1);
		
		ZMQ.Socket server = context.socket(SocketType.PUB);
		server.bind("tcp://*:5555");
		Thread.sleep(1000);
		System.out.println("Server Running...");
		int groupNum = 1;
		
		server.send(String.format("%d", groupNum), ZMQ.SNDMORE);
		 while (!Thread.currentThread().isInterrupted()) {
	            Thread.sleep(500);
	            String dest = String.format("%d", groupNum);
	            server.send(dest, ZMQ.SNDMORE);
	            server.send("Welcome to group "+ groupNum);
	        }
		server.close();
		context.term();
	}
}
