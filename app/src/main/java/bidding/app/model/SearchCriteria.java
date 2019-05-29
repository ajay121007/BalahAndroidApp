package bidding.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchCriteria {


private int pageSize;
private int currentPage;

public int getPageSize() {
return pageSize;
}

public void setPageSize(int pageSize) {
this.pageSize = pageSize;
}

public int getCurrentPage() {
return currentPage;
}

public void setCurrentPage(int currentPage) {
this.currentPage = currentPage;
}

}