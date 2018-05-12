package domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import operation.OperationType;

public abstract class AOperation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Integer originAccount;
	protected Double value;
	protected OperationType type;
	
	public abstract boolean isValid();
	
	public String getLog() {
		String msg = "[" + type + "]\t";
		msg += (value != null) ? value : "0";
		msg += "\t\t" + new Timestamp(new Date().getTime());
		return msg;
	}
	
	public Integer getOriginAccount() {
		return originAccount;
	}
	
	public void setOriginAccount(Integer originAccount) {
		this.originAccount = originAccount;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public OperationType getType() {
		return this.type;
	}
}
