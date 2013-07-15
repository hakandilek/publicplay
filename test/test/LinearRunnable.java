package test;

public class LinearRunnable implements Runnable {

	private Runnable[] calls;

	public LinearRunnable(Runnable[] calls) {
		this.calls = calls;
	}

	@Override
	public void run() {
		if (calls != null) {
			for (Runnable r : calls) {
				r.run();
			}
		}
	}

}
