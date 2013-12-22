package org.nhnnext.nextagram_android;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class ProxyUP {
	

	private String lineEnd = "\r\n";
	private String twoHyphens = "--";
	private String boundary = "*****";
	
	public String uploadArticle(ListData data, String filePath) {
		
		// �ȵ���̵� ������ �������� �ڼ��� �ٷ� �����Դϴ�.
		// ��Ʈ��ũ ������ �ʿ��� �����Դϴ�.
		
		try {
			
			FileInputStream fis = new FileInputStream(filePath);
			
			URL url = new URL("http://10.73.44.93/~stu06/upload.php");
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Connection", "Keep-Alive");
			
			//���� �����Ͱ� ���� ������ ������ �� ��� �����ڴٴ� �ǹ�
			conn.setRequestProperty("Transfer-Encoding", "chunked");
			
			conn.setDoOutput(true);
			conn.setDoInput(true);

			//Content�� multipart�����̰� �������� ���� boundary(*****)�� ǥ�ø� �ϰڴ�.(content-length�� �����Ƿ�...)
			conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

			// write data
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			
			
			dos.write( getPostData("Owner",data.getOwner()).getBytes("UTF-8") );
			dos.write( getPostData("Contents",data.getContents()).getBytes("UTF-8") );
			dos.write( getPostData("ImgName",data.getImgName()).getBytes("UTF-8") );
		    
		    
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + data.getImgName() + "\"" + lineEnd);
			dos.writeBytes(lineEnd);


			
			int bytesAvailable = fis.available();
			int maxBufferSize = 1024;
			
			// ���ۻ����� ����
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);

			byte[] buffer = new byte[bufferSize];
			int bytesRead = fis.read(buffer, 0, bufferSize);

			// read image
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fis.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fis.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			Log.i("Test", "File is written");
			
			fis.close();
			dos.flush();
			dos.close();
			
			int status = conn.getResponseCode();
			Log.i("test", "statusUP:" + status);
			
			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("test", "UPLOAD ERROR:" + e);
		}
		
		return null;
	}
	
	// post��Ŀ� �°� ������ ������ �߰�
	private String getPostData(String key, String value) {
		String result = twoHyphens + boundary + lineEnd;
		result += "Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd;
		result += lineEnd;
		
		result += value;
		
		result += lineEnd;
		
		return result;
	}
	
}