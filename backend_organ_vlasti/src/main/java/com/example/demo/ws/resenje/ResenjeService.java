package com.example.demo.ws.resenje;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "ResenjeService",
                  wsdlLocation = "classpath:wsdl/Resenje.wsdl",
                  targetNamespace = "http://demo.example.com/ws/resenje")
public class ResenjeService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://demo.example.com/ws/resenje", "ResenjeService");
    public final static QName ResenjePort = new QName("http://demo.example.com/ws/resenje", "ResenjePort");
    static {
        URL url = ResenjeService.class.getClassLoader().getResource("wsdl/Resenje.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ResenjeService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Resenje.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ResenjeService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ResenjeService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ResenjeService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ResenjeService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ResenjeService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ResenjeService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "ResenjePort")
    public Resenje getResenjePort() {
        return super.getPort(ResenjePort, Resenje.class);
    }

    @WebEndpoint(name = "ResenjePort")
    public Resenje getResenjePort(WebServiceFeature... features) {
        return super.getPort(ResenjePort, Resenje.class, features);
    }

}
