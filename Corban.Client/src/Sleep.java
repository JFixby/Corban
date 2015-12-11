

public class Sleep {

	public static void sleep(long s){
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
	}
}
