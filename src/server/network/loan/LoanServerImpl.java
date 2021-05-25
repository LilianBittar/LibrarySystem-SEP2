package server.network.loan;

import shared.loan.Loan;
import shared.materials.Material;
import shared.person.borrower.Borrower;
import server.core.ModelFactoryServer;
import shared.servers.ClientCallback;
import shared.servers.LoanServer;
import shared.util.EventTypes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class LoanServerImpl implements LoanServer
{


  public LoanServerImpl()
  {
    try
    {
      UnicastRemoteObject.exportObject(this, 0);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }

  }

  @Override public void registerLoan(Material material, Borrower borrower) throws IllegalStateException
  {
    ModelFactoryServer.getInstance().getLoanModel().registerLoan(material, borrower);
  }

  @Override public List<Loan> getAllLoansByCPR(String cpr)
  {
    return ModelFactoryServer.getInstance().getLoanModel().getAllLoansByCPR(cpr);
  }

  @Override public void extendLoan(Loan loan)
  {
    ModelFactoryServer.getInstance().getLoanModel().extendLoan(loan);
  }

  @Override public void endLoan(Loan loan)
  {
    ModelFactoryServer.getInstance().getLoanModel().endLoan(loan);
  }

  public void registerClientCallBack(ClientCallback client)
  {
    PropertyChangeListener listenerLoanRegister = new PropertyChangeListener()
    {
      @Override public void propertyChange(PropertyChangeEvent evt)
      {
        try
        {
          //TODO: let client/rmiclient implement ClientCallBack and implement the loanRegistered Method. The method should forward/fire the same event.
          client.loanRegistered((Loan) evt.getNewValue());
        }
        catch (RemoteException e)
        {
          //Removes listener if connection failed
          e.printStackTrace();
          ModelFactoryServer.getInstance().getLoanModel().removePropertyChangeListener(EventTypes.LOANREGISTERED, this);
        }
      }
    };
    PropertyChangeListener listenerLoanEnd = new PropertyChangeListener()
    {
      @Override public void propertyChange(PropertyChangeEvent evt)
      {
        try
        {
          client.loanEnded((Loan) evt.getNewValue());
        }
        catch (RemoteException e)
        {
          e.printStackTrace();
          ModelFactoryServer.getInstance().getLoanModel().removePropertyChangeListener(EventTypes.LOANENDED, this);
        }
      }
    };
    PropertyChangeListener listener = new PropertyChangeListener()
    {
      @Override public void propertyChange(PropertyChangeEvent evt)
      {
        try
        {
          client.loanUpdate(evt);
        }
        catch (RemoteException e)
        {
          e.printStackTrace();
          ModelFactoryServer.getInstance().getLoanModel().removePropertyChangeListener(this);
        }
      }
    };
    ModelFactoryServer.getInstance().getLoanModel().addPropertyChangeListener(EventTypes.LOANREGISTERED, listener);
    ModelFactoryServer.getInstance().getLoanModel().addPropertyChangeListener(EventTypes.LOANENDED, listener);
    ModelFactoryServer.getInstance().getLoanModel().addPropertyChangeListener(EventTypes.LOANEXTENDED, listener);
    ModelFactoryServer.getInstance().getLoanModel().addPropertyChangeListener(EventTypes.LOANEXTENDERROR, listener);

  }

}
