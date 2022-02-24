
package com.modefin.app.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MakeupResponse {

    private long id;
    private String brand;
    private String name;
    private String price;
    private String price_sign;
    private String currency;
    private String image_link;
    private String product_link;
    private String website_link;
    private String description;
    private String rating;
    private String category;
    private String product_type;
    private List<String> tag_list = null;
    private String created_at;
    private String updated_at;
    private String product_api_url;
    private String api_featured_image;
    private List<ProductColor> product_colors = null;

}
