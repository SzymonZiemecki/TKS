package com.tks.soap.endpoints;

import com.tks.Product.Product;
import com.tks.soap.model.ProductSoap;
import com.tks.userinterface.ProductService;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.xml.namespace.QName;

@WebService(serviceName = "productSoapApi", endpointInterface = "com.tks.soap.endpoints.ProductEndpointApi")
public class ProductEndpointImpl extends Service implements ProductEndpointApi {
    private final static URL COUNTRYSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException COUNTRYSERVICEIMPLSERVICE_EXCEPTION;
    private final static QName COUNTRYSERVICEIMPLSERVICE_QNAME =
            new QName("http://server.ws.soap.baeldung.com/", "CountryServiceImplService");
    public static String url2;

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(url2);
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        COUNTRYSERVICEIMPLSERVICE_WSDL_LOCATION = url;
        COUNTRYSERVICEIMPLSERVICE_EXCEPTION = e;
    }
    @Inject
    ProductService productManager;

    public ProductEndpointImpl() {
        super(__getWsdlLocation(), COUNTRYSERVICEIMPLSERVICE_QNAME);
    }

    private static URL __getWsdlLocation() {
        if (COUNTRYSERVICEIMPLSERVICE_EXCEPTION != null) {
            throw COUNTRYSERVICEIMPLSERVICE_EXCEPTION;
        }
        return COUNTRYSERVICEIMPLSERVICE_WSDL_LOCATION;
    }

    @Override
    public List<ProductSoap> getProducts() {

        List<Product> productList = productManager.getProducts(null, null);
        List<ProductSoap> productSoapList = new ArrayList<>();

        for (Product product : productList) {
            productSoapList.add(ProductSoap.productToSoap(product));
        }

        return productSoapList;
    }

    @Override
    public ProductSoap getProductById(UUID id) {
        return ProductSoap.productToSoap(productManager.findById(id));
    }

    @Override
    public ProductSoap addProduct(Product product) {
        return ProductSoap.productToSoap(productManager.addItem(product));
    }

    @Override
    public ProductSoap updateProduct(UUID id, Product product) {
        return ProductSoap.productToSoap(productManager.updateProduct(id, product));
    }

    @Override
    public void deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
    }
}
