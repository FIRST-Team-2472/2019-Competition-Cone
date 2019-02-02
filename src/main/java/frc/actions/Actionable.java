package frc.actions;

public interface Actionable {

	public void startAction();
	
	public void periodic();
	
	public void endAction();
	
	public boolean isFinished();
		
}
