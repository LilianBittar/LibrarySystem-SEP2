package client.model.loan.loanstates;

import shared.loan.Loan;

import java.io.Serializable;

public interface LoanState extends Serializable
{

  public void extendLoan(Loan loan);
}
