package operation;

import domain.Account;
import domain.AOperation;

public class WithdrawOperation extends AOperation {
	
	private static final long serialVersionUID = -8747659568554021764L;

	public WithdrawOperation() {
		originAccount = null;
		value = null;
		type = OperationType.WITHDRAW;
	}
	
	@Override
	public boolean isValid() {
		boolean valid = true;
		valid = valid && (originAccount != null);
		valid = valid && (originAccount > 0);
		valid = valid && (value != null);
		valid = valid && (value > 0 && value < Account.MAX_BALANCE);
		return valid;
	}
}
