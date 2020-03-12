import java.util.Base64;
import java.io.*;

public class DecoderBase64{
	private String type;
	private String encodedString;

	public DecoderBase64(String type, String encodedString){
		this.encodedString = encodedString;
		this.type = type;
	}

	public void download(String name) throws IOException{
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String[] ti = type.split("/");
		FileOutputStream file= new FileOutputStream(name + "." + (String)ti[1].subSequence(0,ti[1].length() - 1));
		file.write(decodedBytes);
		file.close();
	}

}