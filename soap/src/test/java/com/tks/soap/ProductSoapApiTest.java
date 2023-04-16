package com.tks.soap;

import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.soap.endpoints.ProductEndpointApi;
import com.tks.soap.model.ProductSoap;
import jakarta.ws.rs.core.GenericType;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductSoapApiTest extends TestContainerInitializer {

    static ProductEndpointApi productSoapApi;

    @Override
    @BeforeAll
    public void setup() {
        super.setup();
        String wsdlUrl = baseUri + "/soap-1.0-SNAPSHOT/productSoapApi?wsdl";
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(ProductEndpointApi.class);
        factory.setAddress(wsdlUrl);

        productSoapApi = (ProductEndpointApi) factory.create();
    }

    @Test
    void addDeleteProductTest() throws Exception {
        Product tv = new Tv(1, 23.0, "tv", "dell", "telewizor", "11", "2", "60Hz", "IPS");

        productSoapApi.addProduct(tv);

        List<ProductSoap> response = productSoapApi.getProducts(null, null).readEntity(new GenericType<List<ProductSoap>>() {
        });

        Assertions.assertEquals(1, response.size());
    }
}