package br.com.opencs.ssltest.console;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionTest {

	public static void connect(String url) {
		PrintWriter out = new PrintWriter(System.out);

		out.printf("# Test connection to '%1$s'\n\n", url);
		try {
			connectCore(new URL(url), out);
		} catch (Exception e) {
			out.printf("\n\n## Error\n\n");			
			out.printf("```\n");
			e.printStackTrace(out);
			out.printf("```\n");
		}
		
		out.close();
	}
		
	private static void connectCore(URL url, PrintWriter out) {
		try {
			
			out.printf("Opening the connection...");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			out.println("OK");
			
			try {
				out.printf("Get the return code...");
				int returnCode = conn.getResponseCode();
				out.println("OK");
				out.printf("The return code is %1$d\n", returnCode);
				
				if (conn instanceof HttpsURLConnection) {
					out.printf("\n## HTTPS connection\n\n");
					HttpsURLConnection ssl = (HttpsURLConnection)conn;
					
					out.println("Property | Value");
					out.println("-------- | -----");
					out.printf("Cipher | %1$s\n", ssl.getCipherSuite());

					out.printf("\n### Certificates \n");
					Certificate [] certs = ssl.getServerCertificates();
					for (int i = 0; i < certs.length; i++) {
						out.printf("\n### Certificate %1$d\n\n", i);
						out.printf("```\n");
						out.println(certs[i].toString());
						out.printf("```\n");
					}
				}
			} finally {
				out.printf("\n\nDisconnecting...");
				conn.disconnect();
				out.println("OK");
			}
		} catch (Exception e) {
			out.printf("\n\n## Error\n\n");			
			out.printf("```\n");
			e.printStackTrace(out);
			out.printf("```\n");
		}	
	}
}
