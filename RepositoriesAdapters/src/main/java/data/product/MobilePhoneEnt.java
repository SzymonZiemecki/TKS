package data.product;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MobilePhoneEnt extends ProductEnt {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int memorySize;
    private String panelType;
    private String operatingSystem;
    private boolean nfcPresent;
    private boolean audioJackPresent;

    public MobilePhoneEnt(int availableAmount, Double price, String name, String producer, String productDescription, String cpu, int ramAmount, String screenSize, String resolution, int batterySize, int memorySize, String panelType, String operatingSystem, boolean nfcPresent, boolean audioJackPresent) {
        super(availableAmount, price, name, producer, productDescription);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.batterySize = batterySize;
        this.memorySize = memorySize;
        this.panelType = panelType;
        this.operatingSystem = operatingSystem;
        this.nfcPresent = nfcPresent;
        this.audioJackPresent = audioJackPresent;
    }

}
