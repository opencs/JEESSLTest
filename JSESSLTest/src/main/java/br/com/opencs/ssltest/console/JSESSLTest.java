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
package br.com.opencs.ssltest.console;

public class JSESSLTest {
	
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Usage:");
			System.out.println("\tjava -jar JSESSLTest dump");
			System.out.println("\tjava -jar JSESSLTest connect <url>");
			System.exit(1);
		} else {
			if (args[0].equals("dump")) {
				SSLInfoExtractor.dump();
			} else if (args[0].equals("connect")) {
				if (args.length >= 2) {
					ConnectionTest.connect(args[1]);	
				} else {
					System.out.println("url missing.");
					System.exit(1);
				}
			} else {
				System.out.printf("Unknown command '%1$s'.", args[0]);
				System.exit(1);
			}
		}
	}
}
