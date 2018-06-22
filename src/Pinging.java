
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Pinging extends Thread {

	private Object[] msg;
	private String ip;

	public Pinging(String ip, Object[] msg) {
		this.ip = ip;
		this.msg = msg;
	}
	@Override
	public void run() {

		BufferedReader br = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ping -a " + ip);
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String lineInput = null;
			msg[0] = ip;
			
			while ((lineInput = br.readLine()) != null) {
				if (lineInput.indexOf("[") >= 0) {
					msg[3] = lineInput.substring(5, lineInput.indexOf("[") - 1);
				}
				if (lineInput.indexOf("ms") >= 0) {
					msg[1] = lineInput.substring(0,lineInput.indexOf("ms") + 2);
					msg[1] = ((String)msg[1]).substring(((String)msg[1]).lastIndexOf("=")+1);
					if(msg[1].toString().length() > 6) {
						msg[1] = ((String)msg[1]).substring(((String)msg[1]).lastIndexOf("<"));
					}
					msg[2] = lineInput.substring(lineInput.indexOf("TTL=")+4);
					break;
				}
				
			}
			
			if (msg[1] == null) msg[1] = "[n/a]";
			if (msg[2] == null) msg[2] = "[n/s]";
			if (msg[3] == null) msg[3] = "[n/s]";
			if (msg[4] == null) msg[4] = "[n/s]";
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
	}

}
