package org.blazer.util;

import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.Record;

public class EventExample implements HSSFListener {

	public void processRecord(Record arg0) {
		
		System.out.println(arg0);
	}

}
