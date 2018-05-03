package operation;

import java.util.Date;

import domain.Account;
import domain.AOperation;

public class TransferenceOperation extends AOperation {

	private static final long serialVersionUID = -4580213410824715890L;
	protected Integer targetAccount;
	
	public TransferenceOperation() {
		originAccount = null;
		value = null;
		type = OperationType.TRANSFERENCE;
		targetAccount = null;
	}

	@Override
	public String createFormattedLog() {
		String log = "[TRANSFERENCE]\t" + targetAccount + "\t";
		log += (value != null) ? value + "\t" : "";
		log += new Date();
		return log;
	}

	@Override
	public boolean isValid() {
		boolean valid = true;
		valid = valid && (originAccount != null && originAccount > 0);
		valid = valid && (targetAccount != null && targetAccount > 0);
		valid = valid && (value != null && (value > 0 && value < Account.MAX_BALANCE));
		return false;
	}
	
	public Integer getTargetAccount() {
		return targetAccount;
	}
	
	public void setTargetAccount(Integer targetAccount) {
		this.targetAccount = targetAccount;
	}
}
