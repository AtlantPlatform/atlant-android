package com.frostchein.atlant.activities.home;

import com.frostchein.atlant.activities.base.BaseView;
import com.frostchein.atlant.model.Balance;
import com.frostchein.atlant.model.TransactionItems;
import java.util.ArrayList;

public interface HomeView extends BaseView {

  void setContentOnToolbar(Balance balance);

  void setTransactionsRecyclerFragmentOnView(ArrayList<TransactionItems> transactionItems);

  void setNoTransactionsOnView();

  void onLoadingError();

  void onTimeout();

  void onNoInternetConnection();

}
