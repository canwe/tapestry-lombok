package net.sf.lombok.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

public class AccessListProducer {

	public List<Access> getList() throws Exception{
		
		
		List<Access >accessList = new ArrayList<Access>();

		// accessLog.txt to be placed in the root of classpath
		URL url = getClass().getResource("/accessLog.txt");
		BufferedReader in = null;

		
			in = new BufferedReader(new InputStreamReader(url.openStream()));

		SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.sss");

		LineIterator iterator = IOUtils.lineIterator(in);
		while (iterator.hasNext()) {
			String line = iterator.nextLine();
			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			Access accessLog = new Access();
			accessLog.setRemoteHost(tokenizer.nextToken());
			accessLog.setCountry(tokenizer.nextToken());
			String timeStr = tokenizer.nextToken();

			accessLog.setAccessTime(dateFormatter.parse(timeStr));
			accessLog.setBrowser(tokenizer.nextToken());
			accessLog.setOs(tokenizer.nextToken());

			accessList.add(accessLog);
        
		}

		return accessList;
		
	}
}	

