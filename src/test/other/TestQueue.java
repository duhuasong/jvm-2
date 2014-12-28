package test.other;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestQueue {
	
	public static BlockingQueue entrancesMethods = new LinkedBlockingQueue<>();

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("start");
		for(int i=0;i<20;i++){
			System.out.println(i);
			entrancesMethods.put("1");
		}
		
		entrancesMethods.take();
		System.out.println("end");
		
	}

}
