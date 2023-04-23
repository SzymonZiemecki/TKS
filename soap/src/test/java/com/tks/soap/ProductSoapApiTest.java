package com.tks.soap;

import static com.tks.soap.endpoints.ProductEndpointImpl.url2;

import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.soap.endpoints.ProductEndpointApi;
import com.tks.soap.endpoints.ProductEndpointImpl;
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
        String wsdlUrl = baseUri + "/soap/productSoapApi?wsdl";
        url2 = wsdlUrl;
        ProductEndpointImpl service = new ProductEndpointImpl();
    }

    @Test
    void addDeleteProductTest() throws Exception {
//        Product tv = new Tv(1, 23.0, "tv", "dell", "telewizor", "11", "2", "60Hz", "IPS");
//
//        productSoapApi.addProduct(tv);

        List<ProductSoap> response = productSoapApi.getProducts();

        Assertions.assertEquals(3, response.size());
    }
}