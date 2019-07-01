/*
 * Copyright 2019 Open Communications Security
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.opencs.ssltest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.opencs.ssltest.tools.SSLInfoExtractor;

public class SSLTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	private void testURL(String urlString, PrintWriter out) throws IOException {
		try {
			out.printf("Creating URL instance for '%1$s'...", urlString);
			URL url = new URL(urlString);
			out.println("Done");
			
			out.printf("Opening the connection...");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			out.println("OK");
			try {
				out.printf("Get the return code...");
				int returnCode = conn.getResponseCode();
				out.println("OK");
				out.printf("The return code is %1$d\n", returnCode);
				
				if (conn instanceof HttpsURLConnection) {
					out.printf("\n\nHTTPS connection:\n");
					HttpsURLConnection ssl = (HttpsURLConnection)conn;
					out.printf("Cipher: %1$s\n", ssl.getCipherSuite());
	
					Certificate [] certs = ssl.getServerCertificates();
					for (int i = 0; i < certs.length; i++) {
						out.println("======================================================================");
						out.println(certs[i].toString());
						out.println("======================================================================");
					}
				}
			} finally {
				out.printf("Disconnecting...");
				conn.disconnect();
				out.println("OK");
			}
		} catch (Exception e) {
			out.println("\n\n#####\nError\n#####\n");
			e.printStackTrace(out);
		}		
	}
	
	private void doDumpSSLInfo(HttpServletRequest req, PrintWriter out) throws IOException {
		SSLInfoExtractor.dumpProperties(out);
		out.println();
		SSLInfoExtractor.dumpProtocols(out);
		out.println();
		SSLInfoExtractor.dumpClientAlgorithms(out);
		out.println();
		SSLInfoExtractor.dumpServerAlgorithms(out);
	}
	
	private void doConnect(HttpServletRequest req, PrintWriter out) throws ServletException, IOException {
		
		String url = req.getParameter("url"); 
		if (url != null) {
			testURL(url, out);
		} else {
			out.print("Missing URL!");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getParameter("action");
		resp.setContentType("text/plain");
		if (action == null) {
			resp.getWriter().println("Missing action.");
		} else if (action.contentEquals("connect")) {
			doConnect(req, resp.getWriter());
		} else if (action.contentEquals("dump")) {
			doDumpSSLInfo(req, resp.getWriter());
		} else {
			resp.getWriter().printf("Unknown action '%1$s'.", action);
		}
		resp.getWriter().close();		
	}
}
