import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map; 
import java.util.HashMap;
import java.io.*; 

public class Client {

    private Client() {}

    public static void main(String[] args) {

	if (args.length != 2){
		System.out.println("java -Djava.security.policy=rmi.policy Server <localhost> <port>");
		System.exit(0);
	}
	int port = Integer.parseInt(args[1]);
	String host = args[0];
        try {
			Registry registry = LocateRegistry.getRegistry(host, port);
		    systemFunctions stub = (systemFunctions) registry.lookup("systemFunctions");

			CallbackClientInterface callbackObj = new CallbackClientImpl();
			stub.addCallback(callbackObj);
		
			InputStreamReader r=new InputStreamReader(System.in);  
			BufferedReader br=new BufferedReader(r);  
			clearScreen();
			while(true){
				System.out.println("Press number for select the option:");
				System.out.println("1. Upload Description");
				System.out.println("2. Get description from key");
				System.out.println("3. Get register from description");
				System.out.println("4. List all content");
				System.out.println("5. List all content with part of description"); 
				System.out.println("6. Exit");  
				int option = (int) Integer.parseInt(br.readLine()); 
				String desc;
				switch (option) {
					case 1:  
						System.out.println("Enter Description:");
						desc = br.readLine();
						int i = stub.setDescription(desc);
						System.out.println("Upload finished, key: ");
						System.out.println(i);
						break;
					case 2:
						System.out.println("Enter key:");   
						desc = br.readLine();
						System.out.println("_____________________________________________________________________");
						System.out.println("Description: ");
						System.out.println(stub.getDescriptionFromKey((int) Integer.parseInt(desc)));
						break;
					case 3:
						System.out.println("Enter Description");    
						desc = br.readLine();
						System.out.println("_____________________________________________________________________");
						System.out.println("Register: ");
						System.out.println(stub.getContentsDescription(desc));
						break;
					case 4:
						System.out.println("_____________________________________________________________________");
						System.out.println("Contents: ");
						System.out.println(stub.getContents());
						break;
					case 5:
						System.out.println("Enter Match");    
						desc = br.readLine();
						System.out.println("_____________________________________________________________________");
						System.out.println("Contents Match: ");
						System.out.println(stub.getContentsValue(desc));
						break;
					case 6:
						System.exit(0);
				}
				System.out.println("_____________________________________________________________________");
				System.out.println("Press enter to clear");    
				br.readLine();
				clearScreen();
				
			}
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
   }  
}


