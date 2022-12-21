package sqliteDB;

import java.util.ArrayList;

public class CalWifiPoint {
	Node head;

	CalWifiPoint() {}

	private boolean isEmpty() {
	    return this.head != null;
	}

    public void dataLog(double num, String X_SWIFI_MGR_NO) {
	    if (this.isEmpty()) {
	        if (this.head.next == null) {
	         	if(this.head.distance > num) {
	         		Node node = new Node(num,X_SWIFI_MGR_NO,this.head);
	           		this.head = node;
	           		return;
	           	}else {
	           		this.head.next = new Node(num,X_SWIFI_MGR_NO,null);
	           		return;
	           	}
	        }
           	if(this.head.distance > num) {
         		Node node = new Node(num,X_SWIFI_MGR_NO,this.head);
           		this.head = node;
           		return;
          	}
	        Node log = this.head.next;
	        Node logBe = this.head;
	        int cts = 0;
	        boolean y_n = true;
	        for(log = this.head; log.next != null; log = log.next) {
	          	if(log.distance > num && y_n) {
	           		Node node = new Node(num,X_SWIFI_MGR_NO,log);
	           		logBe.next = node;
	           		node.next = log;
	           		y_n = false;
	           		cts++;
	           	}
	            logBe = log;
	            cts++;
	            if(cts > 19) {
	            	logBe.next = null;
	              	break;
	            }
	        }
	        
	    } else {
	       	this.head = new Node(num,X_SWIFI_MGR_NO, null);
	    }
	}

	public ArrayList<Node> outData() {
	   if (!this.isEmpty()) {
	      System.out.println("Data 없음.");
	      return null;
	   } else {
		   ArrayList<Node> outdata = new ArrayList<>(); 
	        Node logBe;
	        int cts = 1;
	        
	        outdata.add(this.head);
	        for(logBe = this.head; logBe.next != null; logBe = logBe.next) {
	          	outdata.add(logBe.next);
	          	cts++;
	          	if(cts == 19) {
	          		outdata.add(logBe.next);
	          		break;
	          	}
	        }

	        return outdata;
	   }
	}
}

class Node{
	Node next;
	double distance;
	String X_SWIFI_MGR_NO;
	Node(double num , String X_SWIFI_MGR_NO, Node next){
		this.distance = num;
		this.X_SWIFI_MGR_NO = X_SWIFI_MGR_NO;
		this.next = next;
	}
}
