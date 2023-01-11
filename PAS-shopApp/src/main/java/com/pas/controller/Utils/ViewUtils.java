package com.pas.controller.Utils;

import com.pas.model.Address;
import com.pas.model.User.User;
import jakarta.inject.Named;

@Named
public class ViewUtils {

    public static String getClassName(Object obj){
        return obj.getClass().getSimpleName();
    }

    public static String addressToUiView(Address address){
        StringBuilder sb = new StringBuilder();
        if(address != null) {
            sb = address.getCountry() != null ? sb.append(address.getCountry() + ",") : sb.append("");
            sb = address.getCountry() != null ? sb.append(address.getCity() + ",") : sb.append("");
            sb = address.getStreet() != null ? sb.append(address.getStreet() + ",") : sb.append("");
            sb = address.getHouseNumber() != null ? sb.append(address.getHouseNumber() + ",") : sb.append("");
            sb = address.getZipCode() != null ? sb.append(address.getZipCode() + ",") : sb.append("");
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }


}
