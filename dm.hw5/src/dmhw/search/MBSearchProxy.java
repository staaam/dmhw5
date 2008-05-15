package dmhw.search;

public class MBSearchProxy implements dmhw.search.MBSearch {
  private String _endpoint = null;
  private dmhw.search.MBSearch mBSearch = null;
  
  public MBSearchProxy() {
    _initMBSearchProxy();
  }
  
  public MBSearchProxy(String endpoint) {
    _endpoint = endpoint;
    _initMBSearchProxy();
  }
  
  private void _initMBSearchProxy() {
    try {
      mBSearch = (new dmhw.search.MBSearchServiceLocator()).getMyService();
      if (mBSearch != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mBSearch)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mBSearch)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mBSearch != null)
      ((javax.xml.rpc.Stub)mBSearch)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public dmhw.search.MBSearch getMBSearch() {
    if (mBSearch == null)
      _initMBSearchProxy();
    return mBSearch;
  }
  
  public java.lang.String[] search(java.lang.String[] keywords, int rank, long time) throws java.rmi.RemoteException{
    if (mBSearch == null)
      _initMBSearchProxy();
    return mBSearch.search(keywords, rank, time);
  }
  
  
}