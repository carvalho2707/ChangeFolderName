package bd;

import java.io.Serializable;


public class Gestor implements Serializable{
	
	/**
	 * Serial number for serialization.
	 */
	private static final long serialVersionUID = 1L;

	
	App _outlet;
	
	
	public Gestor(App outs){
		_outlet = outs;
	}
	
	
	public App getApp(){
		return _outlet;
	}
	
	
	public void setApp(App outs){
		_outlet = outs;
	}
}
