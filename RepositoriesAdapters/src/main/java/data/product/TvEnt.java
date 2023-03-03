package data.product;

import com.tks.Product.Product;
import com.tks.Product.Tv;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TvEnt extends ProductEnt {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;

    public TvEnt(int availableAmount, Double price, String name, String producer, String productDescription, String screenSize, String resolution, String refreshRate, String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }

    public static TvEnt toTvEnt(Tv tv) {
        return TvEnt.builder()
                .availableAmount(tv.getAvailableAmount())
                .price(tv.getPrice())
                .name(tv.getName())
                .producer(tv.getProducer())
                .productDescription(tv.getProductDescription())
                .screenSize(tv.getScreenSize())
                .resolution(tv.getResolution())
                .refreshRate(tv.getRefreshRate())
                .panelType(tv.getPanelType()).build();
    }

    public static Tv toTvDomainModel(TvEnt tv) {
        return Tv.builder()
                .availableAmount(tv.getAvailableAmount())
                .price(tv.getPrice())
                .name(tv.getName())
                .producer(tv.getProducer())
                .productDescription(tv.getProductDescription())
                .screenSize(tv.getScreenSize())
                .resolution(tv.getResolution())
                .refreshRate(tv.getRefreshRate())
                .panelType(tv.getPanelType()).build();
    }


}
