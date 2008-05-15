/**
 * RegistrationServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package dmhw.registration;

public class RegistrationServiceServiceLocator extends org.apache.axis.client.Service implements dmhw.registration.RegistrationServiceService {

    public RegistrationServiceServiceLocator() {
    }


    public RegistrationServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistrationServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for EndpointsRegistration
    private java.lang.String EndpointsRegistration_address = "http://ibm268.cs.technion.ac.il:8080/axis/services/endpoints-Registration";

    public java.lang.String getEndpointsRegistrationAddress() {
        return EndpointsRegistration_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String EndpointsRegistrationWSDDServiceName = "endpoints-Registration";

    public java.lang.String getEndpointsRegistrationWSDDServiceName() {
        return EndpointsRegistrationWSDDServiceName;
    }

    public void setEndpointsRegistrationWSDDServiceName(java.lang.String name) {
        EndpointsRegistrationWSDDServiceName = name;
    }

    public dmhw.registration.RegistrationService getEndpointsRegistration() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(EndpointsRegistration_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getEndpointsRegistration(endpoint);
    }

    public dmhw.registration.RegistrationService getEndpointsRegistration(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            dmhw.registration.EndpointsRegistrationSoapBindingStub _stub = new dmhw.registration.EndpointsRegistrationSoapBindingStub(portAddress, this);
            _stub.setPortName(getEndpointsRegistrationWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setEndpointsRegistrationEndpointAddress(java.lang.String address) {
        EndpointsRegistration_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (dmhw.registration.RegistrationService.class.isAssignableFrom(serviceEndpointInterface)) {
                dmhw.registration.EndpointsRegistrationSoapBindingStub _stub = new dmhw.registration.EndpointsRegistrationSoapBindingStub(new java.net.URL(EndpointsRegistration_address), this);
                _stub.setPortName(getEndpointsRegistrationWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("endpoints-Registration".equals(inputPortName)) {
            return getEndpointsRegistration();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ibm268.cs.technion.ac.il:8080/axis/services/endpoints-Registration", "RegistrationServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ibm268.cs.technion.ac.il:8080/axis/services/endpoints-Registration", "endpoints-Registration"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("EndpointsRegistration".equals(portName)) {
            setEndpointsRegistrationEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
