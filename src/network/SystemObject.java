package network;

import java.io.Serializable;

public class SystemObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object obj=null;
	private SystemMode mode=null;
	/**
	 * constructor
	 */
	public SystemObject()
	{
		
	}
	/**
	 * 
	 * @param obj
	 * @param mod
	 */
	public SystemObject(Object obj, SystemMode mod)
	{
		setObj(obj);
		setMode(mod);
	}
	/**
	 * 
	 * @param obj
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	/**
	 * 
	 * @return object
	 */
	public Object getObj() {
		return obj;
	}

	public void setMode(SystemMode mode) {
		this.mode = mode;
	}
	/**
	 * 
	 * @return mode
	 */
	public SystemMode getMode() {
		return mode;
	}
}
