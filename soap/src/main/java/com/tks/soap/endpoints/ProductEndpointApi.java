package com.tks.soap.endpoints;

import com.tks.Product.Product;
import com.tks.soap.model.ProductSoap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.Action;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.naming.spi.ObjectFactory;

@WebService(name = "CountryService", targetNamespace = "http://server.ws.soap.baeldung.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({ ObjectFactory.class })
public interface ProductEndpointApi {
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://server.ws.soap.baeldung.com/CountryService/findByNameRequest",
            output = "http://server.ws.soap.baeldung.com/CountryService/findByNameResponse")
    List<ProductSoap> getProducts();

    ProductSoap getProductById(UUID id);

    ProductSoap addProduct(Product product);

    ProductSoap updateProduct(UUID id, Product product);

    void deleteProduct(UUID id);
}
