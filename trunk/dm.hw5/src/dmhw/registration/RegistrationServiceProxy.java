package dmhw.registration;

public class RegistrationServiceProxy implements dmhw.registration.RegistrationService {
  private String _endpoint = null;
  private dmhw.registration.RegistrationService registrationService = null;
  
  public RegistrationServiceProxy() {
    _initRegistrationServiceProxy();
  }
  
  public RegistrationServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initRegistrationServiceProxy();
  }
  
  private void _initRegistrationServiceProxy() {
    try {
      registrationService = (new dmhw.registration.RegistrationServiceServiceLocator()).getEndpointsRegistration();
      if (registrationService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)registrationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)registrationService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (registrationService != null)
      ((javax.xml.rpc.Stub)registrationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public dmhw.registration.RegistrationService getRegistrationService() {
    if (registrationService == null)
      _initRegistrationServiceProxy();
    return registrationService;
  }
  
  public java.lang.String addEndpoint(java.lang.String url) throws java.rmi.RemoteException{
    if (registrationService == null)
      _initRegistrationServiceProxy();
    return registrationService.addEndpoint(url);
  }
  
  public java.lang.String deleteEndpoint(java.lang.String url) throws java.rmi.RemoteException{
    if (registrationService == null)
      _initRegistrationServiceProxy();
    return registrationService.deleteEndpoint(url);
  }
  
  public java.lang.String[] getRegisteredEndpoints() throws java.rmi.RemoteException{
    if (registrationService == null)
      _initRegistrationServiceProxy();
    return registrationService.getRegisteredEndpoints();
  }
  
  
}