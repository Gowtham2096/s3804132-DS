package databaseSystemAss1;

import java.io.*;

public class dbquery {

	public static void main(String[] args) {
		String query = null;
		int pagesize = 0;
		try {
			query = args[0];
			pagesize = Integer.parseInt(args[1]);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Invalid arguments");
			System.exit(0);
		}
		long startTime, endTime, duration;

		startTime = System.currentTimeMillis();
		search(query, pagesize);
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Time to search: " + (duration / 1000) + "s");
	}

	public static void search(String query, int pagesize) {
		File file = new File("heap" + "." + pagesize);

		try {
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[pagesize];

			String in;
			int pageCount = 0;
			int count = 0;

			while (is.read(buffer) != -1) {
				in = new String(buffer);

				System.out.print("Searching in page " + pageCount + "\r");

				String[] page = in.split("\r\n");

				for (int j = 0; j < page.length - 1; j++) {
					String[] token = page[j].split(",");

					for (int i = 0; i < token.length; i++) {
						// split the data into 2
						String[] data = token[i].split(":", 2);

						// at this point, only search in SDT_NAME
						if (data[0].equals("SDT_NAME")) {
							String text1 = query.toLowerCase();
							String text2 = data[1].toLowerCase();

							if (text2.contains(text1)) {
								System.out.print("Found '" + query + "' in page " + pageCount + ": ");
								System.out.print(data[1] + "\r\n");
								count++;
//								return;
							}
						}
					}
				}
				pageCount++;
			}

			System.out.println();
			System.out.println("Completed");
			System.out.println(count + " match found");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}