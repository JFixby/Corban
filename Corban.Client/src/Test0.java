import nxt.client.ClientResource;

public class Test0 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientResource R = new ClientResource("bts://Fieral/motors/A");
		R.put("{ \"speed\":120, \"forward\":true}");
		System.out.println(R.get());
		Sleep.sleep(1000);
		R.put("{ \"speed\":0, \"forward\":false}");
		System.out.println(R.get());
	}

}
