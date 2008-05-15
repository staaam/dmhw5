/**
 * RegistrationServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package dmhw.registration;

public interface RegistrationServiceService extends javax.xml.rpc.Service {
    public java.lang.String getEndpointsRegistrationAddress();

    public dmhw.registration.RegistrationService getEndpointsRegistration() throws javax.xml.rpc.ServiceException;

    public dmhw.registration.RegistrationService getEndpointsRegistration(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
