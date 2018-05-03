package operation;

import domain.AOperation;

public class FindAccountsOperation extends AOperation {

	private static final long serialVersionUID = 936421529393892799L;

	public FindAccountsOperation() {
		originAccount = null;
		value = null;
		type = OperationType.FIND_ACCOUNTS;
	}

	@Override
	public boolean isValid() {
		return (originAccount != null && originAccount > 0);
	}

}
