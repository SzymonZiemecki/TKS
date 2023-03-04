package repository;

import java.util.List;

import data.product.LaptopEnt;
import data.product.MobilePhoneEnt;
import data.product.ProductEnt;
import data.product.TvEnt;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductEntRepository extends IRepositoryImpl<ProductEnt> {
    public List<ProductEnt> findByName(String name){
        return filter(product -> product.getName().equals(name));
    }

    public List<ProductEnt> findByProducer(String make){
        return filter(product -> product.getProducer().equals(make));
    }
    @PostConstruct
    public void init(){
        this.add(new TvEnt(100, 10D, "Tv", "Samsung", "Best Tv", "big","bigres","144hz","lcd"));
        this.add(new LaptopEnt(100, 10D, "TvA", "Samsung", "Best Laptop", "inteli7",3,"duzy","duza", 1,1, "lcd"));
        this.add(new MobilePhoneEnt(100, 10D, "TvE", "Samsung", "Best Tv", "inteli7",3,"duzy", "duza", 1,1,"lcd","android",false, false));
    }
}
