package Model.Train;

public class TrainInformation {
	private Train train;
	private boolean created;
	private boolean destroyed;
	
	public TrainInformation(Train train, boolean created, boolean destroyed) {
		this.train = train;
		this.created = created;
		this.destroyed = destroyed;
	}
	
	public Train getTrain() {
		return train;
	}
	
	public boolean getCreated() {
		return created;
	}
	
	public boolean getDestroyed() {
		return destroyed;
	}
}
