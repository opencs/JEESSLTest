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
package br.com.opencs.ssltest.tools;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class SSLInfoExtractor {
	
	private static final String [] PROTOCOL_LIST= {
			"SSL", 
			"SSLv2", 
			"SSLv3", 
			"TLS", 
			"TLSv1", 
			"TLSv1.1", 
			"TLSv1.2",
			"TLSv1.3"};
	
	public static void dumpProperties(PrintWriter out) {
		
		out.println("=================");
		out.println("System properties");
		out.println("-----------------");
		
		ArrayList<String> keys = new ArrayList<String>();
		Enumeration<Object> keyEnum = System.getProperties().keys();
		while (keyEnum.hasMoreElements()) {
			keys.add(keyEnum.nextElement().toString());
		}
		Collections.sort(keys);
		
		for (int i = 0; i < keys.size(); i++) {
			out.printf("\t%1$s=%2$s\n", keys.get(i), System.getProperty(keys.get(i)));
		}
	}
	
	public static void dumpProtocols(PrintWriter out) {
		
		out.println("===================");
		out.println("Supported protocols");
		out.println("-------------------");
		for (int i = 0; i < PROTOCOL_LIST.length; i++) {
			boolean supported = false;
			try {
				supported = (SSLContext.getInstance(PROTOCOL_LIST[i]) != null);
			} catch (Exception e) {}
			out.println("\t" + PROTOCOL_LIST[i] + ": " + supported);
		}		
	}
	
	public static void dumpClientAlgorithms(PrintWriter out) {
		
		SSLSocketFactory sf = ((SSLSocketFactory)SSLSocketFactory.getDefault());
		
		out.println("=================");
		out.println("Client Algorithms");
		out.println("-----------------");

		out.println("Default:");		
		String [] defaultAlg = sf.getDefaultCipherSuites();
		Arrays.sort(defaultAlg);
		for (int i = 0; i < defaultAlg.length; i++) {
			out.printf("\t%1$s\n", defaultAlg[i]);
		}

		out.println("Supported:");		
		String [] supported = sf.getSupportedCipherSuites();
		Arrays.sort(supported);
		for (int i = 0; i < supported.length; i++) {
			out.printf("\t%1$s\n", supported[i]);
		}
	}
	
	public static void dumpServerAlgorithms(PrintWriter out) {
		SSLServerSocketFactory sf = ((SSLServerSocketFactory)SSLServerSocketFactory.getDefault());
		
		out.println("=================");
		out.println("Server Algorithms");
		out.println("-----------------");

		out.println("Default:");		
		String [] defaultAlg = sf.getDefaultCipherSuites();
		Arrays.sort(defaultAlg);
		for (int i = 0; i < defaultAlg.length; i++) {
			out.printf("\t%1$s\n", defaultAlg[i]);
		}

		out.println("Supported:");		
		String [] supported = sf.getSupportedCipherSuites();
		Arrays.sort(supported);
		for (int i = 0; i < supported.length; i++) {
			out.printf("\t%1$s\n", supported[i]);
		}
	}
}
