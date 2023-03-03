package data.product;

import com.tks.Product.Laptop;
import com.tks.Product.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LaptopEnt extends ProductEnt {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int diskSize;
    private String panelType;
    public LaptopEnt(int availableAmount, Double price, String name, String producer, String productDescription, String cpu, int ramAmount, String screenSize, String resolution, int batterySize, int diskSize, String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.batterySize = batterySize;
        this.diskSize = diskSize;
        this.panelType = panelType;
    }

    public static LaptopEnt toLaptopEnt(Laptop laptop) {
        return LaptopEnt.builder()
                .availableAmount(laptop.getAvailableAmount())
                .price(laptop.getPrice())
                .name(laptop.getName())
                .producer(laptop.getProducer())
                .productDescription(laptop.getProductDescription())
                .screenSize(laptop.getScreenSize())
                .resolution(laptop.getResolution())
                .panelType(laptop.getPanelType())
                .ramAmount(laptop.getRamAmount())
                .batterySize(laptop.getBatterySize())
                .cpu(laptop.getCpu())
                .diskSize(laptop.getDiskSize())
                .build();
    }

    public static Laptop toLaptopDomainModel(LaptopEnt laptop) {
        return Laptop.builder()
                .availableAmount(laptop.getAvailableAmount())
                .price(laptop.getPrice())
                .name(laptop.getName())
                .producer(laptop.getProducer())
                .productDescription(laptop.getProductDescription())
                .screenSize(laptop.getScreenSize())
                .resolution(laptop.getResolution())
                .panelType(laptop.getPanelType())
                .ramAmount(laptop.getRamAmount())
                .batterySize(laptop.getBatterySize())
                .cpu(laptop.getCpu())
                .diskSize(laptop.getDiskSize())
                .build();
    }
}
